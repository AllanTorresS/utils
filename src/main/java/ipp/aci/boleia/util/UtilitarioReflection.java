package ipp.aci.boleia.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Utilitário criado para centralizar as operações realizadas via reflection no sistema.
 *
 * @author pedro.silva
 */
public final class UtilitarioReflection {

    /**
     * Obtém a anotação presente no atributo de uma classe específica.
     *
     * @param clazz Classe do atributo com a anotação buscada.
     * @param nomeAtributo Nome do atributo com a anotação buscada.
     * @param anotacao Classe da anotação que será buscada.
     * @param <T> Tipo da classe que possui o atributo.
     * @param <A> Tipo da classe da anotação.
     *
     * @return A anotação encontrada ou nulo caso o atributo ou a anotação não sejam encontrados.
     */
    public static <T, A extends Annotation> A obterAnotacaoPorAtributo(Class<T> clazz, String nomeAtributo, Class<A> anotacao) {
        try {
            Field campo = clazz.getDeclaredField(nomeAtributo);
            if(campo.isAnnotationPresent(anotacao)) {
                return campo.getAnnotation(anotacao);
            }
            return null;
        } catch (NoSuchFieldException e) {
            return null;
        }
    }
}
