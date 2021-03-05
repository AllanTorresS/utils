package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.FiltroBasePeriodoPaginado;

import java.util.Date;
import java.util.List;

/**
 * Filtro para pesquisa do detalhe de reembolso.
 *
 * @author pedro.silva
 */
public class FiltroPesquisaDetalheReembolsoVo extends FiltroBasePeriodoPaginado {
    private Long idConsolidado;
    private Date dataAbastecimento;
    private String placaVeiculo;
    private List<EntidadeVo> outrosServicos;
    private String numeroNf;
    private String numeroSerieNf;
    private Boolean transacaoEmitida;

    public Long getIdConsolidado() {
        return idConsolidado;
    }

    public void setIdConsolidado(Long idConsolidado) {
        this.idConsolidado = idConsolidado;
    }

    public Date getDataAbastecimento() {
        return dataAbastecimento;
    }

    public void setDataAbastecimento(Date dataAbastecimento) {
        this.dataAbastecimento = dataAbastecimento;
    }

    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    public void setPlacaVeiculo(String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }

    public List<EntidadeVo> getOutrosServicos() {
        return outrosServicos;
    }

    public void setOutrosServicos(List<EntidadeVo> outrosServicos) {
        this.outrosServicos = outrosServicos;
    }

    public String getNumeroNf() {
        return numeroNf;
    }

    public void setNumeroNf(String numeroNf) {
        this.numeroNf = numeroNf;
    }

    public String getNumeroSerieNf() {
        return numeroSerieNf;
    }

    public void setNumeroSerieNf(String numeroSerieNf) {
        this.numeroSerieNf = numeroSerieNf;
    }

    public Boolean getTransacaoEmitida() {
        return transacaoEmitida;
    }

    public void setTransacaoEmitida(Boolean transacaoEmitida) {
        this.transacaoEmitida = transacaoEmitida;
    }
}
