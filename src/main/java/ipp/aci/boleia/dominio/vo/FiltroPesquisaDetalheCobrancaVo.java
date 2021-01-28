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
}
