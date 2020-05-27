package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.dominio.SubcategoriaNotificacao;

/**
 * Tipo de notificacao a ser disparada
 */
public enum TipoSubcategoriaNotificacao {

    SOLICITACAO_SENHA_MOTORISTA                         (1L, TipoCategoriaNotificacao.GERAIS),
    NOVA_FROTA                                          (2L, TipoCategoriaNotificacao.GERAIS),
    FROTA_REATIVADA_ADM                                 (3L, TipoCategoriaNotificacao.GERAIS),
    FROTA_INATIVADA_ADM                                 (4L, TipoCategoriaNotificacao.GERAIS),
    FROTA_REATIVADA_REVENDA                             (5L, TipoCategoriaNotificacao.GERAIS),
    FROTA_INATIVADA_REVENDA                             (6L, TipoCategoriaNotificacao.GERAIS),
    PRECO_REFERENCIA_REAJUSTADO                         (7L, TipoCategoriaNotificacao.NEGOCIACOES),
    PRECO_BASE_REVENDA_REAJUSTADO                       (8L, TipoCategoriaNotificacao.NEGOCIACOES),
    PRECO_BASE_FROTA_REAJUSTADO                         (9L, TipoCategoriaNotificacao.NEGOCIACOES),
    NOTA_FISCAL_CICLO_A_VENCER                          (10L, TipoCategoriaNotificacao.FINANCEIRAS),
    NOTA_FISCAL_CICLO_ATRASADO                          (11L, TipoCategoriaNotificacao.FINANCEIRAS),
    NOTA_FISCAL_EMITIDA                                 (12L, TipoCategoriaNotificacao.FINANCEIRAS),
    CICLO_ENCERRADO                                     (13L, TipoCategoriaNotificacao.FINANCEIRAS),
    NOVA_SOLICITACAO_ACORDO_ESPECIAL                    (14L, TipoCategoriaNotificacao.NEGOCIACOES),
    ACORDO_ESPECIAL_ACEITO                              (15L, TipoCategoriaNotificacao.NEGOCIACOES),
    ACORDO_ESPECIAL_REJEITADO_SOLUCAO                   (16L, TipoCategoriaNotificacao.NEGOCIACOES),
    RENEGOCIACAO_PRECO_REJEITADA                        (17L, TipoCategoriaNotificacao.NEGOCIACOES),
    RENEGOCIACAO_PRECO_APROVADA                         (18L, TipoCategoriaNotificacao.NEGOCIACOES),
    RENEGOCIACAO_PRECO                                  (19L, TipoCategoriaNotificacao.NEGOCIACOES),
    RENEGOCIACAO_PRECO_REJEITADA_SUGESTAO               (20L, TipoCategoriaNotificacao.NEGOCIACOES),
    HABILITACAO_PONTO_VENDA                             (21L, TipoCategoriaNotificacao.GERAIS),
    VIOLACAO_PARAMETRO_INFORMATIVO_FROTA                (22L, TipoCategoriaNotificacao.GERAIS),
    VIOLACAO_PARAMETRO_RESTRITIVO_FROTA                 (23L, TipoCategoriaNotificacao.GERAIS),
    ABASTECIMENTO_MANUAL_APROVACAO                      (24L, TipoCategoriaNotificacao.FINANCEIRAS),
    VEICULO_SEM_CONSUMO_ESTIMADO                        (25L, TipoCategoriaNotificacao.GERAIS),
    PRECOS_MAXIMOS_FROTA_ATUALIZADOS                    (26L, TipoCategoriaNotificacao.GERAIS),
    ALERTA_MECANISMO_ANTIFRAUDE                         (27L, TipoCategoriaNotificacao.GERAIS),
    ACORDO_ESPECIAL_ACEITO_SOLUCAO                      (28L, TipoCategoriaNotificacao.NEGOCIACOES),
    NOTA_FISCAL_NAO_EMITIDA_SOLUCAO                     (29L, TipoCategoriaNotificacao.FINANCEIRAS),
    COMANDA_AGUARDANDO_REABILITACAO                     (30L, TipoCategoriaNotificacao.GERAIS),
    HODOMETRO_HORIMETRO_FORA_LIMITE                     (31L, TipoCategoriaNotificacao.GERAIS),
    RENEGOCIACAO_PRECO_REVENDA                          (32L, TipoCategoriaNotificacao.NEGOCIACOES),
    RENEGOCIACAO_PRECO_VIGENTE_REVENDA                  (33L, TipoCategoriaNotificacao.NEGOCIACOES),
    RENEGOCIACAO_PRECO_VIGENTE_SOLUCAO                  (34L, TipoCategoriaNotificacao.NEGOCIACOES),
    RELATORIO_CONCLUIDO                                 (35L, TipoCategoriaNotificacao.GERAIS),
    ALERTA_AJUSTE_BOLETO                                (36L, TipoCategoriaNotificacao.FINANCEIRAS),
    CAMPANHA_DISPONIVEL_APROVACAO                       (37L, TipoCategoriaNotificacao.GERAIS),
    CAMPANHA_REJEITADA                                  (38L, TipoCategoriaNotificacao.GERAIS),
    ALTERACAO_ABASTECIMENTO_EXPIRADA                    (39L, TipoCategoriaNotificacao.FINANCEIRAS),
    ABASTECIMENTO_INVALIDAR_SALDO_FROTA                 (40L, TipoCategoriaNotificacao.FINANCEIRAS);

    private final Long value;
    private final TipoCategoriaNotificacao categoria;

    TipoSubcategoriaNotificacao(Long value, TipoCategoriaNotificacao categoria) {
        this.value = value;
        this.categoria = categoria;
    }

    /**
     * Obtem por valor
     *
     * @param value value
     * @return Enum para o valor
     */
    public static TipoSubcategoriaNotificacao obterPorValor(Long value) {
        for (TipoSubcategoriaNotificacao tipoSubcategoriaNotificacao : TipoSubcategoriaNotificacao.values()) {
            if (tipoSubcategoriaNotificacao.value.equals(value)) {
                return tipoSubcategoriaNotificacao;
            }
        }
        return null;
    }

    /**
     * Método que constrói uma entidade de subcategoria notiricacao.
     *
     * @return Entidade de Subcategoria Notificacao
     */
    public SubcategoriaNotificacao obterEntidade() {
        SubcategoriaNotificacao entidade = new SubcategoriaNotificacao();
        entidade.setId(value);
        entidade.setChave(name());
        entidade.setCategoria(categoria.obterEntidade());
        return entidade;
    }

    /**
     * Obtem o valor da enumercao
     * @return o valor da enumeracao
     */
    public Long getValue() {
        return value;
    }

    public TipoCategoriaNotificacao getCategoria() {
        return categoria;
    }
}
