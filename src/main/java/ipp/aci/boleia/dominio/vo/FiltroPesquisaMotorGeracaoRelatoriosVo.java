package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

/**
 * Filtro de Pesquisa para Motor de relatório
 */

public class FiltroPesquisaMotorGeracaoRelatoriosVo extends BaseFiltroPaginado {

    private EnumVo tipoRelatorioMotorGerador;
    private EnumVo statusMotorGeracaoRelatorio;

    public void setTipoRelatorioMotorGerador(EnumVo tipoRelatorioMotorGerador) { this.tipoRelatorioMotorGerador = tipoRelatorioMotorGerador; }
    public EnumVo getTipoRelatorioMotorGerador() { return tipoRelatorioMotorGerador; }

    public EnumVo getStatusMotorGeracaoRelatorio() { return statusMotorGeracaoRelatorio; }
    public void setStatusMotorGeracaoRelatorio(EnumVo statusMotorGeracaoRelatorio) { this.statusMotorGeracaoRelatorio = statusMotorGeracaoRelatorio; }

}
