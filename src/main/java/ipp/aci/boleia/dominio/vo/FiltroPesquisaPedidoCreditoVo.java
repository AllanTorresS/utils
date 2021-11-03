package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

import java.util.Date;

/**
 * Filtro para pesquisa de abastecimento
 */
public class FiltroPesquisaPedidoCreditoVo extends BaseFiltroPaginado {
    private EntidadeVo frota;
    private Date de;
    private Date ate;
    private EntidadeVo codigoPedido;
    private EnumVo statusSolucao;
    private EnumVo statusMundiPagg;
    private EnumVo meioPagamento;

    public EntidadeVo getFrota() {
        return frota;
    }

    public void setFrota(EntidadeVo frota) {
        this.frota = frota;
    }

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

    public EntidadeVo getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(EntidadeVo codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public EnumVo getStatusSolucao() {
        return statusSolucao;
    }

    public void setStatusSolucao(EnumVo statusSolucao) {
        this.statusSolucao = statusSolucao;
    }

    public EnumVo getStatusMundiPagg() {
        return statusMundiPagg;
    }

    public void setStatusMundiPagg(EnumVo statusMundiPagg) {
        this.statusMundiPagg = statusMundiPagg;
    }

    public EnumVo getMeioPagamento() {
        return meioPagamento;
    }

    public void setMeioPagamento(EnumVo meioPagamento) {
        this.meioPagamento = meioPagamento;
    }
}
