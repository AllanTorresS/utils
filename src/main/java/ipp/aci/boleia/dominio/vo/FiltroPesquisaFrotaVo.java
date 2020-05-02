package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

/**
 * Filtro para pesquisa de frotas
 */
public class FiltroPesquisaFrotaVo extends BaseFiltroPaginado {

    private EntidadeVo frota;
    private String cnpj;
    private String razaoSocial;
    private String cidade;
    private EnumVo uf;
    private EnumVo status;
    private EnumVo statusContrato;
    private EnumVo statusApiToken;


    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public EnumVo getUf() {
        return uf;
    }

    public void setUf(EnumVo uf) {
        this.uf = uf;
    }

    public EnumVo getStatus() {
        return status;
    }

    public void setStatus(EnumVo status) {
        this.status = status;
    }

    public EnumVo getStatusContrato() {
        return statusContrato;
    }

    public void setStatusContrato(EnumVo statusContrato) {
        this.statusContrato = statusContrato;
    }

    public EnumVo getStatusApiToken() {
        return statusApiToken;
    }

    public void setStatusApiToken(EnumVo statusApiToken) {
        this.statusApiToken = statusApiToken;
    }

    public EntidadeVo getFrota() {
        return frota;
    }

    public void setFrota(EntidadeVo frota) {
        this.frota = frota;
    }
}
