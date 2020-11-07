package ipp.aci.boleia.dominio.vo.exportacao;

/**
 * Classe base de mapeamento de campos do relatório de PDF.
 */
public abstract class DadosExportacaoPdfBaseVo {
    private Integer totalRegistros;

    public Integer getTotalRegistros() {
        return totalRegistros;
    }

    public void setTotalRegistros(Integer totalRegistros) {
        this.totalRegistros = totalRegistros;
    }
}
