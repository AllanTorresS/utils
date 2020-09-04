package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

import java.util.Date;


/**
 * Filtro para pesquisa de preço micromercado
 */
public class FiltroPesquisaPrecoMicromercadoVo extends BaseFiltroPaginado {

    private EntidadeVo micromercado;
    private EntidadeVo produto;
    private Date dataAtualizacao;
    private EnumVo ufPontoDeVenda;
    private String municipioPontoDeVenda;
    private Long idPontoDeVenda;

    private Date dataAtualizacaoInicial;
    private Date dataAtualizacaoFinal;

    public EntidadeVo getMicromercado() {
        return micromercado;
    }

    public void setMicromercado(EntidadeVo micromercado) {
        this.micromercado = micromercado;
    }

    public EntidadeVo getProduto() {
        return produto;
    }

    public void setProduto(EntidadeVo produto) {
        this.produto = produto;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Date getDataAtualizacaoInicial() {
        return dataAtualizacaoInicial;
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

    public Long getIdPontoDeVenda() {
        return idPontoDeVenda;
    }

    public void setIdPontoDeVenda(Long idPontoDeVenda) {
        this.idPontoDeVenda = idPontoDeVenda;
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
}
