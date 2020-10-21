package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

import java.util.Date;

/**
 * Filtro para pesquisa de Preços Frete.
 */
public class FiltroPesquisaPrecoFreteVo extends BaseFiltroPaginado {

    private EntidadeVo tipoCombustivel;
    private EnumVo status;
    private EntidadeVo pontoVenda;
    private Date dataVigencia;

    public EntidadeVo getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(EntidadeVo tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }

    public EnumVo getStatus() {
        return status;
    }

    public void setStatus(EnumVo status) {
        this.status = status;
    }

    public EntidadeVo getPontoVenda() {
        return pontoVenda;
    }

    public void setPontoVenda(EntidadeVo pontoVenda) {
        this.pontoVenda = pontoVenda;
    }

    public Date getDataVigencia() {
        return dataVigencia;
    }

    public void setDataVigencia(Date dataVigencia) {
        this.dataVigencia = dataVigencia;
    }
}
