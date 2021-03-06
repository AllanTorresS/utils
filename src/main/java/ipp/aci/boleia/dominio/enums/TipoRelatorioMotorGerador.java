package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.TemplatePlanilha;
import ipp.aci.boleia.util.i18n.IEnumComLabel;

import java.util.HashMap;
import java.util.Map;

/**
 * Indica o tipo do relatório emitido pelo motor de geração de relatórios
 */
public enum TipoRelatorioMotorGerador implements IEnumComLabel<TipoRelatorioMotorGerador> {

    RELATORIO_PRECO_EXPORTACAO(0, obterMapaTemplatesPrecoMicromercado()),
    RELATORIO_PRECO_POSTO(1, obterMapaTemplatesPrecoPosto()),
    RELATORIO_ABASTECIMENTO(2, obterMapaTemplatesAbastecimento()),
    RELATORIO_NOTAS_FISCAIS(3, obterMapaTemplatesNotasFiscais()),
    RELATORIO_MOTORISTA(4, obterMapaTemplatesMotorista()),
    RELATORIO_COBRANCA(5, obterMapaTemplatesCobranca()),
    RELATORIO_CICLO_REPASSE(6, obterMapaTemplatesCicloRepasse()),
    RELATORIO_PONTO_VENDA(7, obterMapaTemplatesPontoVenda()),
    RELATORIO_SALDO_FROTA(8, obterMapaTemplatesSaldoFrota()),
    RELATORIO_FROTA(9, obterMapaTemplatesFrota()),
    RELATORIO_POSTO_CREDENCIADO(10, obterMapaTemplatesPostoCredenciado()),
    RELATORIO_ABASTECIMENTOS_ESTORNADOS(11, obterMapaTemplatesAbastecimentosEstornados()),
    RELATORIO_ABASTECIMENTOS_AJUSTADOS(12, obterMapaTemplatesAbastecimentosAjustados()),
    RELATORIO_PRECO_NEGOCIADO(13, obterMapaTemplatesPrecoNegociado()),
    RELATORIO_NEGOCIACAO(14, obterMapaTemplatesNegociacao()),
    RELATORIO_COBRANCA_AGENCIADOR_FRETE(15, obterMapaTemplatesCobrancaAgenciador()),
    RELATORIO_ABASTECIMENTO_AGENCIADOR_FRETE(16, obterMapaTemplatesAbastecimentoAgenciadorFrete()),
    RELATORIO_REEMBOLSO_AGENCIADOR_FRETE(17, obterMapaTemplatesReembolsoAgenciadorFrete()),
	RELATORIO_FINANCEIRO_REVENDA(18, obterMapaTemplatesFinanceiroRevenda()),
    RELATORIO_ABASTECIMENTOS_NO_PERIODO(19, obterMapaTemplatesAbastecimentosPeriodo()),
    RELATORIO_ATIVACAO_TAG(20, obterMapaTemplatesAtivacaoTag()),
    RELATORIO_GERAL_FROTA(21, obterMapaTemplatesFrotaGeral()),
    RELATORIO_COBRANCA_CONECTCAR(22, obterMapaTemplatesCobrancaConectcar()),
    RELATORIO_COTA_VEICULO(23, obterMapaTemplatesCotaVeiculo()),    
	RELATORIO_UTILIZACAO_TAG(24, obterMapaTemplatesUtilizacaoTag()),
	RELATORIO_REEMBOLSO_CONECTCAR(25, obterMapaTemplatesReembolsoConectcar()),
	RELATORIO_FINANCEIRO_FROTA(26, obterMapaTemplatesFinanceiroFrota()),
    RELATORIO_DETALHAMENTO_COBRANCA(27, obterMapaTemplatesDetalhamentoCobranca()),
    RELATORIO_REEMBOLSO(28, obterMapaTemplatesReembolso()),
    RELATORIO_DETALHAMENTO_REEMBOLSO(29, obterMapaTemplatesDetalhamentoReembolso()),
    RELATORIO_ROTAS_INTELIGENTES(30, obterMapaTemplatesRotasInteligentes()),
    RELATORIO_REEMBOLSO_ANTECIPADO(31, obterMapaTemplatesReembolsoAntecipado()),
    RELATORIO_DETALHE_REEMBOLSO_ANTECIPADO(32, obterMapaTemplatesDetalheReembolsoAntecipado()),
    RELATORIO_ULTIMOS_PRECOS(33, obterMapaTemplatesUltimosPrecos()),
    RELATORIO_EXTRATO_VALE_PEDAGIO(34, obterMapaTemplatesExtratoValePedagio()),
	RELATORIO_DETALHE_VALE_PEDAGIO(35, obterMapaTemplatesDetalheValePedagio()),
    RELATORIO_TARIFADOR(36, obterMapaTemplatesTarifador());

    private final Integer value;
    private final Map<TipoPerfilUsuario, TemplatePlanilha> templatePerfil;

