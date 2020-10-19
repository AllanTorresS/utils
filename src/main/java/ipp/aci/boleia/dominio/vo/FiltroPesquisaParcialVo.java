package ipp.aci.boleia.dominio.vo;

/**
 * Filtro padrão para busca parcial
 */
public class FiltroPesquisaParcialVo {

    protected String termo;

    /**
     * Construtor padrão
     */
    public FiltroPesquisaParcialVo() {
        //Construtor default
    }

    public String getTermo() {
        return termo;
    }

    public void setTermo(String termo) {
        this.termo = termo;
    }
}
