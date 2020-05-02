package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

import java.util.Date;


/**
 * Filtro para pesquisa de alteracao de precos para exibicao ao revendedor
 */
public class FiltroPesquisaAlteracaoPrecoVo extends BaseFiltroPaginado {

    private EntidadeVo pontoVenda;
    private EntidadeVo produto;
    private EnumVo status;
    private EnumVo ufPontoDeVenda;
    private String municipioPontoDeVenda;
    private Date dataVigencia;
    private Date dataAtualizacaoInicial;
    private Date dataAtualizacaoFinal;

    public EntidadeVo getPontoVenda() {
        return pontoVenda;
    }

    public void setPontoVenda(EntidadeVo pontoVenda) {
        this.pontoVenda = pontoVenda;
    }

    public EntidadeVo getProduto() {
        return produto;
    }

    public void setProduto(EntidadeVo produto) {
        this.produto = produto;
    }

    public EnumVo getStatus() {
        return status;
    }

    public void setStatus(EnumVo status) {
        this.status = status;
    }

    public Date getDataVigencia() {
        return dataVigencia;
    }

    public void setDataVigencia(Date dataAtualizacao) {
        this.dataVigencia = dataAtualizacao;
    }

    public Date getDataAtualizacaoInicial() {
        return dataAtualizacaoInicial;
    }

    public void setDataAtualizacaoInicial(Date dataAtualizacaoInicial) {
        this.dataAtualizacaoInicial = dataAtualizacaoInicial;
    }

    public Date getDataAtualizacaoFinal() {
        return dataAtualizacaoFinal;
    }

    public void setDataAtualizacaoFinal(Date dataAtualizacaoFinal) {
        this.dataAtualizacaoFinal = dataAtualizacaoFinal;
    }

    public EnumVo getUfPontoDeVenda() {
        return ufPontoDeVenda;
    }

    public void setUfPontoDeVenda(EnumVo ufPontoDeVenda) {
        this.ufPontoDeVenda = ufPontoDeVenda;
    }

    public String getMunicipioPontoDeVenda() {
        return municipioPontoDeVenda;
    }

    public void setMunicipioPontoDeVenda(String municipioPontoDeVenda) {
        this.municipioPontoDeVenda = municipioPontoDeVenda;
    }
}
