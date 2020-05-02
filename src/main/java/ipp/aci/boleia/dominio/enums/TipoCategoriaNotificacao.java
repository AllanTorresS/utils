package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.dominio.CategoriaNotificacao;
import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Tipo de categoria da notificacao a ser disparada
 */
public enum TipoCategoriaNotificacao implements IEnumComLabel<TipoCategoriaNotificacao> {

    GERAIS(1L),
    FINANCEIRAS(2L),
    NEGOCIACOES(3L);

    private final Long value;

    /**
     * Construtor
     * @param value O valor da enum
     */
    TipoCategoriaNotificacao(Long value) {
        this.value = value;
    }

    /**
     * Obtem por valor
     *
     * @param value value
     * @return Enum para o valor
     */
    public static TipoCategoriaNotificacao obterPorValor(Long value) {
        for (TipoCategoriaNotificacao tipoCategoriaNotificacao : TipoCategoriaNotificacao.values()) {
            if (tipoCategoriaNotificacao.value.equals(value)) {
                return tipoCategoriaNotificacao;
            }
        }
        return null;
    }

    /**
     * Método que constrói uma entidade de categoria notiricacao.
     *
     * @return Entidade de Categoria Notificacao
     */
    public CategoriaNotificacao obterEntidade() {
        CategoriaNotificacao entidade = new CategoriaNotificacao();
        entidade.setId(value);
        entidade.setChave(name());
        return entidade;
    }

    public Long getValue() {
        return value;
    }
}
