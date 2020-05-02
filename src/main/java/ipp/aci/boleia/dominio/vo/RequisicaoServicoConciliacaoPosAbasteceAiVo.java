package ipp.aci.boleia.dominio.vo;


/**
 * Classe com informacoes relacionadas a requisicao de conciliacao junto ao POS/Abastece Aí
 */
public class RequisicaoServicoConciliacaoPosAbasteceAiVo {

    private Long nsuZacReqOriginal;

    /**
     * Construtor default da classe.
     */
    public RequisicaoServicoConciliacaoPosAbasteceAiVo() {
        //construtor default
    }

    /**
     * Constrói a partir do número sequencial do POS.
     *
     * @param listaNsuZacc Número sequencial.
     */
    public RequisicaoServicoConciliacaoPosAbasteceAiVo(Long listaNsuZacc) {
        this.nsuZacReqOriginal = listaNsuZacc;
    }

    public Long getNsuZacReqOriginal() {
        return nsuZacReqOriginal;
    }

    public void setNsuZacReqOriginal(Long nsuZacReqOriginal) {
        this.nsuZacReqOriginal = nsuZacReqOriginal;
    }
}
