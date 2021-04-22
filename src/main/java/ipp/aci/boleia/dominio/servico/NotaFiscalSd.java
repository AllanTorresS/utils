package ipp.aci.boleia.dominio.servico;

import com.google.common.collect.ImmutableList;
import ipp.aci.boleia.dados.IArquivoDados;
import ipp.aci.boleia.dados.IComponenteDados;
import ipp.aci.boleia.dados.IEmpresaAgregadaDados;
import ipp.aci.boleia.dados.IFrotaDados;
import ipp.aci.boleia.dados.INfeAnexosArmazemDados;
import ipp.aci.boleia.dados.INotaFiscalDados;
import ipp.aci.boleia.dados.ITipoCombustivelDados;
import ipp.aci.boleia.dados.ITransacaoConsolidadaDados;
import ipp.aci.boleia.dados.IUnidadeDados;
import ipp.aci.boleia.dominio.Arquivo;
import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.NfeAnexosArmazem;
import ipp.aci.boleia.dominio.NotaFiscal;
import ipp.aci.boleia.dominio.TipoCombustivel;
import ipp.aci.boleia.dominio.TipoCombustivelNcm;
import ipp.aci.boleia.dominio.TransacaoConsolidada;
import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.dominio.enums.LocalDestinoPadroNfe;
import ipp.aci.boleia.dominio.enums.TipoArquivo;
import ipp.aci.boleia.dominio.historico.HistoricoParametroNotaFiscal;
import ipp.aci.boleia.dominio.vo.DanfeVo;
import ipp.aci.boleia.dominio.vo.ItemDanfeVo;
import ipp.aci.boleia.dominio.vo.NotaFiscalVo;
import ipp.aci.boleia.dominio.vo.ValidacaoUploadNotaFiscalVo;
import ipp.aci.boleia.util.ConstantesFormatacao;
import ipp.aci.boleia.util.ConstantesNotaFiscal;
import ipp.aci.boleia.util.ConstantesNotaFiscalParser;
import ipp.aci.boleia.util.UtilitarioCalculo;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.UtilitarioFormatacaoData;
import ipp.aci.boleia.util.UtilitarioInputStream;
import ipp.aci.boleia.util.UtilitarioLambda;
import ipp.aci.boleia.util.UtilitarioParse;
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
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Oferece funcionalidades para a Nota fiscal
 */
@Component
public class NotaFiscalSd {

    private static final Logger LOG = LoggerFactory.getLogger(NotaFiscalSd.class);
    private static final int LIMITE_NO_SERIE = 10;
    private static final List<Erro> ERROS_BLOQUEANTES = ImmutableList.of(Erro.NOTA_FISCAL_UPLOAD_VERSAO_INVALIDA, Erro.NOTA_FISCAL_UPLOAD_NOTA_REPETIDA);

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
    private INfeAnexosArmazemDados nfeAnexosArmazemDados;

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

    @Autowired
    private ITipoCombustivelDados tipoCombustivelDados;

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
     * Verifica o conteudo das notas fiscais para verificacao de coerencia com o consolidado correspondente
     *
     * @param documentos lista de documentos que representam os Xmls das notas parseadas
     * @param autorizacoesPagamento a lista de abastecimentos referentes à nota fiscal.
     * @return lista de mensagens das validacoes de erro
     */
    public List<ValidacaoUploadNotaFiscalVo> validarDadosNovaNotaFiscal(List<Document> documentos, List<AutorizacaoPagamento> autorizacoesPagamento) {
        List<ValidacaoUploadNotaFiscalVo> validacoesNotas = new ArrayList<>();
        validarVersaoNota(documentos, validacoesNotas);
        if (validacoesNotas.isEmpty()){
            validarNumeroSerieNota(documentos, validacoesNotas);
            validarNumeroNota(documentos, validacoesNotas);
            validarNotaRepetida(documentos, validacoesNotas);
            validarDadosNota(documentos, autorizacoesPagamento, validacoesNotas);
        }
        return validacoesNotas;
    }

    /**
     * Verifica se o documento contém o número da nota preenchido
     * @param documentos Documento quem contém os dados da nota fiscal
     * @param validacoesNotas Lista de erros das validacoes
     */
    private void validarNumeroNota(List<Document> documentos, List<ValidacaoUploadNotaFiscalVo> validacoesNotas) {
        for(Document documento : documentos) {
            String nNfe = notaFiscalParserSd.getString(documento, ConstantesNotaFiscalParser.NUMERO);
            if (nNfe == null || nNfe.isEmpty()) {
                this.addErroValidacao(validacoesNotas, documento, Erro.NOTA_FISCAL_UPLOAD_NUMERO_OBRIGATORIO);
            }
        }
    }

