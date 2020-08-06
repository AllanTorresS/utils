package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica o status da integração de uma entidade de historico de integração
 */
public enum StatusIntegracaoAPCO implements IEnumComLabel<StatusAtivacao> {

	FALHA(0),
	SUCESSO(1),
	JA_REALIZADA(2);

	private final Integer value;

	/**
	 * Construtor
	 *
	 * @param value O value do status
	 */
	StatusIntegracaoAPCO(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	/**
	 * Obtem por valor
	 * @param value value
	 * @return Enum para o valor
	 */
	public static StatusIntegracaoAPCO obterPorValor(Integer value) {
		for(StatusIntegracaoAPCO status : StatusIntegracaoAPCO.values()) {
			if(status.value.equals(value)) {
				return status;
			}
		}
		return null;
	}
}