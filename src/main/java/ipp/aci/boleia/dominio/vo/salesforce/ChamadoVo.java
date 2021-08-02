package ipp.aci.boleia.dominio.vo.salesforce;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import ipp.aci.boleia.util.UtilitarioJson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * VO com as informações de um chamado do salesforce.
 *
 * @author pedro.silva
 */
public class ChamadoVo {
    @JsonProperty("Id")
    private String idSalesforce;
    @JsonProperty("CaseNumber")
    private String numero;
    @JsonProperty("CreatedDate")
    private String dataAbertura;
    @JsonProperty("CNPJPosto__c")
    private String cnpjPosto;
    @JsonProperty("CNPJFrota__c")
    private String cnpjFrota;
    @JsonProperty("Solicitante__c")
    private String solicitante;
    @JsonProperty("Motivo__c")
    private String motivo;
    @JsonProperty("Status")
    private String status;
    @JsonProperty("ClassificacaoPerfil__c")
    private String sistemaOrigem;
    @JsonProperty("Type")
    private String tipoChamado;
    @JsonProperty("Priority")
    private String prioridade;
    @JsonProperty("MotivoSolicitacao__c")
    private String modulo;
    @JsonProperty("Subject")
    private String assunto;
    @JsonProperty("Description")
    private String descricao;
    private List<ComentarioChamadoVo> comentarios;
    private List<AnexoChamadoVo> anexos;

    /**
     * Construtor default
     */
    public ChamadoVo() {

    }

    public String getIdSalesforce() {
        return idSalesforce;
    }

    public void setIdSalesforce(String idSalesforce) {
        this.idSalesforce = idSalesforce;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(String dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public String getCnpjPosto() {
        return cnpjPosto;
    }

    public void setCnpjPosto(String cnpjPosto) {
        this.cnpjPosto = cnpjPosto;
    }

    public String getCnpjFrota() {
        return cnpjFrota;
    }

    public void setCnpjFrota(String cnpjFrota) {
        this.cnpjFrota = cnpjFrota;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSistemaOrigem() {
        return sistemaOrigem;
    }

    public void setSistemaOrigem(String sistemaOrigem) {
        this.sistemaOrigem = sistemaOrigem;
    }

    public String getTipoChamado() {
        return tipoChamado;
    }

    public void setTipoChamado(String tipoChamado) {
        this.tipoChamado = tipoChamado;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
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

    public List<ComentarioChamadoVo> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<ComentarioChamadoVo> comentarios) {
        this.comentarios = comentarios;
    }

    public List<AnexoChamadoVo> getAnexos() {
        return anexos;
    }

    public void setAnexos(List<AnexoChamadoVo> anexos) {
        this.anexos = anexos;
    }

    /**
     * Realiza o mapeamento dos comentários do chamado.
     *
     * @param caseComments Mapa com os comentários.
     */
    @JsonProperty("CaseComments")
    private void mapearComentarios(Map<String, Object> caseComments) {
        this.comentarios = new ArrayList<>();
        if(caseComments != null) {
            List<Map<String, Object>> records = (ArrayList) caseComments.get("records");
            records.forEach(comentario -> {
                ComentarioChamadoVo comentarioVo = UtilitarioJson.toObject(UtilitarioJson.toJSON(comentario), ComentarioChamadoVo.class);
                this.comentarios.add(comentarioVo);
            });
        }
    }

    

    /**
     * Realiza o mapeamento dos anexos do chamado.
     *
     * @param contentDocumentLinks Mapa com os anexos.
     */
    @JsonProperty("ContentDocumentLinks")
    private void mapearAnexos(Map<String, Object> contentDocumentLinks) {
        this.anexos = new ArrayList<>();
        if(contentDocumentLinks != null) {
            List<Map<String, Object>> records = (ArrayList) contentDocumentLinks.get("records");
            records.forEach(anexo -> {
                AnexoChamadoVo anexoVo = UtilitarioJson.toObject(UtilitarioJson.toJSON(anexo), AnexoChamadoVo.class);
                this.anexos.add(anexoVo);
            });
        }
    }
}
