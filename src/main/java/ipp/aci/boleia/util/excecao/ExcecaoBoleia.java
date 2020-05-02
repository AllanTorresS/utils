package ipp.aci.boleia.util.excecao;

/**
 * Demarca uma excecao que necessita de tratamento. Deve ser estendida para que o
 * chamador possa trata-la de maneira especifica. Esta excecao, por si so, nao causa
 * rollback da transacao subjacente.
 */
public abstract class ExcecaoBoleia extends Exception implements IExcecaoBoleia {

    private final Erro erro;
    private final transient Object[] args;

    /**
     * Construtor
     *
     * @param erro O erro a ser reportado
     * @param t    A causa do erro, caso exista
     * @param args Os argumentos da mensagem do erro a ser reportada
     */
    protected ExcecaoBoleia(Erro erro, Throwable t, Object... args) {
        super(erro.name(), t);
        this.erro = erro;
        this.args = args;
    }

    /**
     * Construtor
     *
     * @param t    A causa do erro, caso exista
     * @param args Os argumentos da mensagem do erro a ser reportada
     */
    public ExcecaoBoleia(Throwable t, Object... args) {
        this(Erro.ERRO_GENERICO, t, args);
    }

    /**
     * Construtor
     *
     * @param erro O erro a ser reportado
     * @param args Os argumentos da mensagem do erro a ser reportada
     */
    protected ExcecaoBoleia(Erro erro, Object... args) {
        this(erro, null, args);
    }

    @Override
    public Erro getErro() {
        return erro;
    }

    @Override
    public Object[] getArgs() {
        return args;
    }
}
