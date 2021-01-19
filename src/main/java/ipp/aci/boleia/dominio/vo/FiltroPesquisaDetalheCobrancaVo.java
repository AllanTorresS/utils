package ipp.aci.boleia.dominio.vo;

import java.util.Date;
import java.util.List;

/**
 * Filtro para pesquisa do detalhe da cobrança.
 *
 * @author pedro.silva
 */
public class FiltroPesquisaDetalheCobrancaVo {
    private Date de;
    private Date ate;
    private EntidadeVo motorista;
    private String placaVeiculo;
    private List<EntidadeVo> outrosServicos;
    private Boolean notaFiscalEmitida;

    public Date getDe() {
        return de;
    }

    public void setDe(Date de) {
        this.de = de;
    }

    public Date getAte() {
        return ate;
    }

    public void setAte(Date ate) {
        this.ate = ate;
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
