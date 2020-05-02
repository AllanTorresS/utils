package ipp.aci.boleia.dominio.vo;

/**
 * Classe com informacoes relacionadas ao CouchDB
 */
public class CouchDbFindVo {

    private SelectorVo selector;

    /**
     * Construtor sem parametros
     */
    public CouchDbFindVo() {
        this.selector = new SelectorVo();
    }

    public SelectorVo getSelector() {
        return selector;
    }

    public void setSelector(SelectorVo selector) {
        this.selector = selector;
    }
}
