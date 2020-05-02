package ipp.aci.boleia.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Utilitario para calculo
 *
 */
public final class UtilitarioCalculo {

    private static final int RAIO_TERRA = 6371;

	/**
	 * Impede a instanciacao e a heranca
	 */
	private UtilitarioCalculo() {
		// Impede a instanciacao e a heranca
	}


    /**
     * Soma todos os valores presentes no stream fornecida
     * @param <T> Tipo de retorno
     * @param streamValores Stream com os valores a serem somados
     * @param funcaoMapeadora Função mapeadora que será usado no map-reduce.
     * @param <T> Tipo das valores presentes no stream
     * @return O resultado da soma
     */
    public static <T> BigDecimal somarValoresLista(Stream<T> streamValores, Function<T, BigDecimal> funcaoMapeadora) {
        if (streamValores == null || funcaoMapeadora == null) {
            return null;
        }

        return streamValores.map(funcaoMapeadora)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Soma todos os valores presentes no stream fornecida
     *
     * @param streamValores Stream com os valores a serem somados
     * @return O resultado da soma
     */
    public static BigDecimal somarValoresLista(Stream<BigDecimal> streamValores) {
        return somarValoresLista(streamValores, Function.identity());
    }

    /**
     * Soma os valores presentes na lista de objetos fornecida. <br>
     * Aceita como parâmetro uma função que será utilizada para obter de cada objeto o valor que deve ser somado.<br>
     * Se os objetos da lista já são do tipo {@link BigDecimal}, pode-se utilizar a função {@link Function#identity()}.<br>
     * <br>
     * Utiliza o método {@link Stream#reduce(Object, BinaryOperator)} para fazer a soma.
     *
     * @param listaObjetos lista de objetos
     * @param funcaoMapeadora função que extrai do objeto o valor a ser somado
     * @param <T> O tipo dos dados da lista
     * @return valor total da soma
     */
    public static <T> BigDecimal somarValoresLista(Collection<T> listaObjetos, Function<T, BigDecimal> funcaoMapeadora) {
        if (listaObjetos == null || funcaoMapeadora == null) {
            return null;
        }
        return somarValoresLista(listaObjetos.stream(), funcaoMapeadora);
    }

    /**
     * Soma todos os valores presentes na lista fornecida
     *
     * @param listaValores lista de valores
     * @return valor total da soma
     */
    public static BigDecimal somarValoresLista(Collection<BigDecimal> listaValores) {
        return somarValoresLista(listaValores, Function.identity());
    }

    /**
     * Calcula o valor da porcentagem de um determinado valor
     *
     * @param valorOriginal Valor original que sera calculado a porcentagem
     * @param porcentagem Valor da porcentagem que sera aplicada no calculo
     * @return Valor da porcentagem calculado
     */
    public static BigDecimal calcularPorcentagem(BigDecimal valorOriginal, BigDecimal porcentagem) {
        if (valorOriginal == null) {
            return null;
        } else if (porcentagem.equals(BigDecimal.ZERO)) {
            return BigDecimal.ZERO;
        }
        return valorOriginal.multiply(porcentagem.divide(new BigDecimal("100"), 4, BigDecimal.ROUND_HALF_UP));
    }

    /**
     * Método que dado um valor calcula o quantos por cento ele excede o valor original percentualmente.
     *
     * @param valor Valor atual
     * @param valorOriginal Valor original.
     * @return Valor excedente percentual.
     */
    public static BigDecimal calcularPorcentagemExcedente(BigDecimal valor, BigDecimal valorOriginal) {
        if (valorOriginal == null || valor == null || valorOriginal.equals(BigDecimal.ZERO)) {
            return null;
        }
        return valor.divide(valorOriginal, 8, BigDecimal.ROUND_HALF_UP)
                .add(new BigDecimal(-1))
                .multiply(new BigDecimal(100));
    }

    /**
     * Calcula a distancia entre duas coordenadas  em metros
     *
     * @param latitudeInicial latitude do ponto inicial
     * @param longitudeInicial longitude do ponto inicial
     * @param latitudeFinal latitude do ponto final
     * @param longitudeFinal longitude do ponto final
     * @return distancia em metros entre as duas coordenadas
     */
    public static BigDecimal calcularDistanciaEntrePontos(BigDecimal latitudeInicial, BigDecimal longitudeInicial, BigDecimal latitudeFinal, BigDecimal longitudeFinal) {
        double distanciaLatitude = Math.toRadians(latitudeFinal.doubleValue() - latitudeInicial.doubleValue());
        double distanciaLongitude = Math.toRadians(longitudeFinal.doubleValue() - longitudeInicial.doubleValue());
        double distanciaRadianos = Math.sin(distanciaLatitude / 2) * Math.sin(distanciaLatitude / 2)
                + Math.cos(Math.toRadians(latitudeInicial.doubleValue())) * Math.cos(Math.toRadians(latitudeFinal.doubleValue()))
                * Math.sin(distanciaLongitude / 2) * Math.sin(distanciaLongitude / 2);
        double distanciaGraus = 2 * Math.atan2(Math.sqrt(distanciaRadianos), Math.sqrt(1 - distanciaRadianos));
        double distanciaMetros = Math.pow(RAIO_TERRA * distanciaGraus * 1000, 2);
        return BigDecimal.valueOf(Math.sqrt(distanciaMetros)).setScale(2, RoundingMode.HALF_UP);
    }
}
