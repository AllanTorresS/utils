package ipp.aci.boleia.util.excecao;

/**
 * Exceção lançada em caso ocorra um pagamento duplicado.
 *
 * @author pedro.silva
 */
public class ExcecaoPagamentoDuplicado extends ExcecaoValidacao {

    /**
     * Construtor da classe.
     * @param mensagem A mensagem a ser exibida
     */
    public ExcecaoPagamentoDuplicado(String mensagem) {
        super(Erro.ERRO_VALIDACAO_SIMILARIDADE, mensagem);
    }
}
