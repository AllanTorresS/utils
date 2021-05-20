package ipp.aci.boleia.dominio.vo.salesforce;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * VO com as informações necessárias à contrução da
 * integração da criação de um chamado do salesforce
 */

public class CriacaoChamadoVo {
    /**
    @JsonProperty("Account")
    private String idExterno;
    **/
    @JsonProperty("Type")
    private String tipoChamado;
    @JsonProperty("Motivo__c")
    private String motivo;
    @JsonProperty("ClassificacaoPerfil__c")
    private String sistemaDeOrigem;
    @JsonProperty("MotivoSolicitacao__c")
    private String modulo;
    @JsonProperty("Subject")
    private String assunto;
    @JsonProperty("DescricaoDoMotivo__c")
    private String descricao;

    public CriacaoChamadoVo() {
    }

    public String getTipoChamado() {
        return tipoChamado;
    }

    public void setTipoChamado(String tipoChamado) {
        this.tipoChamado = tipoChamado;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getSistemaDeOrigem() {
        return sistemaDeOrigem;
    }

    public void setSistemaDeOrigem(String sistemaDeOrigem) {
        this.sistemaDeOrigem = sistemaDeOrigem;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
