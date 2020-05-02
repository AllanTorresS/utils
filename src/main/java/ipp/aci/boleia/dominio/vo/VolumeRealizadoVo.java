package ipp.aci.boleia.dominio.vo;

/**
 * Representa o volume realizada de um tipo de combustivel em um dado periodo
 */
public class VolumeRealizadoVo {

    private String volumeCiclo;
    private String dataInicioCiclo;
    private String dataFimCiclo;

    public String getVolumeCiclo() {
        return volumeCiclo;
    }

    public void setVolumeCiclo(String volumeCiclo) {
        this.volumeCiclo = volumeCiclo;
    }

    public String getDataInicioCiclo() {
        return dataInicioCiclo;
    }

    public void setDataInicioCiclo(String dataInicioCiclo) {
        this.dataInicioCiclo = dataInicioCiclo;
    }

    public String getDataFimCiclo() {
        return dataFimCiclo;
    }

    public void setDataFimCiclo(String dataFimCiclo) {
        this.dataFimCiclo = dataFimCiclo;
    }
}
