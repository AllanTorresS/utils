package ipp.aci.boleia.dominio.pesquisa.parametro;

import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;

/**
 * Representa um parametro de pesquisa para clausula EMPTY,
 * a ser usado pelos repositorios do sistema.
 */
public class ParametroPesquisaEmpty extends ParametroPesquisa {

    private Boolean not;

    /**
     * Construtor
     *
     * @param nome  O nome do parametro
     */
    public ParametroPesquisaEmpty(String nome) {
        super(nome, null);
        not = false;
    }

    /**
     * Construtor
     *
     * @param nome O nome do parametro
     * @param not  Se deve ser não empty
     *
     */
    public ParametroPesquisaEmpty(String nome, Boolean not) {
        super(nome, null);
        this.not = not;
    }

    public Boolean getNot() {
        return not;
    }

    public void setNot(Boolean not) {
        this.not = not;
    }
}
