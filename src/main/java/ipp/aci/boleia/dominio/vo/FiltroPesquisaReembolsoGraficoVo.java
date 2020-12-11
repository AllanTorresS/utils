package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

import java.util.Date;

/**
 * Filtro para pesquisa de reembolsos para o gráfico da tela de Financeiro da Revenda.
 */
public class FiltroPesquisaReembolsoGraficoVo extends BaseFiltroPaginado {

    private Date dataInicioPesquisa;
    private Date dataFimPesquisa;
    private Long idFrotaSelecionada;
    private Long idPontoDeVendaSelecionado;
    private EmpresaUnidadeVo empresaUnidadeSelecionada;

    public Date getDataInicioPesquisa() {
        return dataInicioPesquisa;
    }

    public void setDataInicioPesquisa(Date dataInicioPesquisa) {
        this.dataInicioPesquisa = dataInicioPesquisa;
    }

    public Date getDataFimPesquisa() {
        return dataFimPesquisa;
    }

    public void setDataFimPesquisa(Date dataFimPesquisa) {
        this.dataFimPesquisa = dataFimPesquisa;
    }

    public Long getIdFrotaSelecionada() {
        return idFrotaSelecionada;
    }

    public void setIdFrotaSelecionada(Long idFrotaSelecionada) {
        this.idFrotaSelecionada = idFrotaSelecionada;
    }

    public Long getIdPontoDeVendaSelecionado() {
        return idPontoDeVendaSelecionado;
    }

    public void setIdPontoDeVendaSelecionado(Long idPontoDeVendaSelecionado) {
        this.idPontoDeVendaSelecionado = idPontoDeVendaSelecionado;
    }

    public EmpresaUnidadeVo getEmpresaUnidadeSelecionada() {
        return empresaUnidadeSelecionada;
    }

    public void setEmpresaUnidadeSelecionada(EmpresaUnidadeVo empresaUnidadeSelecionada) {
        this.empresaUnidadeSelecionada = empresaUnidadeSelecionada;
    }
}
