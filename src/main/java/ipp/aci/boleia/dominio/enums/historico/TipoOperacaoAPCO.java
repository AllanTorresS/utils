package ipp.aci.boleia.dominio.enums.historico;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Enumera os tipos de operações de exportação da APCO
 */
public enum TipoOperacaoAPCO implements IEnumComLabel<TipoOperacaoAPCO> {
	EXPORTAR_FROTA(0),
	EXPORTAR_VINCULO(1),
	EXPORTAR_VENDAS(2);


	private final Integer value;

	/**
	 * Construtor da enumeracao com o valor
	 * @param value valor que representa a enumeracao
	 */
	TipoOperacaoAPCO(Integer value) {
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
	public static TipoOperacaoAPCO obterPorValor(Integer value) {
		for(TipoOperacaoAPCO tipoOperacaoAPCO : TipoOperacaoAPCO.values()) {
			if(tipoOperacaoAPCO.value.equals(value)) {
				return tipoOperacaoAPCO;
			}
		}
		return null;
	}
}