    /**
     * Verifica se o documento contém o número de serie preenchido
     * @param documentos Documento quem contém os dados da nota fiscal
     * @param validacoesNotas Lista de erros das validacoes
     */
    private void validarNumeroSerieNota(List<Document> documentos, List<ValidacaoUploadNotaFiscalVo> validacoesNotas) {
        for (Document documento : documentos) {
            String nfSerie = notaFiscalParserSd.getString(documento, ConstantesNotaFiscalParser.SERIE);
            Erro erro = null;
            if (nfSerie == null || nfSerie.isEmpty()) {
                erro = Erro.NOTA_FISCAL_UPLOAD_NUMERO_SERIE_OBRIGATORIO;
            } else if (nfSerie.length() > LIMITE_NO_SERIE) {
                erro = Erro.NOTA_FISCAL_UPLOAD_NUMERO_SERIE_LIMITE;
            }

            if(erro != null){
                this.addErroValidacao(validacoesNotas, documento, erro);
            }
        }
    }

    /**
     * Exclui uma nota fiscal.
     *
     * @param notaFiscal Nota fiscal que será excluída.
     */
    public void excluirNotaFiscal(NotaFiscal notaFiscal) {
        NfeAnexosArmazem nfeAnexosArmazem = nfeAnexosArmazemDados.obterPorNotaFiscal(notaFiscal.getId());
        if (nfeAnexosArmazem != null) {
            nfeAnexosArmazemDados.excluir(nfeAnexosArmazem.getId());
        }
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
     * Obtém o valor dos combustíveis de uma nota fiscal
     * @param nota A nota fiscal
     * @return O valor de combustíveis
     */
    public BigDecimal obterValorTotalCombustivelNota(Document nota) {
        return calcularValoresNota(nota).getLeft();
    }

    /**
     * Obtém o valor dos produtos de uma nota fiscal
     * @param nota A nota fiscal
     * @return O valor de produtos
     */
    public BigDecimal obterValorTotalProdutosNota(Document nota) {
        return calcularValoresNota(nota).getRight();
    }

    /**
     * Obtém o valor dos combustíveis de uma nota fiscal
     * @param itensNota Lista de itens da nota
     * @return O valor de combustíveis
     */
    public BigDecimal obterValorTotalCombustivelNota(List<ItemDanfeVo> itensNota) {
        List<Long> ncms = obterCodigosNcmCombustivel();
        return UtilitarioCalculo.somarValoresLista(itensNota.stream().filter(obterFuncaoFilterCalculoValorTotal(true, ncms)), obterFuncaoMapeadoraValorLiquido());
    }

    /**
     * Obtém o valor dos produtos de uma nota fiscal
     * @param itensNota Lista de itens da nota
     * @return O valor de produtos
     */
    public BigDecimal obterValorTotalProdutosNota(List<ItemDanfeVo> itensNota) {
        List<Long> ncms = obterCodigosNcmCombustivel();
        return UtilitarioCalculo.somarValoresLista(itensNota.stream().filter(obterFuncaoFilterCalculoValorTotal(false, ncms)), obterFuncaoMapeadoraValorLiquido());
    }

    /**
     * Obtém função mapeadora para totalizar valores de nota fiscal
     * @return A função mapeadora
     */
    private Function<ItemDanfeVo, BigDecimal> obterFuncaoMapeadoraValorLiquido() {
        return item -> new BigDecimal(item.getValorLiquido());
    }

    /**
     * Obtém códigos NCM relativos a combustíveis
     * @return A lista de códigos NCM
     */
    private List<Long> obterCodigosNcmCombustivel() {
        List<TipoCombustivel> tiposCombustivel = tipoCombustivelDados.obterTodos(null);
        return tiposCombustivel.stream().map(tipoCombustivel -> tipoCombustivel.getCodigosNcm().stream().map(TipoCombustivelNcm::getCodigoNcm).collect(Collectors.toList())).flatMap(List::stream).collect(Collectors.toList());
    }

    /**
     * Monta predicado para função de filter para determinar itens de combustível ou produto de uma nota
     * @param isCombustivel Se o filtro deve ser feito para itens de combustível
     * @param ncms Lista de NCMs que representam combustíveis
     * @return A função mapeadora
     */
    private Predicate<ItemDanfeVo> obterFuncaoFilterCalculoValorTotal(Boolean isCombustivel, List<Long> ncms) {
        return isCombustivel ? item -> ncms.contains(UtilitarioParse.tryParseLong(item.getNcmsh())) : item -> !ncms.contains(UtilitarioParse.tryParseLong(item.getNcmsh()));
    }

    /**
     * Calcula os valores unitários de combustível e produtos
     * @param nota A nota fiscal
     * @return Os valores calculados em formato de par (esquerda - valor de combustível / direita - valor de produtos)
     */
    private Pair<BigDecimal, BigDecimal> calcularValoresNota(Document nota) {
        NodeList itensNota = notaFiscalParserSd.getItens(nota);
        BigDecimal valorCombustivel = BigDecimal.ZERO;
        BigDecimal valorProdutos = BigDecimal.ZERO;
        List<TipoCombustivel> combustiveis = tipoCombustivelDados.obterTodos(null);

        if(itensNota != null){
            for(int i = 0; i < itensNota.getLength(); i++) {
                Node item = itensNota.item(i);
                Long ncmItem = notaFiscalParserSd.getLong(nota, ConstantesNotaFiscalParser.ITEM_NCM, item);
                BigDecimal valor = notaFiscalParserSd.getBigDecimal(nota, ConstantesNotaFiscalParser.ITEM_VALOR_LIQUIDO, item);
                Boolean isCombustivel = combustiveis
                        .stream()
                        .anyMatch(
                                comb -> comb.getCodigosNcm()
                                        .stream()
                                        .anyMatch(ncm -> ncm.getCodigoNcm().equals(ncmItem))
                        );
                if (isCombustivel) {
                    valorCombustivel = valorCombustivel.add(valor);
                } else {
                    valorProdutos = valorProdutos.add(valor);
                }
            }
        }
        valorCombustivel = valorCombustivel.compareTo(BigDecimal.ZERO) > 0 ? valorCombustivel : null;
        valorProdutos = valorProdutos.compareTo(BigDecimal.ZERO) > 0 ? valorProdutos : null;
        return Pair.of(valorCombustivel, valorProdutos);
    }

    /**
     * Verifica o conteúdo da nota fiscal para verificação de coerência com os dados editados no abastecimento.
     *
     * @param documentos documentos que representa o Xml da nota parseada.
     * @param autorizacoesPagamento a lista de abastecimentos referentes à nota fiscal.
     * @param validacoesNotas Lista de erros das validacoes
     */
    private void validarDadosNota(List<Document> documentos, List<AutorizacaoPagamento> autorizacoesPagamento, List<ValidacaoUploadNotaFiscalVo> validacoesNotas) {
        validarCNPJDestinatario(documentos, autorizacoesPagamento, validacoesNotas);
        validarCNPJEmitente(documentos, autorizacoesPagamento, validacoesNotas);
        validarValorTotal(documentos, validacoesNotas);
        validarValoresTotaisCombustivelProdutos(documentos, autorizacoesPagamento, validacoesNotas);
    }

    /**
     * Verifica o conteúdo da nota fiscal para conferência de valores de abastecimento e produtos
     * @param documentos As notas fiscais
     * @param autorizacoesPagamento Os abastecimentos selecionados para emissão
     * @param validacoesNotas Lista de erros das validacoes
     */
    private void validarValoresTotaisCombustivelProdutos(List<Document> documentos, List<AutorizacaoPagamento> autorizacoesPagamento, List<ValidacaoUploadNotaFiscalVo> validacoesNotas) {
        AutorizacaoPagamento autorizacaoPagamento = UtilitarioLambda.obterPrimeiroObjetoDaLista(autorizacoesPagamento);
        Document documento = documentos.size() == 1 ? documentos.get(0) : null;
        HistoricoParametroNotaFiscal parametroNf = autorizacaoPagamento.getParametroNotaFiscal();
        BigDecimal valorCombustivelRestante = obterValorTotalCombustivelRestanteEmissaoAutorizacoesPagamentoSelecionadas(autorizacoesPagamento).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal valorProdutosRestante = obterValorTotalProdutosRestanteEmissaoAutorizacoesPagamentoSelecionadas(autorizacoesPagamento).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal margemAbastecimentos = obterValorMargemTotal(autorizacoesPagamento.size()).setScale(2, BigDecimal.ROUND_HALF_UP);

        BigDecimal valorTotalCombustivelNota = documentos.stream().map(this::obterValorTotalCombustivelNota).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal valorTotalProdutoNota = documentos.stream().map(this::obterValorTotalProdutosNota).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, BigDecimal.ROUND_HALF_UP);

        if (parametroNf != null && parametroNf.getSepararPorCombustivelProdutoServico() != null && parametroNf.getSepararPorCombustivelProdutoServico()) {
            boolean algumaNotaNaoSeparada = documentos.stream().anyMatch(nota -> obterValorTotalCombustivelNota(nota) != null && obterValorTotalProdutosNota(nota) != null);
            if (algumaNotaNaoSeparada) {
                this.addErroValidacao(validacoesNotas, documento, Erro.NOTAS_FISCAIS_SEPARADAS_NAO_ENCONTRADAS);
            }
            if (
                    (valorCombustivelRestante.compareTo(BigDecimal.ZERO) > 0 && valorTotalCombustivelNota.compareTo(BigDecimal.ZERO) == 0)
                    || (valorProdutosRestante.compareTo(BigDecimal.ZERO) > 0 && valorTotalProdutoNota.compareTo(BigDecimal.ZERO) == 0)
            ) {
                this.addErroValidacao(validacoesNotas, documento, Erro.NOTA_FISCAL_COMB_OU_PROD_AUSENTE);
            }
        }

        BigDecimal diferencaCombustivel = valorTotalCombustivelNota.subtract(valorCombustivelRestante).abs().setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal diferencaProdutos = valorTotalProdutoNota.subtract(valorProdutosRestante).abs().setScale(2, BigDecimal.ROUND_HALF_UP);
        if ((diferencaCombustivel.compareTo(margemAbastecimentos) > 0)) {
            if (valorTotalCombustivelNota.compareTo(valorCombustivelRestante) > 0) {
                this.addErroValidacao(validacoesNotas, null, Erro.NOTA_FISCAL_VALOR_COMB_EXCEDENTE);
            } else {
                BigDecimal valorFaltante = valorCombustivelRestante.subtract(valorTotalCombustivelNota);
                this.addErroValidacao(validacoesNotas, documento, Erro.NOTA_FISCAL_VALOR_COMB_FALTANTE, valorFaltante);
            }
        }
        if ((diferencaProdutos.compareTo(margemAbastecimentos) > 0)) {
            if (valorTotalProdutoNota.compareTo(valorProdutosRestante) > 0) {
                this.addErroValidacao(validacoesNotas, documento, Erro.NOTA_FISCAL_VALOR_PROD_EXCEDENTE);
            } else {
                BigDecimal valorFaltante = valorProdutosRestante.subtract(valorTotalProdutoNota);
                this.addErroValidacao(validacoesNotas, documento, Erro.NOTA_FISCAL_VALOR_PROD_FALTANTE, valorFaltante);
            }
        }
    }

