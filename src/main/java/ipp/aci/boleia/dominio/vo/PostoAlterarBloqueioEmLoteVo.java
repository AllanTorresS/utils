package ipp.aci.boleia.dominio.vo;

import java.util.List;

/**
 * Representa o envio da alteração em lote de bloqueio de postos para uma frota
 *
 */
public class PostoAlterarBloqueioEmLoteVo {

    private boolean bloqueio;
    private List<Long> frotaPtovIds;

    /**
     * Construtor default
     */
    public PostoAlterarBloqueioEmLoteVo() {
        //Construtor default
    }

    /**
     * Construtor da classe.
     *
     * @param frotaPtovIds Identificadores de registros frotaPtov
     * @param bloqueio Flag de bloqueio
     */
    public PostoAlterarBloqueioEmLoteVo(List<Long> frotaPtovIds, boolean bloqueio) {
        this.frotaPtovIds = frotaPtovIds;
        this.bloqueio = bloqueio;
    }

    public boolean getBloqueio() {
        return bloqueio;
    }

    public void setBloqueio(boolean bloqueio) {
        this.bloqueio = bloqueio;
    }

    public List<Long> getFrotaPtovIds() {
        return frotaPtovIds;
    }

    public void setFrotaPtovIds(List<Long> frotaPtovIds) {
        this.frotaPtovIds = frotaPtovIds;
    }
}
