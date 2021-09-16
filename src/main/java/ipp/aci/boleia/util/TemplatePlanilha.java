package ipp.aci.boleia.util;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * TemplatesImportacao de planilhas para importacao de dados do Excel
 */
public enum TemplatePlanilha {

    TEMPLATE_MOTORISTA("template-motorista.xlsx"),
    TEMPLATE_NEGOCIACAO("template-negociacao.xlsx"),
    TEMPLATE_VEICULO("template-veiculo.xlsx"),
    TEMPLATE_MICROMERCADO("template-micromercado.xlsx"),
    TEMPLATE_PRECO_MICROMERCADO("template-preco-micromercado.xlsx"),
    TEMPLATE_PRECO_BASE("template-incluir-precos-posto-em-lote.xlsx"),
    TEMPLATE_RELATORIO_ABASTECIMENTO_CONSUMO_INTERNO("template-relatorio-abastecimento-consumo-interno.xlsx"),
    TEMPLATE_RELATORIO_ABASTECIMENTO_CONSUMO_FROTA("template-relatorio-abastecimento-consumo-frota.xlsx"),
    TEMPLATE_RELATORIO_ABASTECIMENTO_FROTA("template-relatorio-abastecimento-frota.xlsx"),
    TEMPLATE_RELATORIO_ABASTECIMENTO_REVENDA("template-relatorio-abastecimento-revenda.xlsx"),
    TEMPLATE_RELATORIO_ABASTECIMENTO_SOLUCAO("template-relatorio-abastecimento-solucao.xlsx"),
    TEMPLATE_RELATORIO_COBRANCA("template-relatorio-cobranca.xlsx"),
    TEMPLATE_RELATORIO_COBRANCA_CONECTCAR("template-relatorio-cobranca-conectcar.xlsx"),
    TEMPLATE_RELATORIO_COBRANCA_COM_DESCONTO("template-relatorio-cobranca-com-descontos.xlsx"),
    TEMPLATE_RELATORIO_COBRANCA_INTERNO("template-relatorio-cobranca-interno.xlsx"),
    TEMPLATE_RELATORIO_COBRANCA_AGENCIADOR_FRETE("template-relatorio-cobranca-agenciador-frete.xlsx"),
    TEMPLATE_RELATORIO_REEMBOLSO_AGENCIADOR_FRETE_SOLUCAO("template-relatorio-reembolso-agenciador-frete-solucao.xlsx"),
    TEMPLATE_RELATORIO_REEMBOLSO_AGENCIADOR_FRETE_REVENDA("template-relatorio-reembolso-agenciador-frete-revenda.xlsx"),
    TEMPLATE_RELATORIO_TRANSACAO_AGENCIADOR_FRETE("template-relatorio-transacao-agenciador-frete.xlsx"),
    TEMPLATE_RELATORIO_TRANSACAO_AGENCIADOR_FRETE_REVENDA("template-relatorio-transacao-agenciador-frete-revenda.xlsx"),
    TEMPLATE_RELATORIO_FROTA_SOLUCAO("template-relatorio-frota_solucao.xlsx"),
    TEMPLATE_RELATORIO_FROTA_REVENDA("template-relatorio-frota_revenda.xlsx"),
    TEMPLATE_RELATORIO_REEMBOLSO_PAGO("template-relatorio-reembolso-pago.xlsx"),
    TEMPLATE_RELATORIO_REEMBOLSO_PENDENTE("template-relatorio-reembolso-pendente.xlsx"),
    TEMPLATE_RELATORIO_PRECO_BASE_SOLUCAO("template-relatorio-preco-base-solucao.xlsx"),
    TEMPLATE_RELATORIO_PRECO_BASE_REVENDA("template-relatorio-preco-base-revenda.xlsx"),
    TEMPLATE_RELATORIO_PRECO_MICROMERCADO("template-relatorio-preco-referencia.xlsx"),
    TEMPLATE_RELATORIO_PRECO_NEGOCIADO_SOLUCAO("template-relatorio-precos-negociados-solucao.xlsx"),
    TEMPLATE_RELATORIO_PRECO_NEGOCIADO_REVENDA("template-relatorio-precos-negociados-revenda.xlsx"),
    TEMPLATE_RELATORIO_PRECO_NEGOCIADO_FROTA("template-relatorio-precos-negociados-frota.xlsx"),
    TEMPLATE_RELATORIO_PEDIDO_CREDITO_SOLUCAO("template-relatorio-pedido-credito-solucao.xlsx"),
    TEMPLATE_RELATORIO_PEDIDO_CREDITO_FROTA("template-relatorio-pedido-credito-frota.xlsx"),
    TEMPLATE_RELATORIO_PONTO_VENDA_SOLUCAO("template-relatorio-ponto-venda-solucao.xlsx"),
    TEMPLATE_RELATORIO_PONTO_VENDA_FROTA("template-relatorio-ponto-venda-frota.xlsx"),
    TEMPLATE_RELATORIO_EXTRATO_PEDIDO_SOLUCAO("template-relatorio-extrato-pedido-solucao.xlsx"),
    TEMPLATE_RELATORIO_EXTRATO_PEDIDO_FROTA("template-relatorio-extrato-pedido-frota.xlsx"),
    TEMPLATE_RELATORIO_MEDIA_CONSUMO_FROTA("template-relatorio-media-consumo-frota.xlsx"),
    TEMPLATE_RELATORIO_MEDIA_CONSUMO_VEICULO_FROTA("template-relatorio-media-consumo-veiculo-frota.xlsx"),
    TEMPLATE_RELATORIO_MEDIA_CONSUMO_MOTORISTA_FROTA("template-relatorio-media-consumo-motorista-frota.xlsx"),
    TEMPLATE_RELATORIO_MEDIA_CONSUMO_SOLUCAO("template-relatorio-media-consumo-solucao.xlsx"),
    TEMPLATE_RELATORIO_MEDIA_CONSUMO_VEICULO_SOLUCAO("template-relatorio-media-consumo-veiculo-solucao.xlsx"),
    TEMPLATE_RELATORIO_MEDIA_CONSUMO_MOTORISTA_SOLUCAO("template-relatorio-media-consumo-motorista-solucao.xlsx"),
    TEMPLATE_RELATORIO_NEGOCIACOES_SOLUCAO("template-relatorio-negociacoes-solucao.xlsx"),
    TEMPLATE_RELATORIO_REPASSE_SOLUCAO("template-relatorio-repasse-solucao.xlsx"),
    TEMPLATE_RELATORIO_NOTAS_FISCAIS("template-relatorio-notas-fiscais.xlsx"),
    TEMPLATE_RELATORIO_MOTORISTA_SOLUCAO("template-relatorio-motorista-solucao.xlsx"),
    TEMPLATE_RELATORIO_MOTORISTA_FROTA("template-relatorio-motorista-frota.xlsx"),
    TEMPLATE_RELATORIO_CREDITO_FROTA("template-relatorio-credito-frota.xlsx"),
    TEMPLATE_RELATORIO_POSTO_CREDENCIADO_FROTA("template-relatorio-posto-credenciado-frota.xlsx"),
    TEMPLATE_RELATORIO_POSTO_CREDENCIADO_SOLUCAO("template-relatorio-posto-credenciado-solucao.xlsx"),
    TEMPLATE_RELATORIO_ABASTECIMENTOS_ESTORNADOS("template-relatorio-abastecimentos-estornados.xlsx"),
    TEMPLATE_RELATORIO_ABASTECIMENTOS_AJUSTADOS("template-relatorio-abastecimentos-ajustados.xlsx"),
    TEMPLATE_RELATORIO_FINANCEIRO_REVENDA("template-relatorio-financeiro-revenda.xlsx"),
    TEMPLATE_RELATORIO_FINANCEIRO_FROTA("template-relatorio-financeiro-frota.xlsx"),
    TEMPLATE_RELATORIO_ABASTECIMENTOS_NO_PERIODO("template-relatorio-abastecimento-no-periodo.xlsx"),
    TEMPLATE_ATIVACAO_TAG("template-ativacao-tag.xlsx"),
	TEMPLATE_RELATORIO_ATIVACAO_TAG("template-relatorio-ativacao-tag.xlsx"),
	TEMPLATE_PESQUISAR_GERAL_FROTA("template-relatorio-geral-frota.xlsx"),
	TEMPLATE_RELATORIO_COTA_VEICULO_SOLUCAO("template-relatorio-cota-veiculo-interno.xlsx"),
    TEMPLATE_RELATORIO_COTA_VEICULO_FROTA("template-relatorio-cota-veiculo-frota.xlsx"),
	TEMPLATE_UTILIZACAO_TAG("template-utilizacao-tag.xlsx"),
	TEMPLATE_RELATORIO_REEMBOLSO_CONECTCAR("template-relatorio-reembolso-conectcar.xlsx"),
    TEMPLATE_RELATORIO_DETALHAMENTO_COBRANCA("template-relatorio-detalhamento-cobranca.xlsx"),
    TEMPLATE_RELATORIO_DETALHAMENTO_COBRANCA_INTERNO("template-relatorio-detalhamento-cobranca-interno.xlsx"),
    TEMPLATE_RELATORIO_REEMBOLSO("template-relatorio-reembolso.xlsx"),
    TEMPLATE_RELATORIO_DETALHAMENTO_REEMBOLSO("template-relatorio-detalhamento-reembolso.xlsx"),
    TEMPLATE_RELATORIO_ROTAS_INTELIGENTES("template-relatorio-rotas-inteligentes.xlsx"),
    TEMPLATE_RELATORIO_ULTIMOS_PRECOS_FROTA("template-relatorio-ultimos-precos-frota.xlsx"),
    TEMPLATE_RELATORIO_ULTIMOS_PRECOS_SOLUCAO("template-relatorio-ultimos-precos-interno.xlsx"),
    TEMPLATE_RELATORIO_REEMBOLSO_ANTECIPADO_SOLUCAO("template-relatorio-reembolso-antecipado-solucao.xlsx"),
    TEMPLATE_RELATORIO_REEMBOLSO_ANTECIPADO_REVENDA("template-relatorio-reembolso-antecipado-revenda.xlsx"),
    TEMPLATE_RELATORIO_DETALHE_REEMBOLSO_ANTECIPADO("template-relatorio-detalhe-reembolso-antecipado.xlsx");


    private static final String BASE_TEMPLATE = "/template-planilhas/";

    private final String path;
    private final String name;

    TemplatePlanilha(String name) {
        this.name = name;
        this.path = BASE_TEMPLATE + name;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    /**
     * Configura response para download do template
     *
     * @param response Response configurada para download
     */
    public void configurarResponse(HttpServletResponse response) {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("content-disposition", "attachment; filename=" + this.name);
    }

    /**
     * Obtém o template pelo nome do arquivo
     *
     * @param nomeArquivo O nome do arquivo a ser procurado
     * @return O template correspondente
     */
    public static TemplatePlanilha obterPorNomeArquivo(String nomeArquivo) {
        return Arrays.asList(TemplatePlanilha.values())
                .stream()
                .filter(t -> t.getName().equals(nomeArquivo))
                .findFirst()
                .orElse(null);
    }
}
