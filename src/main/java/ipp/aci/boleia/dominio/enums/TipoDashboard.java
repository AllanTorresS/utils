package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica o tipo de dashboard de um usuario.
 */
public enum TipoDashboard implements IEnumComLabel<TipoDashboard> {

    NENHUM(-1, null),
    INTERNO_SIMPLES(0, TipoPerfilUsuario.INTERNO.getValue()),
    INTERNO_GERAL(1, TipoPerfilUsuario.INTERNO.getValue()),
    COMERCIAL(2, TipoPerfilUsuario.INTERNO.getValue()),
    FINANCEIRO(3, TipoPerfilUsuario.INTERNO.getValue()),
    FROTA(4, TipoPerfilUsuario.FROTA.getValue()),
    REVENDA(5, TipoPerfilUsuario.REVENDA.getValue());

    public static final String DECODE_FORMULA = "DECODE(ID_TIPO_DASHBOARD, -1, 'Nenhum', 0, 'Interno Simples', 1, 'Interno Geral', 2, 'Comercial', 3, 'Financeiro', 4, 'Frota', 5, 'Revenda')";

    private final Integer value;
    private final Long tipoPerfil;

    /**
     * Construtor
     *
     * @param value O valor do enum
     */
    TipoDashboard(final Integer value, final Long tipoPerfil) {
        this.value = value;
        this.tipoPerfil = tipoPerfil;
    }

    /**
     * Obtem o valor da enum
     *
     * @return O valor da enum
     */
    public Integer getValue() {
        return value;
    }

    public Long getTipoPerfil() {
        return tipoPerfil;
    }

    /**
     * Obtem por valor
     *
     * @param value value
     * @return Enum para o valor
     */
    public static TipoDashboard obterPorValor(final Integer value) {
        for (final TipoDashboard tipoDashboard : TipoDashboard.values()) {
            if (tipoDashboard.value.equals(value)) {
                return tipoDashboard;
            }
        }
        return null;
    }

    /**
     * Obtem por valor
     *
     * @param value value
     * @return Enum para o valor
     */
    public static TipoDashboard obterPorValor(String value) {
        return obterPorValor(Integer.parseInt(value));
    }

    /**
     * Valida se o id de tipo de perfil eh invalido para o tipo dashboard.
     * @param idTipoPerfil id de tipo de perfil.
     * @return true se o id de tipo de perfil for invalido para o tipo dashboard.
     */
    public boolean isPerfilInvalido(final Long idTipoPerfil) {
        return this.tipoPerfil != null  && !this.tipoPerfil.equals(idTipoPerfil);
    }
}