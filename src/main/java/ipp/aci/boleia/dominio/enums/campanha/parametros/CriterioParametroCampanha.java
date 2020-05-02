package ipp.aci.boleia.dominio.enums.campanha.parametros;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

import java.util.Arrays;


/**
 * Enum que indica o critério de um Parametro das campanhas do sistema
 */
public enum CriterioParametroCampanha implements IEnumComLabel<CriterioParametroCampanha> {
	MAIOR(0),
	MENOR(1);


	private final Integer value;

	/**
	 * Construtor
	 *
	 * @param value O value que representa o critério adotado para o parametro das campanhas do sistema
	 */
	CriterioParametroCampanha(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	/**
	 * Obtem por valor
	 *
	 * @param value value
	 * @return Enum para o valor
	 */
	public static CriterioParametroCampanha obterPorValor(Integer value) {
		for (CriterioParametroCampanha criterio : CriterioParametroCampanha.values()) {
			if (criterio.value.equals(value)) {
				return criterio;
			}
		}
		return null;
	}

	/**
	 * Obtem por nome
	 *
	 * @param nome value
	 * @return Enum para o nome
	 */
	public static CriterioParametroCampanha obterPorNome(String nome) {
		return Arrays.stream(values())
				.filter(e -> e.name().equals(nome))
				.findAny()
				.orElse(null);
	}
}

