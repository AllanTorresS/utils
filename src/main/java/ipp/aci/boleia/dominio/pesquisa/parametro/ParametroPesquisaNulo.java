package ipp.aci.boleia.dominio.pesquisa.parametro;

import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;

/**
 * Representa um parametro de pesquisa para clausula SQL ISNULL,
 * a ser usado pelos repositorios do sistema
 */
public class ParametroPesquisaNulo extends ParametroPesquisa {

    private Boolean not;

    /**
     * Construtor
     *
     * @param nome  O nome do parametro
     */
    public ParametroPesquisaNulo(String nome) {
        super(nome, null);
        not = false;
    }

    /**
     * Construtor
     *
     * @param nome O nome do parametro
     * @param not  Se deve ser não nulo
     *
     */
    public ParametroPesquisaNulo(String nome, Boolean not) {
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
