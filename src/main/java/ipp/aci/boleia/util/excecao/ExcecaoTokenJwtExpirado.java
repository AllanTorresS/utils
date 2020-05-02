package ipp.aci.boleia.util.excecao;

/**
 * Lancada quando um token JWT expirado eh submetido a validacao
 */
public class ExcecaoTokenJwtExpirado extends ExcecaoBoleia {

    /**
     * Constroi uma instancia da excecao
     */
    public ExcecaoTokenJwtExpirado() {
        super(Erro.TOKEN_JWT_EXPIRADO);
    }
}
