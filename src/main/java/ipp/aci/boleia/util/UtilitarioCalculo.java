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
    private static final int MES_EM_DIAS = 30;

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
     * Utiliza o método {@link java.util.stream.Stream#reduce(Object, BinaryOperator)} para fazer a soma.
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
        return calcularPorcentagem(valorOriginal, porcentagem, 4);
    }

    /**
     * Calcula o valor da porcentagem de um determinado valor
     *
     * @param valorOriginal Valor original que sera calculado a porcentagem
     * @param porcentagem Valor da porcentagem que sera aplicada no calculo
     * @param scale O número máximo de casas decimais a serem mantidos no resultado final
     * @return Valor da porcentagem calculado
     */
    public static BigDecimal calcularPorcentagem(BigDecimal valorOriginal, BigDecimal porcentagem, int scale) {
        if (valorOriginal == null || porcentagem == null) {
            return null;
        } else if (BigDecimal.ZERO.equals(porcentagem)) {
            return BigDecimal.ZERO;
        }
        return valorOriginal.multiply(porcentagem.divide(new BigDecimal("100"), scale, BigDecimal.ROUND_HALF_UP));
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

    /**
     * Obtém o valor de juros cobrados sobre um valor inicial, quando aplicada uma taxa mensal por um número de dias
     *
     * @param valorInicial O valor inicial sobre o qual serão aplicados os juros
     * @param taxaMensal A taxa mensal que incide sobre o valor inicial
     * @param numeroDias A quantidade de dias da aplicação
     * @return o total de juros cobrados sobre o valor inicial
     */
    public static BigDecimal calcularValorTotalJuros(BigDecimal valorInicial, BigDecimal taxaMensal, int numeroDias) {
        BigDecimal taxaDiaria = converterTaxaMensalParaDiaria(taxaMensal);
        return valorInicial.multiply((BigDecimal.ONE.add(taxaDiaria)).pow(numeroDias)).subtract(valorInicial);
    }

    /**
     * Obtém a taxa de juros diária a partir de uma taxa de juros mensal
     *
     * @param taxaMensal a taxa de juros a ser convertida
     * @return a taxa de juros diária
     */
    public static BigDecimal converterTaxaMensalParaDiaria(BigDecimal taxaMensal) {
        return BigDecimal.valueOf(Math.pow((BigDecimal.ONE.add(taxaMensal)).doubleValue(), 1.0/MES_EM_DIAS)).subtract(BigDecimal.ONE);
    }
}
