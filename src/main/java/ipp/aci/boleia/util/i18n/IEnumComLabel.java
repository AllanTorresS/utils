package ipp.aci.boleia.util.i18n;

/**
 * Contrato para implementacao de enumeracoes que possua label de apresentação
 * @param <E> O tipo da enumeração
 */
public interface IEnumComLabel<E extends Enum<E>> {

    /**
     * Obtem o label internacionalizado da enumeracao
     * @return O label internacionalizado da enumeracao
     */
    default String getLabel() {
        return Mensagens.obterLabelEnum(this);
    }
}
