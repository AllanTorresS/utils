package ipp.aci.boleia.util.jde;

import java.math.BigDecimal;

/**
 * Enumera as constantes utilizadas pelos serviços de integração com o JDE
 */
public final class AgenciadorFreteConstantesJde {

    public static final String FATURA_SISTEMA_GERADOR = "BOLEIA";
    public static final String FATURA_CENARIO = "140";
    public static final String FILIAL = "746975";
    public static final String FATURA_TIPO_ABASTECIMENTO = "D3";
    public static final String FATURA_TIPO_SAQUE = "F4";
    public static final String FATURA_OBSERVACAO_ABASTECIMENTO ="ABASTECIMENTO PROFROTAS X NDD";
    public static final String FATURA_OBSERVACAO_SAQUE = "SAQUE PROFROTAS X NDD";

    public static final BigDecimal INDICE_CONTA_ABASTECIMENTO = new BigDecimal(3);
    public static final BigDecimal INDICE_CONTA_SAQUE = BigDecimal.ONE;

    /**
     * Impede instanciacao
     */
    private AgenciadorFreteConstantesJde() {
        // Impede instanciacao e heranca
    }
}
