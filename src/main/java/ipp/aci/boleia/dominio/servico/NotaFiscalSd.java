package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IArquivoDados;
import ipp.aci.boleia.dados.IComponenteDados;
import ipp.aci.boleia.dados.IEmpresaAgregadaDados;
import ipp.aci.boleia.dados.IFrotaDados;
import ipp.aci.boleia.dados.INotaFiscalDados;
import ipp.aci.boleia.dados.ITransacaoConsolidadaDados;
import ipp.aci.boleia.dados.IUnidadeDados;
import ipp.aci.boleia.dominio.Arquivo;
import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.NotaFiscal;
import ipp.aci.boleia.dominio.TransacaoConsolidada;
import ipp.aci.boleia.dominio.enums.TipoArquivo;
import ipp.aci.boleia.dominio.vo.DanfeVo;
import ipp.aci.boleia.dominio.vo.ItemDanfeVo;
import ipp.aci.boleia.dominio.vo.NotaFiscalVo;
import ipp.aci.boleia.util.ConstantesFormatacao;
import ipp.aci.boleia.util.ConstantesNotaFiscal;
import ipp.aci.boleia.util.ConstantesNotaFiscalParser;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.UtilitarioFormatacaoData;
import ipp.aci.boleia.util.UtilitarioInputStream;
import ipp.aci.boleia.util.UtilitarioJson;
import ipp.aci.boleia.util.UtilitarioStreams;
import ipp.aci.boleia.util.UtilitarioXml;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoArquivoNaoEncontrado;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import ipp.aci.boleia.util.excecao.ExcecaoSemConteudo;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import ipp.aci.boleia.util.validador.ValidadorCnpj;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Oferece funcionalidades para a Nota fiscal
 */
@Component
public class NotaFiscalSd {

    private static final Logger LOG = LoggerFactory.getLogger(NotaFiscalSd.class);
    private static final int LIMITE_NO_SERIE = 10;

    @Autowired
    private ArmazenamentoArquivosSd armazenamentoArquivosSd;

    @Autowired
    private NotaFiscalParserSd notaFiscalParserSd;

    @Autowired
    private ITransacaoConsolidadaDados transacaoConsolidadaDados;

    @Autowired
    private INotaFiscalDados repositorio;

    @Autowired
    private IArquivoDados repositorioArquivo;

    @Autowired
    private UtilitarioAmbiente utilitarioAmbiente;

    @Autowired
    private IComponenteDados componenteDados;

    @Autowired
    private IFrotaDados frotaDados;

    @Autowired
    private IUnidadeDados unidadeDados;

    @Autowired
    private IEmpresaAgregadaDados empresaAgregadaDados;

    @Autowired
    private Mensagens mensagens;

    /**
     * Informa se um abastecimento está emitido dado um conjunto de notas fiscais.
     *
     * @param autorizacaoPagamento Objeto com os dados de abastecimento.
     * @param notasFiscais Conjunto de notas fiscais.
     * @return True, caso o abastecimento esteja emitido.
     */
    public boolean isNotaFiscalAbastecimentoEmitida(AutorizacaoPagamento autorizacaoPagamento, List<NotaFiscal> notasFiscais) {
        if(notasFiscais == null || notasFiscais.isEmpty()) {
            return false;
        }
        if(notasFiscais.stream().anyMatch(NotaFiscal::getIsJustificativa)) {
            return true;
        }

        BigDecimal valorTotalAbastecimento = autorizacaoPagamento.getValorTotal().setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal valorEmitido = notasFiscais.stream().map(NotaFiscal::getValorTotal).reduce(BigDecimal::add).get();
        BigDecimal diferenca = valorTotalAbastecimento.subtract(valorEmitido);
        BigDecimal zero = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        return diferenca.compareTo(zero) == 0 || diferenca.abs().compareTo(obterValorMargemTotal(1)) <= 0;
    }

