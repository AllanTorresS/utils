package ipp.aci.boleia.dominio.vo;

/**
 * Representa uma entidade de bico para integração com o Integrador
 */
public class BicoIntegradorVo {

    private String codigoBico;

    private Integer numeroPista;

    private ProdutoCombustivelIntegradorVo produtoCombustivel;

    public Integer getNumeroPista() {
        return numeroPista;
    }

    public String getCodigoBico() {
        return codigoBico;
    }

    public void setCodigoBico(String codigoBico) {
        this.codigoBico = codigoBico;
    }

    public void setNumeroPista(Integer numeroPista) {
        this.numeroPista = numeroPista;
    }

    public ProdutoCombustivelIntegradorVo getProdutoCombustivel() {
        return produtoCombustivel;
    }

    public void setProdutoCombustivel(ProdutoCombustivelIntegradorVo produtoCombustivel) {
        this.produtoCombustivel = produtoCombustivel;
    }
}
