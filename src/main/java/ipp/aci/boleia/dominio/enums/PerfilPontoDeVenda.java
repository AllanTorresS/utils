package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.dominio.enums.campanha.IEnumComValorCampanha;

/**
 * Tipos de perfis de ponto de venda.
 */
public enum PerfilPontoDeVenda implements IEnumComValorCampanha<PerfilPontoDeVenda> {

    RODO_REDE("Rodo Rede"),
	RODOVIA("Rodovia"),
	URBANO("Urbano"),
	OUTROS("Outros");

    private final String value;

    /**
     * Construtor
     *
     * @param value O valor do perfil de ponto de venda.
     */
    PerfilPontoDeVenda(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static PerfilPontoDeVenda obterPorValor(String value) {
        for(PerfilPontoDeVenda status : PerfilPontoDeVenda.values()) {
            if(status.getValue().equalsIgnoreCase(value)) {
                return status;
            }
        }
        return null;
    }

    @Override
    public String obterValor() {
        return this.value;
    }
}
