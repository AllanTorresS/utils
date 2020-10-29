package ipp.aci.boleia.dominio.vo.exportacao;

import java.util.List;

/**
 * Classe base de mapeamento de campos do relatório de PDF.
 */
public abstract class DadosExportacaoPdfBaseVo<T> {
    private List<T> registros;
    private Integer totalRegistros;

    public List<T> getRegistros() {
        return registros;
    }

    public void setRegistros(List<T> registros) {
        this.registros = registros;
    }

    public Integer getTotalRegistros() {
        return totalRegistros;
    }

    public void setTotalRegistros(Integer totalRegistros) {
        this.totalRegistros = totalRegistros;
    }
}
