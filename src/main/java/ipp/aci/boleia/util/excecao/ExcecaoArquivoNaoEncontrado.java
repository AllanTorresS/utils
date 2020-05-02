package ipp.aci.boleia.util.excecao;

/**
 * Excecao lançada ao nao encontrar arquivo no storage
 */
public class ExcecaoArquivoNaoEncontrado extends ExcecaoBoleia {

    /**
     * Construtor de Excecao Boleia
     * de Arquivo Nao Encontrado
     */
    public ExcecaoArquivoNaoEncontrado() {
        super(Erro.ARQUIVO_NAO_ENCONTRADO);
    }
}
