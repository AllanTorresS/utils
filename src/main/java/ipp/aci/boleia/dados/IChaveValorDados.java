package ipp.aci.boleia.dados;

/**
 * Interface que representa a manipulação de uma estrutura chave-valor
 * @param <T> Objeto que representa o valor a ser armazenado na estrutura
 */
public interface IChaveValorDados<T> {

    /**
     * Insere o valor na estrutura
     * @param nome O nome do mapa que armazena a chave/valor
     * @param chave String unica que referencia o valor
     * @param valor Valor generico a ser inserido
     */
    void inserir(String nome, String chave, T valor);

    /**
     * Obtém o valor na estrutura
     * @param nome O nome do mapa que armazena a chave/valor
     * @param chave String unica que referencia o valor
     * @return O valor armazenado
     */
    T obter(String nome, String chave);

    /**
     * Insere o valor na estrutura, com tempo de expiração em minutos
     * Quando o tempo, após sua insersão, o valor automaticamente é apagado.
     *
     * @param nome O nome do mapa que armazena a chave/valor
     * @param chave String unica que referencia o valor
     * @param valor Valor generico a ser inserido
     * @param minutosParaExpirar tempo em minutos para expirar
     */
    void inserirComExpiracao(String nome, String chave, T valor, long minutosParaExpirar);
}
