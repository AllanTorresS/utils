package ipp.aci.boleia.util.excecao;

/**
 * Esse erro deve ser disparado quando tiver seu registro bloqueado por concorrencia
 */
public class ExcecaoRecursoBloqueado extends ExcecaoBoleia {

    /**
     * Instancia ExcecaoRecursoBloqueado
     *
     * @param mensagem descrição do erro
     */
    public ExcecaoRecursoBloqueado(String mensagem) {
        super(Erro.RECURSO_CONCORRENTE_BLOQUEADO, mensagem);
    }
}
