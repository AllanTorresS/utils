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
    private EntidadeVo assessor;
    private EnumVo statusConectcar;
	private Boolean possuiCondicaoComercialConectcar;
    private EnumVo classificacaoStatusFrota;
    private Boolean gerenciaNf;

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

    public EntidadeVo getAssessor() {
        return assessor;
    }

    public void setAssessor(EntidadeVo assessor) {
        this.assessor = assessor;
    }

	public Boolean getPossuiCondicaoComercialConectcar() {
		return possuiCondicaoComercialConectcar;
	}

	public void setPossuiCondicaoComercialConectcar(Boolean possuiCondicaoComercialConectcar) {
		this.possuiCondicaoComercialConectcar = possuiCondicaoComercialConectcar;
	}

	public EnumVo getStatusConectcar() {
		return statusConectcar;
	}

	public void setStatusConectcar(EnumVo statusConectcar) {
		this.statusConectcar = statusConectcar;
	}

    public EnumVo getClassificacaoStatusFrota() {
        return classificacaoStatusFrota;
    }

    public void setClassificacaoStatusFrota(EnumVo classificacaoStatusFrota) {
        this.classificacaoStatusFrota = classificacaoStatusFrota;
    }

    public Boolean getGerenciaNf() {
        return gerenciaNf;
    }

    public void setGerenciaNf(Boolean gerenciaNf) {
        this.gerenciaNf = gerenciaNf;
    }
}
