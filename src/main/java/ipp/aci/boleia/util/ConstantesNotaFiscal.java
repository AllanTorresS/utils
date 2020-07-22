package ipp.aci.boleia.util;

import java.math.BigDecimal;

/**
 * Constantes utilizadas pela nota fiscal
 */
public final class ConstantesNotaFiscal {

    public static final String NOTA_FISCAL_PREFIX = "NFe";
    public static final BigDecimal MARGEM_VALOR_ABAST = new BigDecimal("0.05");

    public static final String TEMPLATE_DANFE = "danfe";

    public static final String EMITENTE = "0 - EMITENTE";
    public static final String DESTINATARIO = "1 - DESTINATÁRIO";
    public static final String TERCEIROS = "2 - TERCEIROS";
    public static final String SEM_FRETE = "9 - SEM FRETE";

    public static final int TAMANHO_CHAVE_ACESSO = 44;

    /**
     * Impede instanciacao
     */
    private ConstantesNotaFiscal() {
        // Impede instanciacao
    }
}
