package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica se pontos do kmv foram acumulados
 */
public enum TipoAcumuloKmv implements IEnumComLabel<TipoAcumuloKmv> {

	MOTORISTA(178),
	DONO_FROTA(212);

	private final Integer value;

	/**
	 * Construtor
	 *
	 * @param value O value do tipo
	 */
	TipoAcumuloKmv(Integer value) {
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
	public static TipoAcumuloKmv obterPorValor(Integer value) {
		for(TipoAcumuloKmv tipo : TipoAcumuloKmv.values()) {
			if(tipo.value.equals(value)) {
				return tipo;
			}
		}
		return null;
	}
}
