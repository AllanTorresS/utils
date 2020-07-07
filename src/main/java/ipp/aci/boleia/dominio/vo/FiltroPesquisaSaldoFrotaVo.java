package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

import java.util.List;

/**
 * Filtro para pesquisa de SaldoFrota
 */
public class FiltroPesquisaSaldoFrotaVo extends BaseFiltroPaginado {
    private EntidadeVo frota;
    private EnumVo modalidadePagamento;
    private EnumVo status;
    private EnumVo uf;
    private String cidade;
    private Boolean possuiLimite;
    private List<Long> idsFrotas;

    public EntidadeVo getFrota() {
        return frota;
    }

    public void setFrota(EntidadeVo frota) {
        this.frota = frota;
    }

    public EnumVo getModalidadePagamento() {
        return modalidadePagamento;
    }

    public void setModalidadePagamento(EnumVo modalidadePagamento) {
        this.modalidadePagamento = modalidadePagamento;
    }

    public EnumVo getStatus() {
        return status;
    }

    public void setStatus(EnumVo status) {
        this.status = status;
    }

    public EnumVo getUf() {
        return uf;
    }

    public void setUf(EnumVo uf) {
        this.uf = uf;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Boolean isPossuiLimite() {
        return possuiLimite;
    }

    public void setPossuiLimite(Boolean possuiLimite) {
        this.possuiLimite = possuiLimite;
    }

    public List<Long> getIdsFrotas() {
        return idsFrotas;
    }

    public void setIdsFrotas(List<Long> idsFrotas) {
        this.idsFrotas = idsFrotas;
    }
}
