package ipp.aci.boleia.dominio.vo.salesforce;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * VO com as informações de um chamado do salesforce.
 *
 * @author rafael.cunha
 */
public class CriacaoChamadoVo {
    @JsonProperty("RecordTypeId")
    private String recordTypeId;
    @JsonProperty("Solicitante__c")
    private String solicitante;
    @JsonProperty("Origin")
    private String origem;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("Account" )
    private AccountChamadoVo accountFrota;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("Contact" )
    private ContactChamadoVo contactFrota;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("Frota_Associada__r" )
    private AccountChamadoVo accountPosto;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("ContatoDoPosto__r" )
    private ContactChamadoVo contactPosto;
    @JsonProperty("Subject")
    private String assunto;
    @JsonProperty("Description")
    private String descricao;
    @JsonProperty("Type")
    private String tipoChamado;
    @JsonProperty("ClassificacaoPerfil__c")
    private String sistemaOrigem;
    @JsonProperty("MotivoSolicitacao__c")
    private String modulo;
    @JsonProperty("Motivo__c")
    private String motivo;

    public CriacaoChamadoVo() {
        // serializacao JSON
    }

    public String getRecordTypeId() {
        return recordTypeId;
    }

    public void setRecordTypeId(String recordTypeId) {
        this.recordTypeId = recordTypeId;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public AccountChamadoVo getAccountPosto() {
        return accountPosto;
    }

    public void setAccountPosto(AccountChamadoVo accountPosto) {
        this.accountPosto = accountPosto;
    }

    public ContactChamadoVo getContactPosto() {
        return contactPosto;
    }

    public void setContactPosto(ContactChamadoVo contactPosto) {
        this.contactPosto = contactPosto;
    }

    public AccountChamadoVo getAccountFrota() {
        return accountFrota;
    }

    public void setAccountFrota(AccountChamadoVo accountFrota) {
        this.accountFrota = accountFrota;
    }

    public ContactChamadoVo getContactFrota() {
        return contactFrota;
    }

    public void setContactFrota(ContactChamadoVo contactFrota) {
        this.contactFrota = contactFrota;
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

    public String getTipoChamado() {
        return tipoChamado;
    }

    public void setTipoChamado(String tipoChamado) {
        this.tipoChamado = tipoChamado;
    }

    public String getSistemaOrigem() {
        return sistemaOrigem;
    }

    public void setSistemaOrigem(String sistemaOrigem) {
        this.sistemaOrigem = sistemaOrigem;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
