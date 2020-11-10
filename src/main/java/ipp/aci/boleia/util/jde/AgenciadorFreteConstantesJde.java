package ipp.aci.boleia.util.jde;

import java.math.BigDecimal;

/**
 * Enumera as constantes utilizadas pelos serviços de integração com o JDE
 */
public final class AgenciadorFreteConstantesJde {

    public static final String FILIAL = "746975";

    public static final String FATURA_SISTEMA_GERADOR = "BOLEIA";
    public static final String FATURA_CENARIO = "140";
    public static final String FATURA_TIPO_ABASTECIMENTO = "D3";
    public static final String FATURA_TIPO_SAQUE = "F4";
    public static final String FATURA_OBSERVACAO_ABASTECIMENTO ="ABASTECIMENTO PROFROTAS X NDD";
    public static final String FATURA_OBSERVACAO_SAQUE = "SAQUE PROFROTAS X NDD";

    public static final Long VOUCHER_CENARIO_ABASTECIMENTO = 187L;
    public static final Long VOUCHER_CENARIO_SAQUE = 734L;
    public static final String VOUCHER_CIA = "00074";
    public static final String VOUCHER_OBSERVACAO_ABASTECIMENTO = "ProFrotas X NDD – Abastecimento";
    public static final String VOUCHER_OBSERVACAO_SAQUE = "ProFrotas X NDD – Saque";
    public static final String VOUCHER_ABASTECIMENTO = "ABASTECIMENTO";
    public static final String VOUCHER_SAQUE = "SAQUE";
    public static final String VOUCHER_MDR = "MDR Total";

    public static final BigDecimal FATURA_INDICE_CONTA_ABASTECIMENTO = new BigDecimal(3);
    public static final BigDecimal FATURA_INDICE_CONTA_SAQUE = new BigDecimal(1);
    public static final BigDecimal VOUCHER_INDICE_CONTA_ABASTECIMENTO = new BigDecimal(1);
    public static final BigDecimal VOUCHER_INDICE_CONTA_SAQUE = new BigDecimal(4);
    public static final BigDecimal VOUCHER_INDICE_CONTA_MDR = new BigDecimal(2);

    /**
     * Impede instanciacao
     */
    private AgenciadorFreteConstantesJde() {
        // Impede instanciacao e heranca
    }
}
