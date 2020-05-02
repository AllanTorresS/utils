package ipp.aci.boleia.util.excecao;

/**
 * Representa um erro ocorrido na comunicacao com um sistema de autenticacao remota
 */
public class ExcecaoAutenticacaoRemota extends ExcecaoBoleia {

    /**
     * Constroi uma instancia da excecao
     * @param t a causa
     * @param nomeSistema o nome do sistema
     */
    public ExcecaoAutenticacaoRemota(Throwable t, String nomeSistema) {
        super(Erro.ERRO_INTEGRACAO, t, nomeSistema);
    }
}
