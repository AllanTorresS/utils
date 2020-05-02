package ipp.aci.boleia.dominio.enums.campanha.parametros;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

import java.util.Arrays;


/**
 * Enum que indica o tipo de um Parametro das campanhas do sistema
 */
public enum TipoParametroCampanha implements IEnumComLabel<TipoParametroCampanha> {
	ANTIGUIDADE(0),
	DESCONTO(1);


	private final Integer value;

	/**
	 * Construtor
	 *
	 * @param value O value que representa o tipo de parametro para as campanhas do sistema
	 */
	TipoParametroCampanha(Integer value) {
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
	public static TipoParametroCampanha obterPorValor(Integer value) {
		for (TipoParametroCampanha tipo : TipoParametroCampanha.values()) {
			if (tipo.value.equals(value)) {
				return tipo;
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
	public static TipoParametroCampanha obterPorNome(String nome) {
		return Arrays.stream(values())
				.filter(e -> e.name().equals(nome))
				.findAny()
				.orElse(null);
	}
}

