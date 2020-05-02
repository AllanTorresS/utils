package ipp.aci.boleia.dominio.interfaces;

import java.io.Serializable;

/**
 * Contrato para implementacao de entidades que possuam identificador de hardware
 */
public interface IPossuiFingerprint extends Serializable {

	/**
	 * Obtem a lista de campos que forma o fingerprint da entidade
	 * @return Uma lista de valores textuais
	 */
	String[] getCamposFingerprint();
}