    /**
     * Informa se a nota fiscal de um abastecimento está valida ou não.
     *
     * @param autorizacaoPagamento A autorização de pagamento que será verificado a validade das notas.
     * @return true, caso esteja valida
     */
    public boolean isNotaFiscalValida(AutorizacaoPagamento autorizacaoPagamento) {
        List<NotaFiscal> notasFiscais = autorizacaoPagamento.getNotasFiscaisSemJustificativa();
        BigDecimal valorTotalNfs = notasFiscais.stream().map(NotaFiscal::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal diferenca = valorTotalNfs.subtract(autorizacaoPagamento.getValorTotal());

        //Verifica se a diferença ultrapassa o valor da margem.
        if((valorTotalNfs.compareTo(autorizacaoPagamento.getValorTotal()) > 0) && (diferenca.compareTo(ConstantesNotaFiscal.MARGEM_VALOR_ABAST) > 0)){
            return false;
        } else{
            try {
                for(NotaFiscal nf : notasFiscais) {
                    byte[] xml = UtilitarioInputStream.carregarEmMemoria(obterXmlNotaFiscal(nf));
                    Document documento = UtilitarioXml.lerXml(xml);
                    validarDadosNotaEdicaoAbastecimento(documento, nf.getAutorizacoesPagamento());
                }
                return true;
            } catch (ExcecaoValidacao excecaoValidacao) {
                return false;
            }
        }
    }

    /**
     * Efetua a validação de notas fiscais de um abastecimento em edição.
     * @param abastecimentoOriginal o abastecimento que será editado.
     * @param abastecimentoEditado o abastecimento contendo os dados alterados.
     * @throws ExcecaoValidacao caso os valores não estejam dentro da margem de tolerância ou ainda que o cnpj do emitente ou do destinatário do abastecimento
     * não estejam coerentes.
     */
    public void validarNotaFiscalPreEdicao(AutorizacaoPagamento abastecimentoOriginal, AutorizacaoPagamento abastecimentoEditado) throws ExcecaoValidacao {
        List<NotaFiscal> notasFiscaisAbastecimentoOriginal = abastecimentoOriginal.getNotasFiscaisSemJustificativa();

        //Validação do somatório das notas fiscais com o novo valor total do abastecimento
        BigDecimal valorTotalNfs = notasFiscaisAbastecimentoOriginal.stream().map(NotaFiscal::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal diferenca = valorTotalNfs.subtract(abastecimentoEditado.getValorTotal());

        //Verifica se a diferença ultrapassa o valor da margem.
        if((valorTotalNfs.compareTo(abastecimentoEditado.getValorTotal()) > 0) && (diferenca.compareTo(ConstantesNotaFiscal.MARGEM_VALOR_ABAST) > 0)){
            throw new ExcecaoValidacao(Erro.ERRO_GENERICO);

        } else{
            //Validação dos dados de cada nota atrelada ao abastecimento
            for(NotaFiscal nf:notasFiscaisAbastecimentoOriginal){
                byte[] xml = UtilitarioInputStream.carregarEmMemoria(obterXmlNotaFiscal(nf));
                Document documento = UtilitarioXml.lerXml(xml);

                //Substituindo abastecimento original pelo abastecimento com os dados editados.
                List<AutorizacaoPagamento> autorizacoesPagamento = new ArrayList<>(nf.getAutorizacoesPagamento());
                autorizacoesPagamento.replaceAll(abastecimento -> abastecimento.getId().equals(abastecimentoEditado.getId()) ? abastecimentoEditado: abastecimento);
                validarDadosNotaEdicaoAbastecimento(documento, autorizacoesPagamento);
            }
        }
    }

    /**
     * Método que obtém o xml de uma nota fiscal no repositório.
     * @param nota Nota fiscal à qual o xml deve ser buscado.
     * @return Stream com xml da nota fiscal.
     */
    public InputStream obterXmlNotaFiscal(NotaFiscal nota) {
        try {
            return armazenamentoArquivosSd.obterArquivo(TipoArquivo.NOTA_FISCAL, null, nota.getId());
        } catch (ExcecaoArquivoNaoEncontrado e) {
            LOG.error(mensagens.obterMensagem("notafiscal.arquivo.naoencontrado", nota.getNumero()), e);
            throw new ExcecaoBoleiaRuntime(e);
        }
    }

    /**
     * Verifica o conteudo da nota fiscal para verificacao de coerencia com o consolidado correspondente
     *
     * @param documento documento que representa o Xml da nota parseada
     * @param autorizacoesPagamento a lista de abastecimentos referentes à nota fiscal.
     * @throws ExcecaoValidacao Caso a nota seja inválida
     */
    public void validarDadosNovaNotaFiscal(Document documento, List<AutorizacaoPagamento> autorizacoesPagamento) throws ExcecaoValidacao {
        List<Erro> errosEncontrados = new ArrayList<>();
        validarVersaoNota(documento, errosEncontrados);
        validarNumeroSerieNota(documento, errosEncontrados);
        validarNumeroNota(documento, errosEncontrados);
        if (!errosEncontrados.isEmpty()) {
            throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO_NOTA_FISCAL, errosEncontrados);
        }

        validarNotaRepetida(documento);
        validarDadosNota(documento, autorizacoesPagamento);
    }

    /**
     * Verifica se o documento contém o número da nota preenchido
     * @param documento Documento quem contém os dados da nota fiscal
     * @param errosEncontrados Lista de erros encontrados
     */
    private void validarNumeroNota(Document documento, List<Erro> errosEncontrados) {
        String nNfe = notaFiscalParserSd.getString(documento, ConstantesNotaFiscalParser.NUMERO);
        if(nNfe == null || nNfe.isEmpty()) {
            errosEncontrados.add(Erro.NOTA_FISCAL_UPLOAD_NUMERO_OBRIGATORIO);
        }
    }

    /**
     * Verifica se o documento contém o número de serie preenchido
     * @param documento Documento quem contém os dados da nota fiscal
     * @param errosEncontrados Lista de erros encontrados
     */
    private void validarNumeroSerieNota(Document documento, List<Erro> errosEncontrados) {
        String nfSerie = notaFiscalParserSd.getString(documento, ConstantesNotaFiscalParser.SERIE);
        if(nfSerie==null || nfSerie.isEmpty()) {
            errosEncontrados.add(Erro.NOTA_FISCAL_UPLOAD_NUMERO_SERIE_OBRIGATORIO);
        } else if (nfSerie.length() > LIMITE_NO_SERIE) {
            errosEncontrados.add(Erro.NOTA_FISCAL_UPLOAD_NUMERO_SERIE_LIMITE);
        }
    }

    /**
     * Exclui uma nota fiscal.
     *
     * @param notaFiscal Nota fiscal que será excluída.
     */
    public void excluirNotaFiscal(NotaFiscal notaFiscal) {
        repositorio.excluir(notaFiscal.getId());
        repositorioArquivo.excluir(notaFiscal.getArquivo().getId());
    }

    /**
     * Deleta o arquivo de uma nota fiscal no S3 da AWS.
     *
     * @param notaFiscal nota fiscal que terá o arquivo deletado.
     */
    public void deletarArquivoNotaFiscal(NotaFiscal notaFiscal) {
        Arquivo arquivo = notaFiscal.getArquivo();
        armazenamentoArquivosSd.removerArquivo(TipoArquivo.NOTA_FISCAL, arquivo.getId(), notaFiscal.getId());
    }

    /**
     * Verifica o conteúdo da nota fiscal para verificação de coerência com os dados editados no abastecimento.
     *
     * @param documento documento que representa o Xml da nota parseada.
     * @param autorizacoesPagamento a lista de abastecimentos referentes à nota fiscal.
     * @throws ExcecaoValidacao Caso a nota seja inválida.
     */
    private void validarDadosNota(Document documento, List<AutorizacaoPagamento> autorizacoesPagamento) throws ExcecaoValidacao {
        List<Erro> errosEncontrados = new ArrayList<>();
        validarCNPJDestinatario(documento, autorizacoesPagamento, errosEncontrados);
        validarCNPJEmitente(documento, autorizacoesPagamento, errosEncontrados);
        validarValorTotal(documento, autorizacoesPagamento, errosEncontrados);
        if (!errosEncontrados.isEmpty()) {
            throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO_NOTA_FISCAL, errosEncontrados);
        }
    }


