package ipp.aci.boleia.dominio.vo.exportacao;

public class DadosExportacaoPdfCreditoFrotaVo extends DadosExportacaoPdfBaseVo<RegistroExportacaoPdfCreditoFrotaVo> {
    private String filtroFrota;
    private String filtroModalidadePagamento;
    private String filtroStatusFrota;
    private String filtroUf;
    private String filtroCidade;

    public String getFiltroFrota() {
        return filtroFrota;
    }

    public void setFiltroFrota(String filtroFrota) {
        this.filtroFrota = filtroFrota;
    }

    public String getFiltroModalidadePagamento() {
        return filtroModalidadePagamento;
    }

    public void setFiltroModalidadePagamento(String filtroModalidadePagamento) {
        this.filtroModalidadePagamento = filtroModalidadePagamento;
    }

    public String getFiltroStatusFrota() {
        return filtroStatusFrota;
    }

    public void setFiltroStatusFrota(String filtroStatusFrota) {
        this.filtroStatusFrota = filtroStatusFrota;
    }

    public String getFiltroUf() {
        return filtroUf;
    }

    public void setFiltroUf(String filtroUf) {
        this.filtroUf = filtroUf;
    }

    public String getFiltroCidade() {
        return filtroCidade;
    }

    public void setFiltroCidade(String filtroCidade) {
        this.filtroCidade = filtroCidade;
    }
}
