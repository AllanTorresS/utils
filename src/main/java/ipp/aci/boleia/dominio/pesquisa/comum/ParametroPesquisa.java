package ipp.aci.boleia.dominio.pesquisa.comum;

/**
 * Representa um parametro de pesquisa a ser usado pelos repositorios do sistema
 */
public abstract class ParametroPesquisa {

    private String nome;
    private Object valor;

    /**
     * Construtor
     */
    public ParametroPesquisa() {
    }

    /**
     * Construtor
     *
     * @param nome  O nome do parametro
     * @param valor O valor do parametro
     */
    public ParametroPesquisa(String nome, Object valor) {
        this.nome = nome;
        this.valor = valor;
    }

    /**
     * Retorna o nome do parametro
     *
     * @return O nome do parametro
     */
    public String getNome() {
        return nome;
    }

    /**
     * Atribui o nome do parametro
     *
     * @param nome O nome do parametro
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o valor do parametro
     *
     * @return O valor do parametro
     */
    public Object getValor() {
        return valor;
    }

    /**
     * Atribui o valor do parametro
     *
     * @param valor O valor do parametro
     */
    public void setValor(Object valor) {
        this.valor = valor;
    }
}
