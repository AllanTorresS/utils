package ipp.aci.boleia.dominio.vo.exportacao;

import java.util.List;

/**
 * Vo com as informações utilizadas na exportação de PDF da tela do Financeiro.
 */
public class DadosExportacaoPdfFinanceiroVo {
    private String filtroPeriodo;
    private String filtroFrota;
    private String filtroPosto;
    private String filtroStatus;
    private String reembolsoTotalPago;
    private Integer totalRegistros;
    private List<CicloExportacaoPdfFinanceiroVo> ciclosExportacao;

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

    public List<CicloExportacaoPdfFinanceiroVo> getCiclosExportacao() {
        return ciclosExportacao;
    }

    public void setCiclosExportacao(List<CicloExportacaoPdfFinanceiroVo> ciclosExportacao) {
        this.ciclosExportacao = ciclosExportacao;
    }

    public Integer getTotalRegistros() {
        return totalRegistros;
    }

    public void setTotalRegistros(Integer totalRegistros) {
        this.totalRegistros = totalRegistros;
    }
}
