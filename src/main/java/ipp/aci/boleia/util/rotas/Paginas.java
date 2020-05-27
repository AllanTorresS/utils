package ipp.aci.boleia.util.rotas;

/**
 * Utilitario para enumeracao das paginas do sistema
 */
public final class Paginas {

    public static final String LOGIN                        = "/#!/login";
    public static final String RECUPERAR_SENHA              = "/#!/recuperar-senha";
    public static final String REDEFINIR_SENHA              = "/#!/redefinir-senha";
    public static final String REDEFINIR_SENHA_PDV          = "#!/redefinir-senha";

    /**
     * Construtor privado, impede instanciacao e heranca
     */
    private Paginas() {
        // impede instanciacao e heranca
    }
}
