package ipp.aci.boleia.util.excecao;

import java.io.Serializable;

/**
 * Demarca uma excecao que nao possui tratamento, ou seja, que pode ser simplesmente
 * apresentada em tela de modo amigavel. Estende RuntimeException, pois:
 *  1) nao deve ser tratada, mas apenas apresentada
 *  2) garante rollbak da tranacao subjacente.
 */
public class ExcecaoBoleiaRuntime extends RuntimeException implements IExcecaoBoleia {

    private final Erro erro;
    private final Serializable[] args;

    /**
     * Construtor
     *
     * @param erro O erro a ser reportado
     * @param t    A causa do erro, caso exista
     * @param args Os argumentos da mensagem do erro a ser reportada
     */
    public ExcecaoBoleiaRuntime(Erro erro, Throwable t, Serializable... args) {
        super(erro.name(), t);
        this.erro = erro;
        this.args = args;
    }

    /**
     * Construtor
     * @param t    A causa do erro, caso exista
     * @param args Os argumentos da mensagem do erro a ser reportada
     */
    public ExcecaoBoleiaRuntime(Throwable t, Serializable... args) {
        this(Erro.ERRO_GENERICO, t, args);
    }

    /**
     * Construtor
     * @param erro O erro a ser reportado
     * @param args Os argumentos da mensagem do erro a ser reportada
     */
    public ExcecaoBoleiaRuntime(Erro erro, Serializable... args) {
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
