package ipp.aci.boleia.util.excecao;

/**
 * Contrato para implementacao de excecoes do sistema
 */
public interface IExcecaoBoleia {

    /**
     * Retorna o erro a ser reportado
     *
     * @return O erro a ser reportado
     */
    Erro getErro();

    /**
     * Retorna os argumentos para montagem da mensagem de erro
     *
     * @return Os argumentos para montagem da mensagem
     */
    Object[] getArgs();
}