    /**
     * Construtor
     *  @param value O value do status
     */
    TipoRelatorioMotorGerador(Integer value, Map<TipoPerfilUsuario, TemplatePlanilha> templatePerfil) {
        this.value = value;
        this.templatePerfil = templatePerfil;
    }

    public Integer getValue() {
        return value;
    }

    public Map<TipoPerfilUsuario, TemplatePlanilha> getTemplatePerfil() {
        return templatePerfil;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static TipoRelatorioMotorGerador obterPorValor(Integer value) {
        for(TipoRelatorioMotorGerador status : TipoRelatorioMotorGerador.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }

    public TemplatePlanilha obterTemplatePorTipoPerfilUsuario(TipoPerfilUsuario tipoPerfilUsuario) {
        return templatePerfil.get(tipoPerfilUsuario);
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (preço micromercado)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesPrecoMicromercado(){
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.REVENDA, TemplatePlanilha.TEMPLATE_RELATORIO_PRECO_MICROMERCADO);
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_PRECO_MICROMERCADO);
        mapa.put(TipoPerfilUsuario.FROTA, TemplatePlanilha.TEMPLATE_RELATORIO_PRECO_MICROMERCADO);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (preço posto)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesPrecoPosto(){
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.REVENDA, TemplatePlanilha.TEMPLATE_RELATORIO_PRECO_BASE_REVENDA);
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_PRECO_BASE_SOLUCAO);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (abastecimento)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesAbastecimento(){
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.REVENDA, TemplatePlanilha.TEMPLATE_RELATORIO_ABASTECIMENTO_REVENDA);
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_ABASTECIMENTO_SOLUCAO);
        mapa.put(TipoPerfilUsuario.FROTA, TemplatePlanilha.TEMPLATE_RELATORIO_ABASTECIMENTO_FROTA);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (notas fiscais)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesNotasFiscais() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.REVENDA, TemplatePlanilha.TEMPLATE_RELATORIO_NOTAS_FISCAIS);
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_NOTAS_FISCAIS);
        mapa.put(TipoPerfilUsuario.FROTA, TemplatePlanilha.TEMPLATE_RELATORIO_NOTAS_FISCAIS);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (motorista)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesMotorista() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.FROTA, TemplatePlanilha.TEMPLATE_RELATORIO_MOTORISTA_FROTA);
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_MOTORISTA_SOLUCAO);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (cobrança)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesCobranca() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_COBRANCA_INTERNO);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (ciclo repasse)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesCicloRepasse() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_REPASSE_SOLUCAO);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (ponto venda)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesPontoVenda() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_PONTO_VENDA_SOLUCAO);
        mapa.put(TipoPerfilUsuario.FROTA, TemplatePlanilha.TEMPLATE_RELATORIO_PONTO_VENDA_FROTA);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (saldo frota)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesSaldoFrota() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_CREDITO_FROTA);
        mapa.put(TipoPerfilUsuario.FROTA, TemplatePlanilha.TEMPLATE_RELATORIO_CREDITO_FROTA);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (frota)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesFrota() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_FROTA_SOLUCAO);
        mapa.put(TipoPerfilUsuario.REVENDA, TemplatePlanilha.TEMPLATE_RELATORIO_FROTA_REVENDA);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (posto credenciado)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesPostoCredenciado() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_POSTO_CREDENCIADO_SOLUCAO);
        mapa.put(TipoPerfilUsuario.FROTA, TemplatePlanilha.TEMPLATE_RELATORIO_POSTO_CREDENCIADO_FROTA);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (preço negociado)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesPrecoNegociado() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_PRECO_NEGOCIADO_SOLUCAO);
        mapa.put(TipoPerfilUsuario.FROTA, TemplatePlanilha.TEMPLATE_RELATORIO_PRECO_NEGOCIADO_FROTA);
        mapa.put(TipoPerfilUsuario.REVENDA, TemplatePlanilha.TEMPLATE_RELATORIO_PRECO_NEGOCIADO_REVENDA);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (abastecimentos estornados)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesAbastecimentosEstornados() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_ABASTECIMENTOS_ESTORNADOS);
        mapa.put(TipoPerfilUsuario.FROTA, TemplatePlanilha.TEMPLATE_RELATORIO_ABASTECIMENTOS_ESTORNADOS);
        mapa.put(TipoPerfilUsuario.REVENDA, TemplatePlanilha.TEMPLATE_RELATORIO_ABASTECIMENTOS_ESTORNADOS);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (abastecimentos ajustados)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesAbastecimentosAjustados() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_ABASTECIMENTOS_AJUSTADOS);
        mapa.put(TipoPerfilUsuario.FROTA, TemplatePlanilha.TEMPLATE_RELATORIO_ABASTECIMENTOS_AJUSTADOS);
        mapa.put(TipoPerfilUsuario.REVENDA, TemplatePlanilha.TEMPLATE_RELATORIO_ABASTECIMENTOS_AJUSTADOS);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (negociação)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesNegociacao() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_NEGOCIACOES_SOLUCAO);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (cobrança agenciador)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesCobrancaAgenciador() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_COBRANCA_AGENCIADOR_FRETE);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (transação abastecimento agenciador)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesAbastecimentoAgenciadorFrete() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_TRANSACAO_AGENCIADOR_FRETE);
        mapa.put(TipoPerfilUsuario.REVENDA, TemplatePlanilha.TEMPLATE_RELATORIO_TRANSACAO_AGENCIADOR_FRETE_REVENDA);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (reembolso agenciador)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesReembolsoAgenciadorFrete() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_REEMBOLSO_AGENCIADOR_FRETE_SOLUCAO);
        mapa.put(TipoPerfilUsuario.REVENDA, TemplatePlanilha.TEMPLATE_RELATORIO_REEMBOLSO_AGENCIADOR_FRETE_REVENDA);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (financeiro - revenda)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesFinanceiroRevenda() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.REVENDA, TemplatePlanilha.TEMPLATE_RELATORIO_FINANCEIRO_REVENDA);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (financeiro - frota)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesFinanceiroFrota() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.FROTA, TemplatePlanilha.TEMPLATE_RELATORIO_FINANCEIRO_FROTA);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (abastecimentos no período)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesAbastecimentosPeriodo() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.REVENDA, TemplatePlanilha.TEMPLATE_RELATORIO_ABASTECIMENTOS_NO_PERIODO);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (ativação de tag)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesAtivacaoTag() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_ATIVACAO_TAG);
        mapa.put(TipoPerfilUsuario.FROTA, TemplatePlanilha.TEMPLATE_RELATORIO_ATIVACAO_TAG);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (geral frota)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesFrotaGeral() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_PESQUISAR_GERAL_FROTA);
        return mapa;
    }
    
    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (cobrança Conectcar)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesCobrancaConectcar() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_COBRANCA_CONECTCAR);
        mapa.put(TipoPerfilUsuario.FROTA, TemplatePlanilha.TEMPLATE_RELATORIO_COBRANCA_CONECTCAR);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (Cota de Veículos)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesCotaVeiculo() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_COTA_VEICULO_SOLUCAO);
        mapa.put(TipoPerfilUsuario.FROTA, TemplatePlanilha.TEMPLATE_RELATORIO_COTA_VEICULO_FROTA);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (utilização tag)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesUtilizacaoTag() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_UTILIZACAO_TAG);
        mapa.put(TipoPerfilUsuario.FROTA, TemplatePlanilha.TEMPLATE_UTILIZACAO_TAG);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (extrato vale pedágio)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesExtratoValePedagio() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_EXTRATO_VALE_PEDAGIO);
        mapa.put(TipoPerfilUsuario.FROTA, TemplatePlanilha.TEMPLATE_EXTRATO_VALE_PEDAGIO);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (detalhe vale pedágio)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesDetalheValePedagio() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_DETALHE_VALE_PEDAGIO);
        mapa.put(TipoPerfilUsuario.FROTA, TemplatePlanilha.TEMPLATE_DETALHE_VALE_PEDAGIO);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (reembolso Conectcar)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesReembolsoConectcar() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_REEMBOLSO_CONECTCAR);
        mapa.put(TipoPerfilUsuario.FROTA, TemplatePlanilha.TEMPLATE_RELATORIO_REEMBOLSO_CONECTCAR);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (Detalhamento Cobrança)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesDetalhamentoCobranca() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.FROTA, TemplatePlanilha.TEMPLATE_RELATORIO_DETALHAMENTO_COBRANCA);
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_DETALHAMENTO_COBRANCA_INTERNO);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (reembolso)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesReembolso() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_REEMBOLSO);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (detalhamento reembolso)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesDetalhamentoReembolso() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_DETALHAMENTO_REEMBOLSO);
        return mapa;
    }
    
    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (Rotas Inteligentes)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesRotasInteligentes() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_ROTAS_INTELIGENTES);
        mapa.put(TipoPerfilUsuario.FROTA, TemplatePlanilha.TEMPLATE_RELATORIO_ROTAS_INTELIGENTES);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (Últimos Preços)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesUltimosPrecos() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_ULTIMOS_PRECOS_SOLUCAO);
        mapa.put(TipoPerfilUsuario.FROTA, TemplatePlanilha.TEMPLATE_RELATORIO_ULTIMOS_PRECOS_FROTA);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatÃ³rio (Reembolso Antecipado)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesReembolsoAntecipado() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_REEMBOLSO_ANTECIPADO_SOLUCAO);
        mapa.put(TipoPerfilUsuario.REVENDA, TemplatePlanilha.TEMPLATE_RELATORIO_REEMBOLSO_ANTECIPADO_REVENDA);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatÃ³rio (Detalhe Reembolso Antecipado)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesDetalheReembolsoAntecipado() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_DETALHE_REEMBOLSO_ANTECIPADO_SOLUCAO);
        mapa.put(TipoPerfilUsuario.REVENDA, TemplatePlanilha.TEMPLATE_RELATORIO_DETALHE_REEMBOLSO_ANTECIPADO_REVENDA);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (Tarifador)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesTarifador() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_TARIFADOR);
        return mapa;
    }

}
