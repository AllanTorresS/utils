package ipp.aci.boleia.dominio.vo;

import java.util.Date;

/**
 * Filtro para pesquisa para busca de quantidade de transações na visão da Frota
 */
public class FiltroPesquisaQtdTransacoesFrotaVo {

    private Long idConsolidado;
    private Long idFrota;
    private Long idCobranca; 
    private Integer statusConsolidacao; 
    private Date dataInicioPeriodo; 
    private Date dataFimPeriodo;
    private Date dataLimiteEmissao; 
    private Date dataVencimento;

    public Long getIdConsolidado() {
        return idConsolidado;
    }

    public void setIdConsolidado(Long idConsolidado) {
        this.idConsolidado = idConsolidado;
    }

    public Long getIdFrota() {
        return idFrota;
    }

    public void setIdFrota(Long idFrota) {
        this.idFrota = idFrota;
    }

    public Long getIdCobranca() {
        return idCobranca;
    }

    public void setIdCobranca(Long idCobranca) {
        this.idCobranca = idCobranca;
    }

    public Integer getStatusConsolidacao() {
        return statusConsolidacao;
    }

    public void setStatusConsolidacao(Integer statusConsolidacao) {
        this.statusConsolidacao = statusConsolidacao;
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

    public Date getDataLimiteEmissao() {
        return dataLimiteEmissao;
    }

    public void setDataLimiteEmissao(Date dataLimiteEmissao) {
        this.dataLimiteEmissao = dataLimiteEmissao;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }
}