    /**
     * Calcula o valor restante para emissão em combustível nos abastecimentos selecionados
     * @param autorizacoesPagamento Os abastecimentos selecionados
     * @return O valor restante calculado
     */
    private BigDecimal obterValorTotalCombustivelRestanteEmissaoAutorizacoesPagamentoSelecionadas(List<AutorizacaoPagamento> autorizacoesPagamento) {
        List<AutorizacaoPagamento> abastecimentosComCombustivel = autorizacoesPagamento
                .stream()
                .filter(autorizacaoPagamento -> autorizacaoPagamento.obtemValorTotalAbastecimento() != null)
                .collect(Collectors.toList());
        BigDecimal valorTotalCombustivel = UtilitarioCalculo.somarValoresLista(abastecimentosComCombustivel, AutorizacaoPagamento::obtemValorTotalAbastecimento);
        BigDecimal valorEmitidoCombustivel = UtilitarioCalculo.somarValoresLista(abastecimentosComCombustivel, AutorizacaoPagamento::obtemValorEmitidoCombustivel);
        return valorTotalCombustivel.subtract(valorEmitidoCombustivel);
    }

    /**
     * Calcula o valor restante para emissão em produtos/serviços nos abastecimentos selecionados
     * @param autorizacoesPagamento Os abastecimentos selecionados
     * @return O valor restante calculado
     */
    private BigDecimal obterValorTotalProdutosRestanteEmissaoAutorizacoesPagamentoSelecionadas(List<AutorizacaoPagamento> autorizacoesPagamento) {
        List<AutorizacaoPagamento> abastecimentosComProduto = autorizacoesPagamento
                .stream()
                .filter(autorizacaoPagamento -> autorizacaoPagamento.obtemValorTotalProdutoServico() != null)
                .collect(Collectors.toList());
        BigDecimal valorTotalProduto = UtilitarioCalculo.somarValoresLista(abastecimentosComProduto, AutorizacaoPagamento::obtemValorTotalProdutoServico);
        BigDecimal valorEmitidoProduto = UtilitarioCalculo.somarValoresLista(abastecimentosComProduto, AutorizacaoPagamento::obtemValorEmitidoProdutoServico);
        return valorTotalProduto.subtract(valorEmitidoProduto);
    }

