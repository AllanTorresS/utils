package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Enumera as entidades Unidade e Empresa Agregada.
 */
public enum TipoEntidadeUnidadeEmpresaAgregada implements IEnumComLabel<TipoEntidadeUnidadeEmpresaAgregada>, IEnumComValor {

    MATRIZ(0),
    UNIDADE(1),
    EMPRESA_AGREGADA(2);

    private final Integer value;

    /**
     * Construtor da enumeracao com o valor
     *
     * @param value valor que representa a enumeracao
     */
    TipoEntidadeUnidadeEmpresaAgregada(Integer value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    /**
     * Obtem por valor
     *
     * @param value value
     * @return Enum para o valor
     */
    public static TipoEntidadeUnidadeEmpresaAgregada obterPorValor(Integer value) {
        for (TipoEntidadeUnidadeEmpresaAgregada tipoEntidade : TipoEntidadeUnidadeEmpresaAgregada.values()) {
            if (tipoEntidade.value.equals(value)) {
                return tipoEntidade;
            }
        }
        return null;
    }

    /**
     * Informa se o enum é igual a EMPRESA_AGREGADA.
     *
     * @return true, caso seja.
     */
    public boolean isEmpresaAgregada() {
        return equals(EMPRESA_AGREGADA);
    }

    /**
     * Informa se o enum é igual a UNIDADE.
     *
     * @return true, caso seja.
     */
    public boolean isUnidade() {
        return equals(UNIDADE);
    }

    /**
     * Informa se o enum é igual a MATRIZ.
     *
     * @return true, caso seja.
     */
    public boolean isMatriz() {
        return equals(MATRIZ);
    }
}