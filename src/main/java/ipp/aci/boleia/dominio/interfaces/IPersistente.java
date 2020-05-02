package ipp.aci.boleia.dominio.interfaces;

import java.io.Serializable;

/**
 * Contrato para implementacao de classes persistentes
 */
public interface IPersistente extends Serializable {

	/**
	 * Obtem o id da entidade
	 *
	 * @return O id da entidade
	 */
	Long getId();


	/**
	 * Atribui o id da entidade
	 *
	 * @param id O id da entidade
	 */
	void setId(Long id);
}
