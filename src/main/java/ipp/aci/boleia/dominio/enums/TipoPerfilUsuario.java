package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.dominio.TipoPerfil;
import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Tipo de perfil acesso dos usuários
 */
public enum TipoPerfilUsuario implements IEnumComLabel<TipoPerfilUsuario> {

    FROTA(1L),
    INTERNO(2L),
    REVENDA(3L),
    PRECOS(4L),
    SISTEMA_EXTERNO(5L),
    MOTORISTA(6L),
    MODULO_INTERNO(7L);

    private final Long value;

    /**
     * Construtor
     * @param value O valor da enum
     */
    TipoPerfilUsuario(Long value) {
        this.value = value;
    }

    /**
     * Obtem por valor
     *
     * @param value value
     * @return Enum para o valor
     */
    public static TipoPerfilUsuario obterPorValor(Long value) {
        for (TipoPerfilUsuario tipoPerfil : TipoPerfilUsuario.values()) {
            if (tipoPerfil.value.equals(value)) {
                return tipoPerfil;
            }
        }
        return null;
    }

    /**
     * Obtém uma instância do enum com nome correspondente a string passada.
     *
     * @param nome Nome do enum.
     * @return Enum com o nome correspondente ou nulo.
     */
    public static TipoPerfilUsuario obterPorNome(String nome) {
        for (TipoPerfilUsuario tipoPerfil : TipoPerfilUsuario.values()) {
            if (tipoPerfil.toString().equals(nome)) {
                return tipoPerfil;
            }
        }
        return null;
    }

    /**
     * Método que constrói uma entidade de tipo perfil.
     *
     * @return Entidade de Tipo Perfil
     */
    public TipoPerfil obterEntidade() {
        TipoPerfil entidade = new TipoPerfil();
        entidade.setId(value);
        entidade.setNome(getLabel());
        return entidade;
    }

    /**
     * Obtem o valor da enumercao
     * @return o valor da enumeracao
     */
    public Long getValue() {
        return value;
    }
}
