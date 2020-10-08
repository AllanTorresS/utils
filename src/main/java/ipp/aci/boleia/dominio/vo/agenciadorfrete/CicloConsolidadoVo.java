package ipp.aci.boleia.dominio.vo.agenciadorfrete;

import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.agenciadorfrete.AgenciadorFrete;
import ipp.aci.boleia.dominio.agenciadorfrete.Consolidado;

import java.util.Date;
import java.util.List;

/**
 * Classe que representa um ciclo de um agenciador de frete e posto
 */
public class CicloConsolidadoVo {

    private Date dataInicio;
    private Date dataFim;
    private Long agenciadorFreteId;
    private Long postoId;
    private List<Consolidado> consolidados;

    public CicloConsolidadoVo() {

    }

    public CicloConsolidadoVo(AgenciadorFrete agenciadorFrete, Date dataInicio, Date dataFim) {
        this.agenciadorFreteId = agenciadorFrete.getId();
        this.dataFim = dataFim;
        this.dataInicio = dataInicio;
    }

    public CicloConsolidadoVo(PontoDeVenda posto, Date dataInicio, Date dataFim) {
        this.postoId = posto.getId();
        this.dataFim = dataFim;
        this.dataInicio = dataInicio;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public List<Consolidado> getConsolidados() {
        return consolidados;
    }

    public void setConsolidados(List<Consolidado> consolidados) {
        this.consolidados = consolidados;
    }

    public Long getAgenciadorFreteId() {
        return agenciadorFreteId;
    }

    public void setAgenciadorFreteId(Long agenciadorFreteId) {
        this.agenciadorFreteId = agenciadorFreteId;
    }

    public Long getPostoId() {
        return postoId;
    }

    public void setPostoId(Long postoId) {
        this.postoId = postoId;
    }
}

