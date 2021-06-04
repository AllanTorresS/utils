package ipp.aci.boleia.dominio.vo;

/**
 * Classe responsável pelo Vo do item da danfe.
 */
public class ItemDanfeVo {

    private String codigoProduto;
    private String descProdutoServico;
    private String ncmsh;
    private String cst;
    private String cfop;
    private String unid;
    private String qtde;
    private String valorUnitario;
    private String desconto;
    private String valorLiquido;

    private String baseCalcIcms;
    private String valorIcms;
    private String valorIpi;
    private String alqIcms;
    private String alqIpi;

    private String infAdProd;

    /**
     * Construtor default.
     */
    public ItemDanfeVo(){
        //Construtor default
    }

    public String getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(String codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public String getDescProdutoServico() {
        return descProdutoServico;
    }

    public void setDescProdutoServico(String descProdutoServico) {
        this.descProdutoServico = descProdutoServico;
    }

    public String getNcmsh() {
        return ncmsh;
    }

    public void setNcmsh(String ncmsh) {
        this.ncmsh = ncmsh;
    }

    public String getCst() {
        return cst;
    }

    public void setCst(String cst) {
        this.cst = cst;
    }

    public String getCfop() {
        return cfop;
    }

    public void setCfop(String cfop) {
        this.cfop = cfop;
    }

    public String getUnid() {
        return unid;
    }

    public void setUnid(String unid) {
        this.unid = unid;
    }

    public String getQtde() {
        return qtde;
    }

    public void setQtde(String qtde) {
        this.qtde = qtde;
    }

    public String getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(String valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public String getDesconto() {
        return desconto;
    }

    public void setDesconto(String desconto) {
        this.desconto = desconto;
    }

    public String getValorLiquido() {
        return valorLiquido;
    }

    public void setValorLiquido(String valorLiquido) {
        this.valorLiquido = valorLiquido;
    }

    public String getBaseCalcIcms() {
        return baseCalcIcms;
    }

    public void setBaseCalcIcms(String baseCalcIcms) {
        this.baseCalcIcms = baseCalcIcms;
    }

    public String getValorIcms() {
        return valorIcms;
    }

    public void setValorIcms(String valorIcms) {
        this.valorIcms = valorIcms;
    }

    public String getValorIpi() {
        return valorIpi;
    }

    public void setValorIpi(String valorIpi) {
        this.valorIpi = valorIpi;
    }

    public String getAlqIcms() {
        return alqIcms;
    }

    public void setAlqIcms(String alqIcms) {
        this.alqIcms = alqIcms;
    }

    public String getAlqIpi() {
        return alqIpi;
    }

    public void setAlqIpi(String alqIpi) {
        this.alqIpi = alqIpi;
    }

    public String getInfAdProd() { return infAdProd; }

    public void setInfAdProd(String infAdProd) { this.infAdProd = infAdProd; }
}
