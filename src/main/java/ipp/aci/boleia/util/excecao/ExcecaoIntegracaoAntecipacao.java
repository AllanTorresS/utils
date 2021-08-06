package ipp.aci.boleia.util.excecao;

import java.io.Serializable;

/**
 * Exceção lançada durante erro na integração de documentos de antecipação no JDE
 */
public class ExcecaoIntegracaoAntecipacao extends RuntimeException implements IExcecaoBoleia {

    private final Erro erro;
    private final Serializable[] args;

    /**
     * Construtor
     *
     * @param erro O erro a ser reportado
     * @param t    A causa do erro, caso exista
     * @param args Os argumentos da mensagem do erro a ser reportada
     */
    public ExcecaoIntegracaoAntecipacao(Erro erro, Throwable t, Serializable... args) {
        super(erro.name(), t);
        this.erro = erro;
        this.args = args;
    }

    /**
     * Construtor
     * @param erro O erro a ser reportado
     * @param args Os argumentos da mensagem de erro a ser reportada
     */
    public ExcecaoIntegracaoAntecipacao(Erro erro, Serializable... args) {
        this(erro, null, args);
    }

    @Override
    public Erro getErro() {
        return erro;
    }

    @Override
    public Serializable[] getArgs() {
        return args;
    }
}
