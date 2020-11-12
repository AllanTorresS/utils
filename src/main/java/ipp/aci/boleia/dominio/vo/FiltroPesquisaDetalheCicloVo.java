package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

import java.util.Date;

/**
 * Vo  utilizado na busca o detalhe do ciclo.
 */
public class FiltroPesquisaDetalheCicloVo extends BaseFiltroPaginado {
    private Date inicio;
    private Date fim;
    private Long idPv;
    private Long idConsolidado;
    private EntidadeVo frota;
    private EnumVo statusCiclo;
    private EnumVo statusNf;

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFim() {
        return fim;
    }

    public void setFim(Date fim) {
        this.fim = fim;
    }

    public Long getIdPv() {
        return idPv;
    }

    public void setIdPv(Long idPv) {
        this.idPv = idPv;
    }

    public Long getIdConsolidado() {
        return idConsolidado;
    }

    public void setIdConsolidado(Long idConsolidado) {
        this.idConsolidado = idConsolidado;
    }

    public EntidadeVo getFrota() {
        return frota;
    }

    public void setFrota(EntidadeVo frota) {
        this.frota = frota;
    }

    public EnumVo getStatusCiclo() {
        return statusCiclo;
    }

    public void setStatusCiclo(EnumVo statusCiclo) {
        this.statusCiclo = statusCiclo;
    }

    public EnumVo getStatusNf() {
        return statusNf;
    }

    public void setStatusNf(EnumVo statusNf) {
        this.statusNf = statusNf;
    }
}
