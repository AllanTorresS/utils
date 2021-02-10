package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.FiltroBasePeriodoPaginado;

import java.util.Date;
import java.util.List;

/**
 * Filtro para pesquisa do detalhe da cobrança.
 *
 * @author pedro.silva
 */
public class FiltroPesquisaDetalheCobrancaVo extends FiltroBasePeriodoPaginado {
    private Long idConsolidado;
    private Date dataAbastecimento;
    private EntidadeVo motorista;
    private String placaVeiculo;
    private List<EntidadeVo> outrosServicos;
    private Boolean notaFiscalEmitida;
    private EntidadeVo pontoVenda;
    private String numeroNf;
    private String numeroSerieNf;
    private Boolean consultaSolucao;
    private String chaveIdentificacaoAgrupamento;
    private Date dataInicioPeriodo;
    private Date dataFimPeriodo;
    private Long idFrota;

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

    public EntidadeVo getMotorista() {
        return motorista;
    }

    public void setMotorista(EntidadeVo motorista) {
        this.motorista = motorista;
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

    public Boolean getNotaFiscalEmitida() {
        return notaFiscalEmitida;
    }

    public void setNotaFiscalEmitida(Boolean notaFiscalEmitida) {
        this.notaFiscalEmitida = notaFiscalEmitida;
    }

    public EntidadeVo getPontoVenda() {
        return pontoVenda;
    }

    public void setPontoVenda(EntidadeVo pontoVenda) {
        this.pontoVenda = pontoVenda;
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

    public Boolean getConsultaSolucao() {
        return consultaSolucao;
    }

    public void setConsultaSolucao(Boolean consultaSolucao) {
        this.consultaSolucao = consultaSolucao;
    }

    public String getChaveIdentificacaoAgrupamento() {
        return chaveIdentificacaoAgrupamento;
    }

    public void setChaveIdentificacaoAgrupamento(String chaveIdentificacaoAgrupamento) {
        this.chaveIdentificacaoAgrupamento = chaveIdentificacaoAgrupamento;
    }

    public Date getDataInicioPeriodo() {
        return dataInicioPeriodo;
    }

    public void setDataInicioPeriodo(Date dataInicioPeriodo) {
        this.dataInicioPeriodo = dataInicioPeriodo;
    }

    public Date getDataFimPeriodo() {
        return dataFimPeriodo;
    }

    public void setDataFimPeriodo(Date dataFimPeriodo) {
        this.dataFimPeriodo = dataFimPeriodo;
    }

    public Long getIdFrota() {
        return idFrota;
    }

    public void setIdFrota(Long idFrota) {
        this.idFrota = idFrota;
    }
}
