package ipp.aci.boleia.util.excecao;

import java.io.Serializable;

/**
 * Excecao lancada em caso de erro na integracao com apco
 */
public class ExcecaoIntegracaoKmv extends ExcecaoBoleia {

    /**
     * Instancia ExcecaoIntegracaoKmv
     *
     * @param erro O erro a ser reportado
     * @param args Os argumentos da mensagem do erro a ser reportada
     */
    public ExcecaoIntegracaoKmv(Erro erro, Serializable... args) {
        super(erro, args);
    }

    /**
     * Instancia ExcecaoIntegracaoKmv
     *
     * @param erro O erro a ser reportado
     * @param t    A causa do erro, caso exista
     * @param args Os argumentos da mensagem do erro a ser reportada
     */
    public ExcecaoIntegracaoKmv(Erro erro, Throwable t, Serializable... args) {
        super(erro, t, args);
    }
}
