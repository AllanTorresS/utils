package ipp.aci.boleia.util;

/**
 * Constantes utilizadas pelos serviços de integração com a ConectCar
 */
public final class ConstantesConectcar {
	
	public static final String TIPO_BLOQUEIO_CONTRATO = "C";
	public static final String TIPO_BLOQUEIO_LOTE = "L";
	public static final String TIPO_BLOQUEIO_INDIVIDUAL = "I";
	
	public static final Integer TIPO_EVENTO_CONECTCAR_TRANSACAO = 1;
    public static final Integer TIPO_EVENTO_VALE_PEDAGIO = 6;
    
    public static final String  TIPO_PROTOCOLO_CONECTCAR_ESTACIONAMENTO = "PARK";
    
    public static final Long    TIPO_OPERACAO_RECARGA_VALE_PEDAGIO = 1l;
    public static final Long    TIPO_OPERACAO_CANCELAMENTO_VALE_PEDAGIO = 44l;
    public static final Long    TIPO_OPERACAO_PASSAGEM_VALE_PEDAGIO = 20l;
    public static final Long    TIPO_OPERACAO_ESTORNO_VALE_PEDAGIO = 22l;

    /**
     * Impede instanciacao
     */
    private ConstantesConectcar() {
        // Impede instanciacao
    }
}
