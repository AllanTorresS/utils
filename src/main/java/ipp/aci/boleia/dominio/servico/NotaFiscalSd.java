package ipp.aci.boleia.dominio.servico;


import ipp.aci.boleia.dados.IComponenteDados;
import ipp.aci.boleia.dados.IEmpresaAgregadaDados;
import ipp.aci.boleia.dados.IFrotaDados;
import ipp.aci.boleia.dados.INotaFiscalDados;
import ipp.aci.boleia.dados.ITransacaoConsolidadaDados;
import ipp.aci.boleia.dados.IUnidadeDados;
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
import ipp.aci.boleia.util.UtilitarioJson;
import ipp.aci.boleia.util.UtilitarioStreams;
import ipp.aci.boleia.util.UtilitarioXml;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoArquivoNaoEncontrado;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import ipp.aci.boleia.util.excecao.ExcecaoRecursoNaoEncontrado;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import ipp.aci.boleia.util.validador.ValidadorCnpj;
import org.apache.commons.lang.StringUtils;
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

    @Autowired
    private ArmazenamentoArquivosSd armazenamentoArquivosSd;

    @Autowired
    private ITransacaoConsolidadaDados transacaoConsolidadaDados;

    @Autowired
    private INotaFiscalDados repositorio;

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
     * Método que obtém o xml de uma nota fiscal no repositório.
     * @param nota Nota fiscal à qual o xml deve ser buscado.
     * @return Stream com xml da nota fiscal.
     */
    public InputStream obterXmlNotaFiscal(NotaFiscal nota) {
        try {
            return armazenamentoArquivosSd.obterArquivo(TipoArquivo.NOTA_FISCAL, null, nota.getId());
        } catch (ExcecaoArquivoNaoEncontrado e) {
            throw new ExcecaoBoleiaRuntime(e);
        }
    }

    /**
     * Verifica o conteudo da nota fiscal para verificacao de coerencia com o consolidado correspondente
     *
     * @param documento documento que representa o Xml da nota parseada
     * @param autorizacoesPagamento a transacao consolidada correpondente
     * @throws ExcecaoValidacao Caso a nota seja inválida
     */
    public void validarDadosNota(Document documento, List<AutorizacaoPagamento> autorizacoesPagamento) throws ExcecaoValidacao {
        List<Erro> errosEncontrados = new ArrayList<>();
        validarVersaoNota(documento, errosEncontrados);
        validarNotaRepetida(documento);
        validarCNPJDestinatario(documento, autorizacoesPagamento, errosEncontrados);
        validarCNPJEmitente(documento, autorizacoesPagamento, errosEncontrados);
        validarValorTotal(documento, autorizacoesPagamento, errosEncontrados);
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
    public void validarVersaoNota(Document documento, List<Erro> errosEncontrados) {
        if (isHomologacao(documento) || !ValidadorCnpj.isValidCNPJ(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.DEST_CNPJ))
                || !ValidadorCnpj.isValidCNPJ(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.EMIT_CNPJ))) {
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
        NotaFiscal notaFiscal = repositorio.obterNotaPorNumero(ConstantesNotaFiscal.NOTA_FISCAL_PREFIX + UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.NUMERO))
                .stream()
                .findFirst()
                .orElse(null);
        if (notaFiscal != null) {
            AutorizacaoPagamento autorizacaoPagamento = notaFiscal.getAutorizacoesPagamento().stream()
                    .findFirst()
                    .orElseThrow(ExcecaoRecursoNaoEncontrado::new);
            TransacaoConsolidada consolidada = transacaoConsolidadaDados.obterConsolidadoPorAbastecimento(autorizacaoPagamento.getId());
            NotaFiscalVo nota = new NotaFiscalVo(notaFiscal, consolidada, autorizacaoPagamento);
            throw new ExcecaoValidacao(Erro.NOTA_FISCAL_UPLOAD_NOTA_REPETIDA, UtilitarioJson.toJSONString(nota));
        }
    }

    /**
     * Verifica o cnpj do destinatario
     *
     * @param documento documento que representa o Xml da nota parseada
     * @param autorizacoesPagamento a transacao consolidada correpondente
     * @param errosEncontrados os erros encontrados
     */
    private void validarCNPJDestinatario(Document documento, List<AutorizacaoPagamento> autorizacoesPagamento, List<Erro> errosEncontrados) {
        String destCnpjToString = UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.DEST_CNPJ);
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
        Long emitCnpj = UtilitarioXml.getLong(documento, ConstantesNotaFiscalParser.EMIT_CNPJ);
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
                .orElseThrow(ExcecaoRecursoNaoEncontrado::new)
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
                .orElseThrow(ExcecaoRecursoNaoEncontrado::new);
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
    public void validarValorTotal(Document documento, List<AutorizacaoPagamento> autorizacoesPagamento, List<Erro> errosEncontrados) {
        BigDecimal valorTotalNota = UtilitarioXml.getBigDecimal(documento, ConstantesNotaFiscalParser.VALOR_TOTAL);
        if (valorTotalNota != null) {
            valorTotalNota = valorTotalNota.setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal diferenca = obterValorRestanteParaSubirNota(autorizacoesPagamento,valorTotalNota);
            diferenca = diferenca.setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal zero = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
            if (autorizacoesPagamento.size() > 1) {
                if (diferenca.abs().compareTo(obterValorMargemTotal(autorizacoesPagamento.size())) > 0) {
                    errosEncontrados.add(Erro.NOTA_FISCAL_UPLOAD_TOTAL_INVALIDO_MULT_ABAST);
                }
            } else if(diferenca.compareTo(zero.subtract(obterValorMargemTotal(autorizacoesPagamento.size()))) < 0) {
                errosEncontrados.add(Erro.NOTA_FISCAL_UPLOAD_TOTAL_INVALIDO);
            }
        } else {
            errosEncontrados.add(Erro.NOTA_FISCAL_NAO_POSSUI_VALOR_TOTAL);
        }
    }

    /**
     * Obtem o valor restante para subida de nota a partir da lista de autorizacoes e do valor total da nota
     *
     * @param autorizacoesPagamento As autorizacoes de pagamento para calculo do valor restante
     * @param valorNota O valor da nota fiscal
     * @return O saldo restante para emissao de nota
     */
    public BigDecimal obterValorRestanteParaSubirNota(List<AutorizacaoPagamento> autorizacoesPagamento, BigDecimal valorNota){
        BigDecimal valorTotalAutorizacoes = BigDecimal.ZERO;
        BigDecimal valorTotalJaEmitido = BigDecimal.ZERO;
        for(AutorizacaoPagamento autorizacao : autorizacoesPagamento){
            valorTotalJaEmitido = valorTotalJaEmitido.add(autorizacao.getValorEmitido().setScale(2, BigDecimal.ROUND_HALF_UP));
            valorTotalAutorizacoes = valorTotalAutorizacoes.add(autorizacao.getValorTotal().setScale(2, BigDecimal.ROUND_HALF_UP));
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
     * Verifica se uma nota fiscal eh uma versao de homologacao
     *
     * @param documento documento que representa o Xml da nota parseada
     * @return true caso seja de homologacao
     */
    public boolean isHomologacao(Document documento) {
        String razaoSocial = UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.DEST_RAZAO_SOCIAL);
        String inscrEstad = UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.DEST_INSC_ESTADUAL);
        String cnpj = UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.DEST_CNPJ);
        return ConstantesNotaFiscalParser.RAZAO_SOCIAL_NOTA_HOMOLOG.equalsIgnoreCase(razaoSocial)
                && ConstantesNotaFiscalParser.INSCR_ESTADU_NOTA_HOMOLOG.equals(inscrEstad)
                && ConstantesNotaFiscalParser.CNPJ_NOTA_HOMOLOG.equals(cnpj);
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
        DanfeVo danfe = new DanfeVo();
        danfe.setEmitNome(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.EMIT_RAZAO_SOCIAL));
        danfe.setDhEmiDataEmissao(UtilitarioFormatacaoData.formatarDataHoraDanfe(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.DATA_EMISSAO), ConstantesFormatacao.FORMATO_DATA_CURTA));
        danfe.setDestNome(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.DEST_RAZAO_SOCIAL));
        danfe.setValorTotalNota(UtilitarioXml.getBigDecimal(documento, ConstantesNotaFiscalParser.VALOR_TOTAL));

        danfe.setNumeroDaNota(UtilitarioFormatacao.formatarNumeroNota(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.NUMERO)));
        danfe.setSerieNota(UtilitarioFormatacao.formatarSerieNota(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.SERIE)));

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
    private String getModalidadeFrete(Serializable modalidade) {
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
        }
        catch(ExcecaoValidacao e) {
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
        }
        else if (danfe.getNumeroDaNota() == null || StringUtils.isEmpty(danfe.getNumeroDaNota()) ) {
            return mensagens.obterMensagem(Erro.ERRO_VALIDACAO_NUMERO_NOTA.getChaveMensagem());
        }
        else if (danfe.getDestCnpjCpf() == null || StringUtils.isEmpty(danfe.getDestCnpjCpf()) ) {
            return mensagens.obterMensagem(Erro.ERRO_VALIDACAO_CNPJ_DEST.getChaveMensagem());
        }
        else if (danfe.getEmitCnpj() == null || StringUtils.isEmpty(danfe.getEmitCnpj())) {
            return mensagens.obterMensagem(Erro.ERRO_VALIDACAO_CNPJ_EMIT.getChaveMensagem());
        }
        else if (danfe.getDhEmiDataEmissao() == null || StringUtils.isEmpty(danfe.getDhEmiDataEmissao()) ) {
            return mensagens.obterMensagem(Erro.ERRO_VALIDACAO_DATA_EMISSAO.getChaveMensagem());
        }
        else if (danfe.getSerieNota() == null ||StringUtils.isEmpty(danfe.getSerieNota()) ) {
            return mensagens.obterMensagem(Erro.ERRO_VALIDACAO_SERIE_NOTA.getChaveMensagem());
        }
        else {
            return null;
        }
    }
}