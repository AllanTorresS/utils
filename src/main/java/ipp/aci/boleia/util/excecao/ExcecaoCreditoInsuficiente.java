package ipp.aci.boleia.util.excecao;

/**
 * Representa um erro ocorrido durante o processamento de uma transacao financeira
 * quando a mesma nao pode ser completada por falta de credito
 */
public class ExcecaoCreditoInsuficiente extends ExcecaoBoleia {

    /**
     * Constroi uma instancia da excecao
     */
    public ExcecaoCreditoInsuficiente() {
        super(Erro.CREDITO_INSUFICIENTE);
    }
}
