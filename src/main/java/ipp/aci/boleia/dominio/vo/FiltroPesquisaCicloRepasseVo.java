package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.FiltroBasePeriodoPaginado;

import java.util.List;

/**
 * Filtro para pesquisa de Ciclos de Repasse
 */
public class FiltroPesquisaCicloRepasseVo extends FiltroBasePeriodoPaginado {
    private EntidadeVo pontoDeVenda;
    private String numeroDocumento;
    private List<EnumVo> produto;
    private EnumVo statusPagamento;
    private EnumVo statusIntegracao;
    private String deMesAno;
    private String ateMesAno;

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public EntidadeVo getPontoDeVenda() {
        return pontoDeVenda;
    }

    public void setPontoDeVenda(EntidadeVo pontoDeVenda) {
        this.pontoDeVenda = pontoDeVenda;
    }

    public List<EnumVo> getProduto() {
        return produto;
    }

    public void setProduto(List<EnumVo> produto) {
        this.produto = produto;
    }

    public EnumVo getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(EnumVo statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public EnumVo getStatusIntegracao() {
        return statusIntegracao;
    }

    public void setStatusIntegracao(EnumVo statusIntegracao) {
        this.statusIntegracao = statusIntegracao;
    }

    public String getDeMesAno() {
        return deMesAno;
    }

    public void setDeMesAno(String deMesAno) {
        this.deMesAno = deMesAno;
    }

    public String getAteMesAno() {
        return ateMesAno;
    }

    public void setAteMesAno(String ateMesAno) {
        this.ateMesAno = ateMesAno;
    }
}