    /**
     * Verifica o conteúdo da nota fiscal para verificação de coerência com os dados editados no abastecimento.
     *
     * @param documento documento que representa o Xml da nota parseada.
     * @param autorizacoesPagamento a lista de abastecimentos referentes à nota fiscal.
     * @throws ExcecaoValidacao Caso a nota seja inválida.
     */
    private void validarDadosNotaEdicaoAbastecimento(Document documento, List<AutorizacaoPagamento> autorizacoesPagamento) throws ExcecaoValidacao {
        List<Erro> errosEncontrados = new ArrayList<>();
        validarCNPJDestinatario(documento, autorizacoesPagamento, errosEncontrados);
        validarCNPJEmitente(documento, autorizacoesPagamento, errosEncontrados);
        validarValorTotalEdicaoAbastecimento(documento, autorizacoesPagamento, errosEncontrados);

        if (!errosEncontrados.isEmpty()) {
            throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO_NOTA_FISCAL, errosEncontrados);
        }
    }

    /**
     * Verifica a versao da nota fiscal
     *
     * @param documento documento que representa o Xml da nota parseada
     * @param errosEncontrados lista de erros encontrados na validacao
     */
    private void validarVersaoNota(Document documento, List<Erro> errosEncontrados) {
        if (notaFiscalParserSd.isHomologacao(documento) || !ValidadorCnpj.isValidCNPJ(notaFiscalParserSd.getString(documento, ConstantesNotaFiscalParser.DEST_CNPJ))
                || !ValidadorCnpj.isValidCNPJ(notaFiscalParserSd.getString(documento, ConstantesNotaFiscalParser.EMIT_CNPJ))) {
            errosEncontrados.add(Erro.NOTA_FISCAL_UPLOAD_VERSAO_INVALIDA);
        }
    }

    /**
     * Verifica se a nota já foi incluida.
     *
     * @param documento documento que representa o Xml da nota parseada
     * @throws ExcecaoValidacao Caso já exista uma nota fiscal com o mesmo número.
     */
    private void validarNotaRepetida(Document documento) throws ExcecaoValidacao {
        List<NotaFiscal> notasFiscais = repositorio.obterNotaPorNumero(ConstantesNotaFiscal.NOTA_FISCAL_PREFIX + notaFiscalParserSd.getString(documento, ConstantesNotaFiscalParser.NUMERO));
        if (notasFiscais != null && !notasFiscais.isEmpty()) {
            NotaFiscal notaFiscal = notasFiscais.stream()
                                                .filter(nf -> possuiSerieCnpjEmitenteIguais(nf, documento))
                                                .findFirst()
                                                .orElse(null);

            if(notaFiscal != null) {
                AutorizacaoPagamento autorizacaoPagamento = notaFiscal.getAutorizacoesPagamento().stream()
                        .findFirst()
                        .orElseThrow(ExcecaoSemConteudo::new);
                TransacaoConsolidada consolidada = transacaoConsolidadaDados.obterConsolidadoParaAbastecimento(autorizacaoPagamento.getId());
                NotaFiscalVo nota = new NotaFiscalVo(notaFiscal, consolidada, autorizacaoPagamento);
                throw new ExcecaoValidacao(Erro.NOTA_FISCAL_UPLOAD_NOTA_REPETIDA, UtilitarioJson.toJSONString(nota));
            }
        }
    }

    /**
     * Verifica se duas notas fiscais possuem os mesmos número de série e CNPJ do emitente
     * @param nf a nota fiscal existente a ser verificada
     * @param documento o documento sendo processado
     * @return true caso as duas notas possuam o mesmo CNPJ do emitente e número de série, false caso contrário
     */
    private boolean possuiSerieCnpjEmitenteIguais(NotaFiscal nf, Document documento) {
        recuperarCamposNulos(nf);
        return possuiMesmoNumeroSerie(nf.getNumeroSerie(), documento)
                && possuiMesmoCnpjEmitente(nf.getCnpjEmitente(), documento);
    }

    /**
     * Recupera campos da nota a partir do arquivo no S3, caso não estejam preenchidos no banco.
     * @param nf a nota fiscal que terá os campos recuperados
     */
    public void recuperarCamposNulos(NotaFiscal nf) {
        if (!nf.getIsJustificativa() &&
                (nf.getNumeroSerie() == null || nf.getCnpjEmitente() == null || nf.getChaveAcesso() == null)) {
            Document documentoBase = UtilitarioXml.lerXml(UtilitarioStreams.carregarEmMemoria(obterXmlNotaFiscal(nf)));
            nf.setNumeroSerie(UtilitarioXml.getString(documentoBase, ConstantesNotaFiscalParser.SERIE));
            nf.setCnpjEmitente(UtilitarioXml.getString(documentoBase, ConstantesNotaFiscalParser.EMIT_CNPJ));
            nf.setChaveAcesso(UtilitarioFormatacao.formatarNumeroZerosEsquerda(
                    UtilitarioXml.getString(documentoBase, ConstantesNotaFiscalParser.CHAVE_ACESSO),
                    ConstantesNotaFiscal.TAMANHO_CHAVE_ACESSO));
            repositorio.armazenar(nf);
        }
    }

    /**
     * Verifica se um documento possui o mesmo CNPJ do emitente que uma NF existente
     * na base com o mesmo número de documento.
     *
     * @param cnpjEmitente Número do CNPJ
     * @param documento Documento usado na verificação
     * @return True, caso possuam o mesmo CNPJ
     */
    private boolean possuiMesmoCnpjEmitente(String cnpjEmitente, Document documento) {
        return cnpjEmitente.equals(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.EMIT_CNPJ));
    }

    /**
     * Verifica se um documento possui o mesmo número de série de uma NF existente
     * na base com o mesmo número de documento.
     *
     * @param numeroSerie Número de série da NF
     * @param documento Documento usado na verificação
     * @return True, caso possuam o mesmo número de série
     */
    private boolean possuiMesmoNumeroSerie(String numeroSerie, Document documento) {
        return numeroSerie.equals(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.SERIE));
    }

    /**
     * Verifica o cnpj do destinatario
     *
     * @param documento documento que representa o Xml da nota parseada
     * @param autorizacoesPagamento a transacao consolidada correpondente
     * @param errosEncontrados os erros encontrados
     */
    private void validarCNPJDestinatario(Document documento, List<AutorizacaoPagamento> autorizacoesPagamento, List<Erro> errosEncontrados) {
        String destCnpjToString = notaFiscalParserSd.getString(documento, ConstantesNotaFiscalParser.DEST_CNPJ);
        String destCnpjRaiz = ValidadorCnpj.isValidCNPJ(destCnpjToString) ? UtilitarioFormatacao.formatarCnpjRaizApresentacao(destCnpjToString)
                : destCnpjToString.substring(0, 8);

        String frotaCnpjToString = Long.toString(getFrotaResponsavelAbastecimentos(autorizacoesPagamento));
        String frotaCnpjRaiz = ValidadorCnpj.isValidCNPJ(frotaCnpjToString) ? UtilitarioFormatacao.formatarCnpjRaizApresentacao(frotaCnpjToString)
                : frotaCnpjToString.substring(0, 8);
        if(!destCnpjRaiz.equals(frotaCnpjRaiz)) {
            errosEncontrados.add(Erro.NOTA_FISCAL_UPLOAD_CNPJ_DESTINATARIO_INVALIDO);
        }
    }

    /**
     * Verifica o cnpj do emitente
     *
     * @param documento documento que representa o Xml da nota parseada
     * @param autorizacoesPagamento a transacao consolidade correspondente
     * @param errosEncontrados os erros encontrados
     */
    private void validarCNPJEmitente(Document documento, List<AutorizacaoPagamento> autorizacoesPagamento, List<Erro> errosEncontrados) {
        Long emitCnpj = notaFiscalParserSd.getLong(documento, ConstantesNotaFiscalParser.EMIT_CNPJ);
        Long cnpjEmitente = getPontoVendaResponsavelAbastecimentos(autorizacoesPagamento);
        boolean emitCnpjOk = cnpjEmitente.equals(emitCnpj);
        if(!emitCnpjOk) {
            errosEncontrados.add(Erro.NOTA_FISCAL_UPLOAD_CNPJ_EMITENTE_INVALIDO);
        }
    }

    /**
     * Retorna o ponto de venda responsavel pelos abastecimentos informados.
     * Uma nota fiscal sempre se refere a abastecimentos realizados por uma frota ou
     * empresa agregada especifica em um posto especifico. Por esse motivo eh seguro retornar
     * a o cnpj do responsável do primeiro abastecimento.
     *
     * @param autorizacoesDaNota A lista de abastecimentos da nota fiscal
     * @return O cnpj do responsavel pelos abastecimentos
     */
    private Long getPontoVendaResponsavelAbastecimentos(List<AutorizacaoPagamento> autorizacoesDaNota) {
        return autorizacoesDaNota.stream()
                .findFirst()
                .orElseThrow(ExcecaoSemConteudo::new)
                .getPontoVenda().getComponenteAreaAbastecimento().getCodigoPessoa();
    }

    /**
     * Retorna a frota responsavel pelos abastecimentos informados.
     * Uma nota fiscal sempre se refere a abastecimentos realizados por uma frota ou
     * empresa agregada especifica em um posto especifico. Por esse motivo eh seguro retornar
     * a o cnpj do responsável do primeiro abastecimento.
     *
     * @param autorizacoesDaNota A lista de abastecimentos da nota fiscal
     * @return O cnpj do responsavel pelos abastecimentos
     */
    private Long getFrotaResponsavelAbastecimentos(List<AutorizacaoPagamento> autorizacoesDaNota) {
        AutorizacaoPagamento primeiraAutorizacao = autorizacoesDaNota.stream()
                .findFirst()
                .orElseThrow(ExcecaoSemConteudo::new);
        if(primeiraAutorizacao.getEmpresaAgregadaExigeNf() != null && primeiraAutorizacao.getEmpresaAgregadaExigeNf()) {
            return primeiraAutorizacao.getEmpresaAgregada().getCnpj();
        } else if(primeiraAutorizacao.getUnidadeExigeNf() != null && primeiraAutorizacao.getUnidadeExigeNf()) {
            return primeiraAutorizacao.getUnidade().getCnpj();
        } else {
            return primeiraAutorizacao.getFrota().getCnpj();
        }
    }

    /**
     * Verifica a consistencia do valor total da nota fiscal
     *
     * @param documento documento que representa o Xml da nota parseada
     * @param autorizacoesPagamento a transacao consolidada correpondente
     * @param errosEncontrados os erros encontrados
     */
    private void validarValorTotal(Document documento, List<AutorizacaoPagamento> autorizacoesPagamento, List<Erro> errosEncontrados) {
        BigDecimal valorTotalNota = notaFiscalParserSd.getBigDecimal(documento, ConstantesNotaFiscalParser.VALOR_TOTAL);
        if (valorTotalNota != null) {
            valorTotalNota = valorTotalNota.setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal diferenca = obterValorRestanteParaSubirNota(autorizacoesPagamento, valorTotalNota, Boolean.FALSE);
            validarValorRestanteParaSubir(autorizacoesPagamento, errosEncontrados, diferenca);
        } else {
            errosEncontrados.add(Erro.NOTA_FISCAL_NAO_POSSUI_VALOR_TOTAL);
        }
    }

    /**
     *
     * Verifica a consistência do valor total da nota fiscal para edição.
     * @param documento documento que representa o Xml da nota parseada.
     * @param autorizacoesPagamento a transacao consolidada correpondente.
     * @param errosEncontrados os erros encontrados.
     */
    private void validarValorTotalEdicaoAbastecimento(Document documento, List<AutorizacaoPagamento> autorizacoesPagamento, List<Erro> errosEncontrados) {
        BigDecimal valorTotalNota = notaFiscalParserSd.getBigDecimal(documento, ConstantesNotaFiscalParser.VALOR_TOTAL);
        if (valorTotalNota != null){
            valorTotalNota = valorTotalNota.setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal diferenca = obterValorRestanteParaSubirNota(autorizacoesPagamento, valorTotalNota, Boolean.TRUE);
            validarValorRestanteParaSubir(autorizacoesPagamento, errosEncontrados, diferenca);
        } else {
            errosEncontrados.add(Erro.NOTA_FISCAL_NAO_POSSUI_VALOR_TOTAL);
        }
    }

    /**
     * Valida o valor restante para subir da nota fiscal.
     *
     * @param autorizacoesPagamento Autorizações de pagamento da nota
     * @param errosEncontrados Erros encontrados na validação.
     * @param valorRestante Valor restante para subir.
     */
    private void validarValorRestanteParaSubir(List<AutorizacaoPagamento> autorizacoesPagamento, List<Erro> errosEncontrados, BigDecimal valorRestante) {
        valorRestante = valorRestante.setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal zero = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        if (autorizacoesPagamento.size() > 1) {
            if (valorRestante.abs().compareTo(obterValorMargemTotal(autorizacoesPagamento.size())) > 0) {
                errosEncontrados.add(Erro.NOTA_FISCAL_UPLOAD_TOTAL_INVALIDO_MULT_ABAST);
            }
        } else if(valorRestante.compareTo(zero.subtract(obterValorMargemTotal(autorizacoesPagamento.size()))) < 0) {
            errosEncontrados.add(Erro.NOTA_FISCAL_UPLOAD_TOTAL_INVALIDO);
        }
    }

    /**
     * Obtem o valor restante para subida de nota a partir da lista de autorizacoes e do valor total da nota
     *
     * @param autorizacoesPagamento As autorizacoes de pagamento para calculo do valor restante
     * @param valorNota O valor da nota fiscal
     * @param isValidacaoEdicao indica se a validação de notas fiscais está sendo feita para a edição de um abastecimento.
     * @return O saldo restante para emissao de nota
     */
    public BigDecimal obterValorRestanteParaSubirNota(List<AutorizacaoPagamento> autorizacoesPagamento, BigDecimal valorNota, Boolean isValidacaoEdicao){
        BigDecimal valorTotalAutorizacoes = BigDecimal.ZERO;
        BigDecimal valorTotalJaEmitido = BigDecimal.ZERO;
        for(AutorizacaoPagamento autorizacao : autorizacoesPagamento){
            valorTotalJaEmitido = valorTotalJaEmitido.add(autorizacao.getValorEmitido().setScale(2, BigDecimal.ROUND_HALF_UP));
            valorTotalAutorizacoes = valorTotalAutorizacoes.add(autorizacao.getValorTotal().setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        if(isValidacaoEdicao){
            return valorTotalAutorizacoes.subtract(valorNota);
        }
        return valorTotalAutorizacoes.subtract(valorTotalJaEmitido).subtract(valorNota);
    }

    /**
     * Obtêm o valor total da margem de aceitação de upload de notas fiscais baseado no total de abastecimentos
     *
     * @param totalAbastecimentos O total de abastecimentos selecionados para upload de nota
     * @return O valor da margem total calculado
     */
    public BigDecimal obterValorMargemTotal(Integer totalAbastecimentos){
        return ConstantesNotaFiscal.MARGEM_VALOR_ABAST.multiply(new BigDecimal(totalAbastecimentos));
    }

    /**
     * Verifica se uma nota fiscal é uma nota válida
     * @param documento Documento que representa o XML da nota parseada
     * @return true caso seja válida, e false caso contrário
     */
    public Boolean verificaNotaValida(Document documento) {
        List<Erro> errosEncontrados = new ArrayList<>();
        validarVersaoNota(documento, errosEncontrados);
        return errosEncontrados.isEmpty();
    }

    /**
     * Constroi a Nota Fiscal DANFe
     * @param nota nota fiscal
     * @return DANFe
     */
    public DanfeVo getDanfeVo(NotaFiscal nota) {
        Document documento = UtilitarioXml.lerXml(UtilitarioStreams.carregarEmMemoria(obterXmlNotaFiscal(nota)));
        DanfeVo danfe = getDanfeVo(documento);
        danfe.setNumeroDaNota(UtilitarioFormatacao.formatarNumeroNota(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.NUMERO)));
        danfe.setSerieNota(UtilitarioFormatacao.formatarSerieNota(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.SERIE)));
        return danfe;
    }

    /**
     * Constroi a Nota Fiscal DANFe
     * @param documento XML parseado da nota
     * @return DANFe
     */
    public DanfeVo getDanfeVo(Document documento) {
        DanfeVo danfe = new DanfeVo();
        danfe.setEmitNome(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.EMIT_RAZAO_SOCIAL));
        danfe.setDhEmiDataEmissao(UtilitarioFormatacaoData.formatarDataHoraDanfe(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.DATA_EMISSAO), ConstantesFormatacao.FORMATO_DATA_HORA_MINUTOS_SEGUNDOS));
        danfe.setDestNome(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.DEST_RAZAO_SOCIAL));
        danfe.setValorTotalNota(UtilitarioXml.getBigDecimal(documento, ConstantesNotaFiscalParser.VALOR_TOTAL));

        danfe.setNumeroDaNota(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.NUMERO));
        danfe.setSerieNota(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.SERIE));

        danfe.setEmitLogradouro(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.ENDER_EMIT_LOGRADOURO));
        danfe.setEmitNumeroEndereco(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.ENDER_EMIT_NUMERO));
        danfe.setEmitComplemento(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.ENDER_EMIT_COMPLEMENTO));
        danfe.setEmitBairro(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.ENDER_EMIT_BAIRROS));
        danfe.setEmitMunicipio(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.ENDER_EMIT_MUNICIPIO));
        danfe.setEmitUf(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.ENDER_EMIT_UF));
        danfe.setEmitTelefone(UtilitarioFormatacao.formatarNumeroTelefone(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.ENDER_EMIT_TELEFONE)));
        danfe.setEmitCep(UtilitarioFormatacao.formatarCepApresentacao(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.ENDER_EMIT_CEP)));

        danfe.setTipoOperacao(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.TIPO));
        danfe.setCodigoBarra(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.CHAVE_ACESSO));
        danfe.setChaveAcesso(UtilitarioFormatacao.formatarChaveAcesso(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.CHAVE_ACESSO)));
        danfe.setNaturezaOperacao(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.NATUREZA_OPERACAO));
        danfe.setProtocoloAutorizacao(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.PROTOCOLO_AUTORIZACAO));
        danfe.setEmitInscEstadual(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.EMIT_INSC_ESTADUAL));
        danfe.setEmitInscEstadualSubsTributario(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.EMIT_INSC_ESTADUAL_SUB));
        danfe.setEmitCnpj(UtilitarioFormatacao.formatarCnpjApresentacao(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.EMIT_CNPJ)));

        danfe.setDestNome(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.DEST_RAZAO_SOCIAL));
        danfe.setDestCnpjCpf(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.DEST_CNPJ) != null && !UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.DEST_CNPJ).trim().isEmpty() ?
                UtilitarioFormatacao.formatarCpjCnpjApresentacao(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.DEST_CNPJ)) :
                UtilitarioFormatacao.formatarCpjCnpjApresentacao(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.DEST_CPF)));
        danfe.setDestLogradouro(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.ENDER_DEST_LOGRADOURO));
        danfe.setDestNumero(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.ENDER_DEST_NUMERO));
        danfe.setDestBairro(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.ENDER_DEST_BAIRRO));
        danfe.setDestCep(UtilitarioFormatacao.formatarCepApresentacao(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.ENDER_DEST_CEP)));
        danfe.setDestDataSaida(UtilitarioFormatacaoData.formatarDataHoraDanfe(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.DATA_SAIDA), ConstantesFormatacao.FORMATO_DATA_CURTA));
        danfe.setDestHoraSaida(UtilitarioFormatacaoData.formatarDataHoraDanfe(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.DATA_SAIDA), ConstantesFormatacao.FORMATO_HORA_MINUTOS_SEGUNDOS));
        danfe.setDestMunicipio(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.ENDER_DEST_MUNICIPIO));
        danfe.setDestUf(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.ENDER_DEST_UF));
        danfe.setDestTelefone(UtilitarioFormatacao.formatarNumeroTelefone(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.ENDER_DEST_TELEFONE)));
        danfe.setDestInscricaoEstadual(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.DEST_INSC_ESTADUAL));

        danfe.setBaseCalcIcms(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.VALOR_BASE_CALCULO_ICMS));
        danfe.setValorIcms(UtilitarioXml.getBigDecimal(documento, ConstantesNotaFiscalParser.VALOR_ICMS));
        danfe.setBaseCalcIcmsSubst(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.VALOR_BASE_CALCULO_ICMS_SUBST));
        danfe.setValorIcmsSubst(UtilitarioXml.getBigDecimal(documento, ConstantesNotaFiscalParser.VALOR_ICMS_SUBST));
        danfe.setValorAproxTributos(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.VALOR_TOTAL_TRIBUTOS));
        danfe.setValorTotalProdutos(UtilitarioXml.getBigDecimal(documento, ConstantesNotaFiscalParser.VALOR_TOTAL_PRODUTOS));
        danfe.setValorFrete(UtilitarioXml.getBigDecimal(documento, ConstantesNotaFiscalParser.VALOR_FRETE));
        danfe.setValorSeguro(UtilitarioXml.getBigDecimal(documento, ConstantesNotaFiscalParser.VALOR_SEGURO));
        danfe.setDesconto(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.VALOR_DESCONTO));
        danfe.setOutraDespesasAcessorias(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.VALOR_OUTRO));
        danfe.setValorIpi(UtilitarioXml.getBigDecimal(documento, ConstantesNotaFiscalParser.VALOR_IPI));
        danfe.setValorTotalNota(UtilitarioXml.getBigDecimal(documento, ConstantesNotaFiscalParser.VALOR_TOTAL));

        danfe.setTranspNome(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.TRANSP_NOME));
        danfe.setFretePorConta(getModalidadeFrete(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.TRANSP_MODALIDADE_FRETE)));
        danfe.setVeicTranspCodigoAntt(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.TRANSP_CODIGO_ANTT));
        danfe.setPlacaVeiculo(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.TRANSP_PLACA_VEICULO));
        danfe.setTranspUf(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.TRANSP_VEICULO_UF));
        danfe.setTranspCnpjCpf(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.TRANSP_CPF) != null && !UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.TRANSP_CPF).trim().isEmpty() ?
                UtilitarioFormatacao.formatarCpjCnpjApresentacao(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.TRANSP_CPF)) :
                UtilitarioFormatacao.formatarCpjCnpjApresentacao(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.TRANSP_CNPJ)));
        danfe.setTranspEndereco(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.TRANSP_ENDERECO));
        danfe.setTranspMunicipio(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.TRANSP_MUNICIPIO));
        danfe.setVeicTranspUf(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.TRANSP_VEICULO_UF));
        danfe.setTranspInscricaoEstadual(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.TRANSP_INSCRICAO_ESTADUAL));

        NodeList itens = UtilitarioXml.getItens(documento);
        for (int i = 0; i < itens.getLength(); i++) {
            danfe.getDadosDanfe().add(getItemDanfeVo(documento, itens, i));
        }
        danfe.setInfCpl(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.INFOMACOES_COMPLEMENTARES));
        danfe.setInfAdFisco(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.INFOMACOES_ADICIONAL_FISCO));
        danfe.setObsCont(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.OBS_CONT_TEXTO) + "("+UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.OBS_CONT_CAMPO) +")");
        danfe.setDataHoraImpressao(UtilitarioFormatacaoData.formatarDataHoraImpressao(utilitarioAmbiente.buscarDataAmbiente()));
        return danfe;
    }

    /**
     * Inicializa um ItemDanfeVo
     *
     * @param documento Documento que representa o xml da nota
     * @param itens lista que representa a propria nota fiscal
     * @param i index do loop
     * @return retorna um ItemDanfeVo
     */
    private ItemDanfeVo getItemDanfeVo(Document documento, NodeList itens, int i) {
        ItemDanfeVo item = new ItemDanfeVo();
        item.setCodigoProduto(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.ITEM_CODIGO_PRODUTO, itens.item(i)));
        item.setDescProdutoServico(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.ITEM_DESCR_PRODUTO, itens.item(i)));
        item.setNcmsh(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.ITEM_NCM, itens.item(i)));
        item.setCst(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.ITEM_CST, itens.item(i)));
        item.setCfop(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.ITEM_CFOP, itens.item(i)));
        item.setUnid(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.ITEM_UNIDADE, itens.item(i)));
        item.setQtde(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.ITEM_QUANTIDADE, itens.item(i)));
        item.setCfop(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.ITEM_CFOP, itens.item(i)));
        item.setQtde(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.ITEM_QUANTIDADE, itens.item(i)));
        item.setValorUnitario(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.ITEM_VALOR_UNITARIO, itens.item(i)));
        item.setDesconto(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.ITEM_DESCONTO, itens.item(i)));
        item.setValorLiquido(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.ITEM_VALOR_LIQUIDO, itens.item(i)));
        item.setBaseCalcIcms(ConstantesNotaFiscalParser.PREENCHER_ZERO);
        item.setValorIcms(ConstantesNotaFiscalParser.PREENCHER_ZERO);
        item.setValorIpi(ConstantesNotaFiscalParser.PREENCHER_ZERO);
        item.setAlqIcms(ConstantesNotaFiscalParser.PREENCHER_ZERO);
        item.setAlqIpi(ConstantesNotaFiscalParser.PREENCHER_ZERO);
        return item;
    }

    /**
     * Retorna o tipo de modalidade de frete
     * @param modalidade modalidade a ser convertida
     * @return A modalidade do frete (0 - Emitente, 1 - Destinatário, 2 - Terceiros ou 9 - Sem Frete
     */
    public String getModalidadeFrete(Serializable modalidade) {
        if (modalidade != null) {
            if ("0".equals(modalidade)) {
                return ConstantesNotaFiscal.EMITENTE;
            }
            if ("1".equals(modalidade)) {
                return ConstantesNotaFiscal.DESTINATARIO;
            }
            if ("2".equals(modalidade)) {
                return ConstantesNotaFiscal.TERCEIROS;
            }
        }
        return ConstantesNotaFiscal.SEM_FRETE;
    }

    /**
     * Valida se uma nota é única na solução
     * @param nota O XML da nota
     * @return true caso a nota seja única, e false caso contrário
     */
    public Boolean validarUnicidadeNota(Document nota) {
        try {
            validarNotaRepetida(nota);
            return true;
        } catch(Exception e) {
            LOG.error(mensagens.obterMensagem("recolhanf.erro.unicidade"), e);
            return false;
        }
    }

    /**
     * Valida se emitente e destinatário de uma nota se encontram cadastrados na solução para o fluxo de conciliação automática
     * @param cnpjDest CNPJ do destinatário da nota
     * @param cnpjEmit CNPJ do emitente da nota
     * @throws ExcecaoValidacao quando é encontrado uma inconsistência em relação aos CNPJs a serem validados
     */
    public void validarExistenciaCnpjsSolucao(Long cnpjDest, Long cnpjEmit) throws ExcecaoValidacao {
        if(frotaDados.pesquisarPorCnpj(cnpjDest) == null &&
                unidadeDados.pesquisarPorCnpj(cnpjDest) == null &&
                empresaAgregadaDados.pesquisarPorCnpj(cnpjDest) == null) {
            throw new ExcecaoValidacao(mensagens.obterMensagem(Erro.ERRO_VALIDACAO_CNPJ_DEST.getChaveMensagem()));
        }

        if(componenteDados.buscarPorCnpj(cnpjEmit) == null) {
            throw new ExcecaoValidacao(mensagens.obterMensagem(Erro.ERRO_VALIDACAO_CNPJ_EMIT.getChaveMensagem()));
        }
    }

    /**
     * Valida se o objeto DanfeVo está preenchido corretamente com dados obrigatórios
     *
     * @param danfe O objeto a ser validado
     * @return mensagem de erro caso haja algum
     */
    public String validarDanfeVo(DanfeVo danfe) {
        if (danfe.getValorTotalNota() == null) {
            return mensagens.obterMensagem(Erro.ERRO_VALIDACAO_VALOR_NOTA.getChaveMensagem());
        } else if (danfe.getNumeroDaNota() == null || StringUtils.isEmpty(danfe.getNumeroDaNota()) ) {
            return mensagens.obterMensagem(Erro.ERRO_VALIDACAO_NUMERO_NOTA.getChaveMensagem());
        } else if (danfe.getDestCnpjCpf() == null || StringUtils.isEmpty(danfe.getDestCnpjCpf()) ) {
            return mensagens.obterMensagem(Erro.ERRO_VALIDACAO_CNPJ_DEST.getChaveMensagem());
        } else if (danfe.getEmitCnpj() == null || StringUtils.isEmpty(danfe.getEmitCnpj())) {
            return mensagens.obterMensagem(Erro.ERRO_VALIDACAO_CNPJ_EMIT.getChaveMensagem());
        } else if (danfe.getDhEmiDataEmissao() == null || StringUtils.isEmpty(danfe.getDhEmiDataEmissao()) ) {
            return mensagens.obterMensagem(Erro.ERRO_VALIDACAO_DATA_EMISSAO.getChaveMensagem());
        } else if (danfe.getSerieNota() == null ||StringUtils.isEmpty(danfe.getSerieNota()) ) {
            return mensagens.obterMensagem(Erro.ERRO_VALIDACAO_SERIE_NOTA.getChaveMensagem());
        } else if (danfe.getChaveAcesso() == null
                || StringUtils.isEmpty(danfe.getChaveAcesso())
                || danfe.getChaveAcesso().replaceAll(" ", "").length() != ConstantesNotaFiscal.TAMANHO_CHAVE_ACESSO) {
            return mensagens.obterMensagem(Erro.ERRO_VALIDACAO_CHAVE_ACESSO_NOTA.getChaveMensagem());
        } else {
            return null;
        }
    }
}