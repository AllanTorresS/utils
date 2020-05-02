package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

import java.util.Date;

/**
 * Filtro para pesquisa de abastecimento
 */
public class FiltroPesquisaExtratoPedidoCreditoVo extends BaseFiltroPaginado {
    private EntidadeVo frota;
    private Date de;
    private Date ate;
    private EntidadeVo codigoPedido;
    private EnumVo tipoOperacao;

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

    public EnumVo getTipoOperacao() {
        return tipoOperacao;
    }

    public void setTipoOperacao(EnumVo tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
    }
}
