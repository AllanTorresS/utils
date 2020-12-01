package ipp.aci.boleia.dominio.vo.exportacao;

/**
 * Vo com as informações utilizadas na exportação de PDF da tela do Financeiro.
 */
public class DadosExportacaoPdfFinanceiroVo extends DadosExportacaoPdfBaseVo {
    private String filtroPeriodo;
    private String filtroFrota;
    private String filtroFrotaCnpj;
    private String filtroPosto;
    private String filtroPostoCnpj;
    private String filtroStatus;
    private String reembolsoTotalPago;

    public String getFiltroPeriodo() {
        return filtroPeriodo;
    }

    public void setFiltroPeriodo(String filtroPeriodo) {
        this.filtroPeriodo = filtroPeriodo;
    }

    public String getFiltroFrota() {
        return filtroFrota;
    }

    public void setFiltroFrota(String filtroFrota) {
        this.filtroFrota = filtroFrota;
    }

    public String getFiltroPosto() {
        return filtroPosto;
    }

    public void setFiltroPosto(String filtroPosto) {
        this.filtroPosto = filtroPosto;
    }

    public String getFiltroFrotaCnpj() {
        return filtroFrotaCnpj;
    }

    public void setFiltroFrotaCnpj(String filtroFrotaCnpj) {
        this.filtroFrotaCnpj = filtroFrotaCnpj;
    }

    public String getFiltroPostoCnpj() {
        return filtroPostoCnpj;
    }

    public void setFiltroPostoCnpj(String filtroPostoCnpj) {
        this.filtroPostoCnpj = filtroPostoCnpj;
    }

    public String getFiltroStatus() {
        return filtroStatus;
    }

    public void setFiltroStatus(String filtroStatus) {
        this.filtroStatus = filtroStatus;
    }

    public String getReembolsoTotalPago() {
        return reembolsoTotalPago;
    }

    public void setReembolsoTotalPago(String reembolsoTotalPago) {
        this.reembolsoTotalPago = reembolsoTotalPago;
    }
}
