package ipp.aci.boleia.dominio.pesquisa.parametro;

import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;

import java.util.Collection;

/**
 * Representa um parametro de pesquisa para clausula SQL IN, 
 * a ser usado pelos repositorios do sistema
 */
public class ParametroPesquisaIn extends ParametroPesquisa {
	
	private Boolean not;

	/**
	 * Construtor
	 * @param nome  O nome do parametro
	 * @param valor Um conjunto de valores aceitos
	 */
	public ParametroPesquisaIn(String nome, Collection<?> valor) {
		super(nome, valor);
	}
	
    /**
     * Construtor
     *
     * @param nome O nome do parametro
     * @param not  Se deve ser não nulo
     *
     */
    public ParametroPesquisaIn(String nome, Collection<?> valor, Boolean not) {
        super(nome, valor);
        this.not = not;
    }

    public Boolean getNot() {
        return not;
    }

    public void setNot(Boolean not) {
        this.not = not;
    }

}