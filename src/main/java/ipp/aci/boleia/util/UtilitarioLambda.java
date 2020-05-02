package ipp.aci.boleia.util;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Utilitario para simplificacao de expressoes Java 8 Lambda/Streams
 */
public final class UtilitarioLambda {

    /**
     * Impede instanciacao e heranca
     */
    private UtilitarioLambda() {
       // impede instanciacao e heranca
    }

    /**
     * Converte uma lista de objetos do tipo <code>T</code> em uma lista de objetos do tipo <code>R</code>
     * dado o mapeador de T para R
     *
     * @param lista A lista original
     * @param funcaoMapeadora O mapeador
     * @param <T> O tipo origem da transformacao
     * @param <R> O tipo alvo da transformacao
     * @return Uma lista de objetos do tipo alvo
     */
    public static <R,T> List<R> converterLista(Collection<T> lista, Function<T,R> funcaoMapeadora) {
        if (lista == null) {
            return null;
        }
        return lista.stream().map(funcaoMapeadora).collect(Collectors.toList());
    }

    /**
     * Método que realiza o mapeamento de uma lista para um Mapa
     *
     * @param lista A lista
     * @param keyMapper A função que obtém a chave
     * @param valueMapper A função que obtém o valor
     * @param <T> O tipo origem da lista
     * @param <K> O tipo alvo da chave
     * @param <V> O tipo alvo do valor
     * @return Um mapa
     */
    public static <T, K, V> Map<K,V> mapearLista(List<T> lista, Function<T,K> keyMapper, Function<T,V> valueMapper) {
        if (lista == null) {
            return null;
        }
        return lista.stream().collect(Collectors.toMap(keyMapper, valueMapper));
    }

    /**
     * Metodo que agrupa os elementos de uma lista em um Mapa, utilizando como chave o retorno da função fornecida
     *
     * @param lista A lista
     * @param funcaoMapeadora a função mapeadora
     * @param <T> o tipo origem da lista
     * @param <K> o tipo alvo da chave
     * @return uma lista agrupada
     */
    public static <T,K> Map<K,List<T>> agrupar(List<T> lista, Function<? super T, ? extends K> funcaoMapeadora) {
        if (lista == null) {
            return null;
        }
        return lista.stream().collect(Collectors.groupingBy(funcaoMapeadora));
    }

    /**
     * Verifica se todos os objetos da lista são não-nulos
     *
     * @param objetos lista de objetos
     * @return True se todos os objetos forem não-nulos
     */
    public static boolean verificarTodosNaoNulos(Object... objetos) {
        return Stream.of(objetos).allMatch(Objects::nonNull);
    }

    /**
     * Obtem o primeiro resultado da lista
     * @param lista lista de objetos
     * @param <T> tipo da lista
     * @return o primeiro objeto da lista
     */
    public static <T> T obterPrimeiroObjetoDaLista(List<T> lista){
        return CollectionUtils.isNotEmpty(lista) ? lista.stream().findFirst().get() : null;
    }

    /**
     * Concatena uma série de listas em uma única lista.
     *
     * @param listas Conjunto de listas a ser concatenada.
     * @param <T>    Tipo de origem da lista
     * @return Lista unificada
     */
    public static <T> List<T> concatenarListas(List<T>... listas) {
        Stream<T> listaConcatenada = Stream.of();
        for (List<T> lista : listas) {
            listaConcatenada = Stream.concat(listaConcatenada, lista.stream());
        }
        return listaConcatenada.collect(Collectors.toList());
    }

    /**
     * Filtra uma lista removendo duplicadas de acordo com uma propriedade do objeto
     * @param lista a lista a ser filtrada
     * @param extratorChave a propriedade a ser utilizada como chave
     * @param <T> O tipo da lista
     * @return a lista filtrada
     */
    public static <T> List<T> filtrarDistintosPorPropriedade(List<T> lista, Function<? super T, ?> extratorChave) {
        return lista.stream().filter(predicadoDistintoPorPropriedade(extratorChave)).collect(Collectors.toList());
    }

    /**
     * Gera predicado para filtrar uma lista removendo duplicatas de acordo com uma propriedade do objeto
     * @param extratorChave a chave a ser utilizada para buscar duplicatas
     * @param <T> O tipo do objeto
     * @return O predicado a ser utilizado em uma lambda
     */
    private static <T> Predicate<T> predicadoDistintoPorPropriedade(Function<? super T, ?> extratorChave) {
        Set<Object> visto = new HashSet<>();
        return t -> visto.add(extratorChave.apply(t));
    }
}
