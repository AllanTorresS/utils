package ipp.aci.boleia.util.validador;

import ipp.aci.boleia.util.excecao.ExcecaoValidacao;

/**
 * Contrato para implementacao de tratadores personalizados de erro
 */
@FunctionalInterface
public interface TratadorErroValidacao {

    /**
     * Trata um erro
     * @param message A mensagem do erro
     * @throws ExcecaoValidacao Caso seja necessario interromper o fluxo lancando uma excecao com o erro
     */
    void tratarErro(String message) throws ExcecaoValidacao;

}