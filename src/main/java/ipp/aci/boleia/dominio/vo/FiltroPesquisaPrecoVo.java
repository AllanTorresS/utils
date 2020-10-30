package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

import java.util.Date;


/**
 * Filtro para pesquisa de negociacao
 */
public class FiltroPesquisaPrecoVo extends BaseFiltroPaginado {

    private Long id;
    private EntidadeVo frota;
    private EntidadeVo pontoDeVenda;
    private EntidadeVo tipoCombustivel;
    private EnumVo status;
    private EnumVo ufPontoDeVenda;
    private String municipioPontoDeVenda;
    private String nomeRede;
    private Date dataAtualizacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EntidadeVo getFrota() {
        return frota;
    }

    public void setFrota(EntidadeVo frota) {
        this.frota = frota;
    }

    public EntidadeVo getPontoDeVenda() {
        return pontoDeVenda;
    }

    public void setPontoDeVenda(EntidadeVo pontoDeVenda) {
        this.pontoDeVenda = pontoDeVenda;
    }

    public EntidadeVo getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(EntidadeVo tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }

    public EnumVo getStatus() {
        return status;
    }

    public void setStatus(EnumVo stauts) {
        this.status = stauts;
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

    public String getNomeRede() {
        return nomeRede;
    }
    public void setNomeRede(String nomeRede) {
        this.nomeRede = nomeRede;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}
