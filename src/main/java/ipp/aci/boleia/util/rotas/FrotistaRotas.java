package ipp.aci.boleia.util.rotas;

/**
 * Classe com as configurações de Rotas da API de Frotista.
 * 
 */
public final class FrotistaRotas {

    // Prefixos
    public static final String BASE_API = "/api/frotista";

    // APIs
    public static final String ABASTECIMENTO_API = BASE_API + "/abastecimento";
    public static final String MOTORISTA_API = BASE_API + "/motorista";
    public static final String COBRANCA_API = BASE_API + "/cobranca";
    public static final String NOTA_FISCAL_API = BASE_API + "/nota-fiscal";
    public static final String VEICULO_API = BASE_API + "/veiculo";

    // Produces
    public static final String PRODUCES_APPLICATION = "application";
    public static final String PRODUCES_APPLICATION_JSON =  PRODUCES_APPLICATION + "/json";

    // Consumes
    public static final String CONSUMES_APPLICATION_JSON = PRODUCES_APPLICATION_JSON;
    public static final String PESQUISA = "/pesquisa";

    /**
     * Construtor privado, impede instanciacao e heranca
     */
    private FrotistaRotas() {
        // impede instanciacao e heranca
    }
}