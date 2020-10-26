package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica o tipo do relatório emitido pelo motor de geração de relatórios
 */
public enum TipoRelatorioMotorGerador implements IEnumComLabel<TipoRelatorioMotorGerador> {

    RELATORIO_PRECO_EXPORTACAO(0),
    RELATORIO_PRECO_POSTO(1),
    RELATORIO_ABASTECIMENTO(2),
    RELATORIO_NOTAS_FISCAIS(3),
    RELATORIO_MOTORISTA(4),
    RELATORIO_COBRANCA(5),
    RELATORIO_CICLO_REPASSE(6),
    RELATORIO_PONTO_VENDA(7),
    RELATORIO_SALDO_FROTA(8),
    RELATORIO_FROTA(9),
    RELATORIO_POSTO_CREDENCIADO(10),
    RELATORIO_ABASTECIMENTOS_ESTORNADOS(11),
    RELATORIO_ABASTECIMENTOS_AJUSTADOS(12),
    RELATORIO_PRECO_NEGOCIADO(13),
    RELATORIO_NEGOCIACAO(14),
    RELATORIO_COBRANCA_AGENCIADOR_FRETE(15),
    RELATORIO_ABASTECIMENTO_AGENCIADOR_FRETE(16),
    RELATORIO_REEMBOLSO_AGENCIADOR_FRETE(17),
    RELATORIO_FINANCEIRO(18),
    RELATORIO_ABASTECIMENTOS_NO_PERIODO(19);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do status
     */
    TipoRelatorioMotorGerador(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
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
}
