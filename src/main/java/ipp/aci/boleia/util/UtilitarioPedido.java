package ipp.aci.boleia.util;

/**
 * Utilitario para formatacao de datas
 */
public final class UtilitarioPedido {
    private static final int VALOR_DESLOCAMENTO = 2;
    private static final int NUMERO_DESLOCAMENTO = 7;


    /**
    * Impede a instanciacao e a heranca
    */
    private UtilitarioPedido() {
        // Impede a instanciacao e a heranca
    }

    /**
     * Cria um numero de pedido
     *
     * @param seed Seed para gerar o pedido
     * @param tamanho o tamanho do pedido
     * @return O código do pedido
     */
    public static String gerarPedido(long seed, int tamanho) {
        int[] vertorPedido = converterSeedEmVetor(seed, tamanho);

        embaralharNumeroPedido(vertorPedido, tamanho);
        deslocarNumeroPedido(vertorPedido, tamanho);
        StringBuilder pedido = new StringBuilder();
        for(int i =0 ; i<  tamanho; i++){
            pedido.append(vertorPedido[i]);
        }
        return pedido.toString();
    }

    /**
     * Cria um numero de pedido com valor inicial
     *
     * @param seed Seed para gerar o pedido
     * @param tamanho o tamanho do pedido
     * @param valorInicial o valor inicial
     * @return O código do pedido
     */
    public static String gerarPedido(long seed, int tamanho, long valorInicial) {
        int tamanhoValorInical = String.valueOf(valorInicial).length();
        return valorInicial + gerarPedido(seed,tamanho - tamanhoValorInical);
    }

    /**
     * Converte a seed em vetor
     *
     * @param seed Seed para gerar o pedido
     * @param tamanho tamanho máximo
     * @return O vetor
     */
    private static int[] converterSeedEmVetor(long seed, int tamanho) {
        String pedidoSeed =  UtilitarioFormatacao.formatarNumeroZerosEsquerda(seed, tamanho);
        int[] vertorPedido = new int[tamanho];
        for(int i=0; i<pedidoSeed.length(); i++) {
            vertorPedido[i] = Integer.parseInt(pedidoSeed.substring(i, i+1));
        }
        return vertorPedido;
    }

    /**
     * Converte a seed em vetor
     *
     * @param vertorPedido o vetor do pedido
     * @param tamanho tamanho máximo
     * @return O vetor
     */
    private static int[] deslocarNumeroPedido(int[] vertorPedido, int tamanho) {
        for(int i=0; i<tamanho; i++){
            vertorPedido[i] = deslocarValor(vertorPedido[i], (i+ NUMERO_DESLOCAMENTO) %10);
        }
        return vertorPedido;
    }

    /**
     * Embaralhar numero pedido
     *
     * @param caracterIds o vetor de Ids
     * @param tamanho tamanho máximo
     * @return O vetor
     */
    private static int[] embaralharNumeroPedido(int[] caracterIds, int tamanho){
        int auxiliar;
        for(int i=0; i<tamanho;i++){
            if(i* VALOR_DESLOCAMENTO >=tamanho){
                break;
            }
            auxiliar=caracterIds[i* VALOR_DESLOCAMENTO];
            caracterIds[i* VALOR_DESLOCAMENTO]=caracterIds[i];
            caracterIds[i]=auxiliar;
        }
        return caracterIds;
    }

    /**
     * Desloca Valor
     *
     * @param valor o vetor de Ids
     * @param deslocamento tamanho do deslocamento
     * @return o valor deslocado
     */
    private static int deslocarValor(int valor, int deslocamento ){
        return ((valor+deslocamento) % 10);
    }
}
