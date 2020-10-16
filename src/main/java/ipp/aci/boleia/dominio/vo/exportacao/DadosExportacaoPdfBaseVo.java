package ipp.aci.boleia.dominio.vo.exportacao;

import java.util.List;

public abstract class DadosExportacaoPdfBaseVo<T> {
    private List<T> registros;
    private Integer totalRegistros;

    public List<T> getRegistros() {
        return registros;
    }

    public void setRegistros(List<T> registros) {
        this.registros = registros;
        this.totalRegistros = registros.size();
    }

    public Integer getTotalRegistros() {
        return totalRegistros;
    }
}
