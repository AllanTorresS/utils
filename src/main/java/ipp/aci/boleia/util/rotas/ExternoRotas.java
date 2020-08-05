package ipp.aci.boleia.util.rotas;

/**
 * Classe com as configurações de Rotas de Integração Externa.
 * 
 */
public final class ExternoRotas {

    // Prefixos
    public static final String BASE_API = "/api/externo";
    public static final String FROTAS_LEVES_API = BASE_API + "/frotasleves";
    public static final String AGENCIADOR_FRETE_API = BASE_API + "/agenciadorfrete";

    // APIs
    public static final String TELEMETRIA_API = BASE_API + "/telemetria";
    public static final String AUTORIZACAO_API = BASE_API + "/autorizacao";
    public static final String MOTORISTA_API = BASE_API + "/motorista";
    public static final String PEDIDO_API = BASE_API + "/pedido";
    public static final String AUTORIZACAO_PEDIDO_FROTAS_LEVES_API = FROTAS_LEVES_API + "/pedido";
    public static final String TRANSACAO_FROTAS_LEVES_API = FROTAS_LEVES_API + "/transacao";
    public static final String PV_API = BASE_API + "/pv";
    public static final String PRODUTO_API = BASE_API + "/produto";
    public static final String VEICULO_API = BASE_API + "/veiculo";
    public static final String FROTA_API = BASE_API + "/frota";
    public static final String AGENCIADOR_POSTO_API = AGENCIADOR_FRETE_API + "/posto";
    public static final String MOTORISTA_AUTONOMO_API = AGENCIADOR_FRETE_API + "/motoristaautonomo";


    // Produces
    public static final String PRODUCES_APPLICATION = "application";
    public static final String PRODUCES_APPLICATION_JSON =  PRODUCES_APPLICATION + "/json";

    // Consumes
    public static final String CONSUMES_APPLICATION_JSON = PRODUCES_APPLICATION_JSON;

    /**
     * Construtor privado, impede instanciacao e heranca
     */
    private ExternoRotas() {
        // impede instanciacao e heranca
    }
}