    /**
     * Verifica o conteúdo da nota fiscal para verificação de coerência com os dados editados no abastecimento.
     *
     * @param documento documento que representa o Xml da nota parseada.
     * @param autorizacoesPagamento a lista de abastecimentos referentes à nota fiscal.
     * @throws ExcecaoValidacao Caso a nota seja inválida.
     */
    private void validarDadosNotaEdicaoAbastecimento(Document documento, List<AutorizacaoPagamento> autorizacoesPagamento) throws ExcecaoValidacao {
        List<ValidacaoUploadNotaFiscalVo> validacoesNotas = new ArrayList<>();
        validarCNPJDestinatario(Collections.singletonList(documento), autorizacoesPagamento, validacoesNotas);
        validarCNPJEmitente(Collections.singletonList(documento), autorizacoesPagamento, validacoesNotas);
        validarValorTotalEdicaoAbastecimento(documento, autorizacoesPagamento, validacoesNotas);

        if (!validacoesNotas.isEmpty()) {
            List<String> mensagens = validacoesNotas.stream().map(m-> m.getNumero() + " " + m.getErro()).collect(Collectors.toList());
            throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO_NOTA_FISCAL, mensagens);
        }
    }

    /**
     * Verifica as versões das notas fiscais
     *
     * @param documentos documentos que representam os Xmls das notas parseadas
     * @param validacoesNotas Lista de erros das validacoes
     */
    private void validarVersaoNota(List<Document> documentos, List<ValidacaoUploadNotaFiscalVo> validacoesNotas) {
        for (Document documento : documentos) {
            if (notaFiscalParserSd.isHomologacao(documento) || !ValidadorCnpj.isValidCNPJ(notaFiscalParserSd.getString(documento, ConstantesNotaFiscalParser.DEST_CNPJ))
                    || !ValidadorCnpj.isValidCNPJ(notaFiscalParserSd.getString(documento, ConstantesNotaFiscalParser.EMIT_CNPJ))) {
                this.addErroValidacao(validacoesNotas, documento, Erro.NOTA_FISCAL_UPLOAD_VERSAO_INVALIDA);
            }
        }
    }

    /**
     * Adiciona o erro da validacao na lista de validações
     * @param validacoesNotas lista de validações
     * @param documento xml da nota fiscal
     * @param erro erro da validacao
     * @param argumentos argumentos da validação
     */
    private void addErroValidacao(List<ValidacaoUploadNotaFiscalVo> validacoesNotas, Document documento, Erro erro, Object ... argumentos){
        if(validacoesNotas == null || erro == null){
            return;
        }

        final String numeroNfe = documento != null ? ConstantesNotaFiscal.NOTA_FISCAL_PREFIX + notaFiscalParserSd.getString(documento, ConstantesNotaFiscalParser.NUMERO) + "-" + notaFiscalParserSd.getString(documento, ConstantesNotaFiscalParser.SERIE) : "";
        if (validacoesNotas.stream().anyMatch(v-> numeroNfe.equals(v.getNumero()) && ERROS_BLOQUEANTES.contains(v.getErro()))){
            return;
        }

        if(validacoesNotas.stream().anyMatch(v -> numeroNfe.equals(v.getNumero()) && erro.equals(v.getErro()))) {
            return;
        }

        final BigDecimal valorTotal = documento != null ? notaFiscalParserSd.getBigDecimal(documento, ConstantesNotaFiscalParser.VALOR_TOTAL) : null;

        ValidacaoUploadNotaFiscalVo validacaoNF = new ValidacaoUploadNotaFiscalVo();
        validacaoNF.setNumero(numeroNfe);
        validacaoNF.setErro(erro);
        validacaoNF.setValorTotal(valorTotal);
        validacaoNF.getArgumentos().addAll(Arrays.asList(argumentos));

        String mensagem;
        switch(erro){
            case NOTA_FISCAL_VALOR_PROD_FALTANTE:
            case NOTA_FISCAL_VALOR_COMB_FALTANTE:
                mensagem = mensagens.obterMensagem(erro.getChaveMensagem(), UtilitarioFormatacao.formatarDecimalMoedaReal((BigDecimal)argumentos[0]));
                break;
            case NOTA_FISCAL_UPLOAD_CNPJ_DESTINATARIO_INVALIDO :
                mensagem =  mensagens.obterMensagem(erro.getChaveMensagem(), UtilitarioFormatacao.formatarCnpjApresentacao((String)argumentos[0]));
                break;
            case NOTAS_FISCAIS_REPETIDAS_NO_UPLOAD:
                mensagem = mensagens.obterMensagem(erro.getChaveMensagem(), numeroNfe, UtilitarioFormatacao.formatarCnpjApresentacao(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.EMIT_CNPJ)) );
                validacaoNF.setNumero("");
                validacaoNF.setValorTotal(null);
                break;
            default:
                mensagem = mensagens.obterMensagem(erro.getChaveMensagem());
        }
        validacaoNF.setMensagem(mensagem);

        if (ERROS_BLOQUEANTES.contains(erro)){
            validacoesNotas.removeIf(v-> numeroNfe.equals(v.getNumero()));
        }

        validacoesNotas.add(validacaoNF);
    }

    /**
     * Verifica se a nota já foi incluida.
     *
     * @param documentos documentos que representão os Xmls das notas parseadas
     * @param validacoesNotas Lista de erros das validacoes
     */
    private void validarNotaRepetida(List<Document> documentos, List<ValidacaoUploadNotaFiscalVo> validacoesNotas) {
        documentos.stream()
                .collect(Collectors.groupingBy(d-> UtilitarioXml.getString(d, ConstantesNotaFiscalParser.NUMERO)
                    + "," + UtilitarioXml.getString(d, ConstantesNotaFiscalParser.SERIE)
                    + "," + UtilitarioXml.getString(d, ConstantesNotaFiscalParser.EMIT_CNPJ)))
                .forEach((k,v)->{
                    if (v.size() > 1){
                        this.addErroValidacao(validacoesNotas, v.get(0), Erro.NOTAS_FISCAIS_REPETIDAS_NO_UPLOAD);
                    }
                });

        for (Document documento : documentos) {
            List<NotaFiscal> notasFiscais = repositorio.obterNotaPorNumero(ConstantesNotaFiscal.NOTA_FISCAL_PREFIX + notaFiscalParserSd.getString(documento, ConstantesNotaFiscalParser.NUMERO));
            if (notasFiscais != null) {
                Optional<NotaFiscal> notaFiscal = notasFiscais.stream()
                        .filter(nf -> possuiSerieCnpjEmitenteIguais(nf, documento))
                        .findFirst();

                if (notaFiscal.isPresent()) {
                    NotaFiscal notaFiscalRepetida = notaFiscal.get();
                    final Optional<AutorizacaoPagamento> autorizacaoPagamentoOptional = notaFiscalRepetida.getAutorizacoesPagamento().stream()
                            .findFirst();
                    if(autorizacaoPagamentoOptional.isPresent()){
                        final TransacaoConsolidada consolidada = transacaoConsolidadaDados.obterConsolidadoParaAbastecimento(autorizacaoPagamentoOptional.get().getId());
                        final NotaFiscalVo nota = new NotaFiscalVo(notaFiscalRepetida, consolidada, autorizacaoPagamentoOptional.get());
                        this.addErroValidacao(validacoesNotas, documento, Erro.NOTA_FISCAL_UPLOAD_NOTA_REPETIDA, nota);
                    }
                }
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
     * @param documentos documentos que representão os Xmls das notas parseadas
     * @param autorizacoesPagamento a transacao consolidada correpondente
     * @param validacoesNotas Lista de erros das validacoes
     */
    private void validarCNPJDestinatario(List<Document> documentos, List<AutorizacaoPagamento> autorizacoesPagamento, List<ValidacaoUploadNotaFiscalVo> validacoesNotas) {
        for(AutorizacaoPagamento abastecimento : autorizacoesPagamento) {
            final Long cnpjValidacao = obterCnpjDestino(abastecimento);
            documentos.forEach(nota -> {
                Long destCnpj = notaFiscalParserSd.getLong(nota, ConstantesNotaFiscalParser.DEST_CNPJ);
                boolean destinoConfiguradoIgualDestinoNFe = cnpjValidacao != null && cnpjValidacao.equals(destCnpj);
                if(!destinoConfiguradoIgualDestinoNFe){
                    this.addErroValidacao(validacoesNotas, nota, Erro.NOTA_FISCAL_UPLOAD_CNPJ_DESTINATARIO_INVALIDO, cnpjValidacao.toString());
                }
            });
        }
    }

    /**
     * Validar o local de destino da nota fiscal segundo a parametrização da frota
     * @param abastecimento O abastecimento que contém os dados a serem validados
     * @param cnpjDest O cnpj do local de destino oriundo da nota
     * @throws ExcecaoValidacao Caso a validação falhe
     */
    public void validarLocalDestino(AutorizacaoPagamento abastecimento, Long cnpjDest) throws ExcecaoValidacao {
        Long cnpjDestino = obterCnpjDestino(abastecimento);
        if(!cnpjDestino.equals(cnpjDest)){
            throw new ExcecaoValidacao(mensagens.obterMensagem("nota.fiscal.validacao.local.destino", cnpjDestino));
        }
    }

    /**
     * Obtém o CNPJ correto de destino para emissão de um abastecimento
     * @param abastecimento O abastecimento a ser emitido
     * @return O CNPJ de destino correto
     */
    public Long obterCnpjDestino(AutorizacaoPagamento abastecimento) {
        Long cnpjDestino;
        Veiculo veiculo = abastecimento.getVeiculo();
        boolean veiculoTemUnidade = veiculo != null && veiculo.getUnidade() != null;
        boolean veiculoTemEmpresaAgregada = veiculo != null && veiculo.getEmpresaAgregada() != null;
        if (abastecimento.getParametroNotaFiscal() != null) {
            HistoricoParametroNotaFiscal parametroNf = abastecimento.getParametroNotaFiscal();
            if (parametroNf != null && LocalDestinoPadroNfe.ABASTECIMENTO.getValue().equals(parametroNf.getLocalDestino())) {
                String uf = abastecimento.getPontoVenda().getUf();
                Optional<Long> cnpjUnidadeLocalDestinoPadrao = parametroNf.getParametroNotaFiscalUfs()
                        .stream()
                        .filter(p -> p.getUf().equals(uf))
                        .map(p -> p.getUnidadeLocalDestino() != null ? p.getUnidadeLocalDestino().getCnpj() : abastecimento.getCnpjFrota())
                        .findFirst();
                cnpjDestino = cnpjUnidadeLocalDestinoPadrao.orElseGet(
                        () -> parametroNf.getUnidadeLocalDestinoPadrao() != null
                        ? parametroNf.getUnidadeLocalDestinoPadrao().getCnpj()
                        : abastecimento.getCnpjFrota()
                );
            } else if (parametroNf != null && LocalDestinoPadroNfe.VEICULO.getValue().equals(parametroNf.getLocalDestino()) && veiculoTemUnidade) {
                cnpjDestino = veiculo.getUnidade().getCnpj();
            } else if (parametroNf != null && LocalDestinoPadroNfe.VEICULO.getValue().equals(parametroNf.getLocalDestino()) && veiculoTemEmpresaAgregada) {
                cnpjDestino = veiculo.getEmpresaAgregada().getCnpj();
            } else {
                cnpjDestino = abastecimento.getFrota().getCnpj();
            }
        } else {
            cnpjDestino = getFrotaResponsavelAbastecimentos(Collections.singletonList(abastecimento));
        }
        return cnpjDestino;
    }

    /**
     * Calcula o valor total da nota baseado nos somatórios de suas partes
     * @param nota A nota fiscal
     * @return O valor total calculado
     */
    public BigDecimal obterValorTotalNota(Document nota) {
        BigDecimal valorCombustivel = obterValorTotalCombustivelNota(nota);
        BigDecimal valorProdutos = obterValorTotalProdutosNota(nota);
        valorCombustivel = valorCombustivel != null ? valorCombustivel : BigDecimal.ZERO;
        valorProdutos = valorProdutos != null ? valorProdutos : BigDecimal.ZERO;
        return valorCombustivel.add(valorProdutos).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Verifica o cnpj do emitente
     *
     * @param documentos documentos que representão os Xmls das notas parseadas
     * @param autorizacoesPagamento a transacao consolidade correspondente
     * @param validacoesNotas Lista de erros das validacoes
     */
    private void validarCNPJEmitente(List<Document> documentos, List<AutorizacaoPagamento> autorizacoesPagamento, List<ValidacaoUploadNotaFiscalVo> validacoesNotas) {
        for(Document documento : documentos) {
            Long emitCnpj = notaFiscalParserSd.getLong(documento, ConstantesNotaFiscalParser.EMIT_CNPJ);
            Long cnpjEmitente = getPontoVendaResponsavelAbastecimentos(autorizacoesPagamento);
            boolean emitCnpjOk = cnpjEmitente.equals(emitCnpj);
            if (!emitCnpjOk) {
                this.addErroValidacao(validacoesNotas, documento, Erro.NOTA_FISCAL_UPLOAD_CNPJ_EMITENTE_INVALIDO);
            }
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
     * @param documentos documentos que representão os Xmls das notas parseadas
     * @param validacoesNotas Lista de erros das validacoes
     */
    private void validarValorTotal(List<Document> documentos, List<ValidacaoUploadNotaFiscalVo> validacoesNotas) {
        documentos.forEach(documento -> {
            BigDecimal valorCombustivel = obterValorTotalCombustivelNota(documento);
            BigDecimal valorTotalCombustivel = valorCombustivel != null ? valorCombustivel.setScale(2, BigDecimal.ROUND_HALF_UP) : BigDecimal.ZERO;

            BigDecimal valorProdutos = obterValorTotalProdutosNota(documento);
            BigDecimal valorTotalProduto = valorProdutos != null ? valorProdutos.setScale(2, BigDecimal.ROUND_HALF_UP) : BigDecimal.ZERO;

            BigDecimal valorTotalItensNota = valorTotalProduto.add(valorTotalCombustivel);
            BigDecimal valorDescontoTotal = notaFiscalParserSd.getBigDecimal(documento, ConstantesNotaFiscalParser.VALOR_DESCONTO);
            BigDecimal valorTotalBrutoNota = notaFiscalParserSd.getBigDecimal(documento, ConstantesNotaFiscalParser.VALOR_TOTAL);
            if (valorTotalBrutoNota != null) {
                BigDecimal valorTotalNota = valorDescontoTotal !=null ? valorTotalBrutoNota.add(valorDescontoTotal) : valorTotalBrutoNota;
                valorTotalNota = valorTotalNota.setScale(2, BigDecimal.ROUND_HALF_UP);
                if(valorTotalItensNota.compareTo(valorTotalNota) != 0) {
                    addErroValidacao(validacoesNotas, documento, Erro.NOTA_FISCAL_UPLOAD_VERSAO_INVALIDA);
                }
            } else{
                addErroValidacao(validacoesNotas, documento, Erro.NOTA_FISCAL_NAO_POSSUI_VALOR_TOTAL);
            }
        });
    }

    /**
     *
     * Verifica a consistência do valor total da nota fiscal para edição.
     * @param documento documento que representa o Xml da nota parseada.
     * @param autorizacoesPagamento a transacao consolidada correpondente.
     * @param validacoesNotas validacoes dos erros encontrados.
     */
    private void validarValorTotalEdicaoAbastecimento(Document documento, List<AutorizacaoPagamento> autorizacoesPagamento, List<ValidacaoUploadNotaFiscalVo> validacoesNotas) {
        BigDecimal valorTotalNota = notaFiscalParserSd.getBigDecimal(documento, ConstantesNotaFiscalParser.VALOR_TOTAL).setScale(2, BigDecimal.ROUND_HALF_UP);
        if (valorTotalNota != null){
            valorTotalNota = valorTotalNota.setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal diferenca = obterValorRestanteParaSubirNota(autorizacoesPagamento, valorTotalNota, Boolean.TRUE);
            final Erro erro = validarValorRestanteParaSubir(autorizacoesPagamento, diferenca);
            this.addErroValidacao(validacoesNotas, documento, erro);
        } else {
            this.addErroValidacao(validacoesNotas, documento, Erro.NOTA_FISCAL_NAO_POSSUI_VALOR_TOTAL);
        }
    }

    /**
     * Valida o valor restante para subir da nota fiscal.
     *
     * @param autorizacoesPagamento Autorizações de pagamento da nota
     * @param valorRestante Valor restante para subir.
     * @return erro
     */
    private Erro validarValorRestanteParaSubir(List<AutorizacaoPagamento> autorizacoesPagamento, BigDecimal valorRestante) {
        valorRestante = valorRestante.setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal zero = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        if (autorizacoesPagamento.size() > 1) {
            if (valorRestante.abs().compareTo(obterValorMargemTotal(autorizacoesPagamento.size())) > 0) {
                return Erro.NOTA_FISCAL_UPLOAD_TOTAL_INVALIDO_MULT_ABAST;
            }
        } else if(valorRestante.compareTo(zero.subtract(obterValorMargemTotal(autorizacoesPagamento.size()))) < 0) {
            return Erro.NOTA_FISCAL_UPLOAD_TOTAL_INVALIDO;
        }

        return null;
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
     * Verifica se as notas fiscais são notas válidas
     * @param documentos Documentos que representam os XMLs das notas parseadas
     * @return true caso seja válida, e false caso contrário
     */
    public Boolean verificaNotaValida(List<Document> documentos) {
        List<ValidacaoUploadNotaFiscalVo> validacoesNotas = new ArrayList<>();
        validarVersaoNota(documentos, validacoesNotas);
        return validacoesNotas.isEmpty();
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
                UtilitarioFormatacao.formatarCpfCnpjApresentacao(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.DEST_CNPJ)) :
                UtilitarioFormatacao.formatarCpfCnpjApresentacao(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.DEST_CPF)));
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
                UtilitarioFormatacao.formatarCpfCnpjApresentacao(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.TRANSP_CPF)) :
                UtilitarioFormatacao.formatarCpfCnpjApresentacao(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.TRANSP_CNPJ)));
        danfe.setTranspEndereco(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.TRANSP_ENDERECO));
        danfe.setTranspMunicipio(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.TRANSP_MUNICIPIO));
        danfe.setVeicTranspUf(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.TRANSP_VEICULO_UF));
        danfe.setTranspInscricaoEstadual(UtilitarioXml.getString(documento, ConstantesNotaFiscalParser.TRANSP_INSCRICAO_ESTADUAL));

        NodeList itens = UtilitarioXml.getItens(documento);
        if(itens != null){
            for (int i = 0; i < itens.getLength(); i++) {
                danfe.getDadosDanfe().add(getItemDanfeVo(documento, itens, i));
            }
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
            List<ValidacaoUploadNotaFiscalVo> validacoesNotas = new ArrayList<>();
            validarNotaRepetida(Collections.singletonList(nota), validacoesNotas);
            if(validacoesNotas.isEmpty()){
                return true;
            }else{
                validacoesNotas.forEach(m -> LOG.error(mensagens.obterMensagem("recolhanf.erro.unicidade"), m.getErro()));
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