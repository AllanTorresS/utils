package ipp.aci.boleia.dominio.enums.campanha;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Contarp para implementação de enums utilizandos em campanhas que devam apresentar o campo label
 *
 * @param <E> O tipo da enumeração
 */
public interface IEnumComValorCampanha<E extends Enum<E>> extends IEnumComLabel<E> {
    /**
     * Obtém o valor do enum
     *
     * @return O valor do enum
     */
    String obterValor();
}
