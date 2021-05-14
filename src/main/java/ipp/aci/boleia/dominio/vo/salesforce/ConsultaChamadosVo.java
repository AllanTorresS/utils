package ipp.aci.boleia.dominio.vo.salesforce;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * VO para mapear a resposta da integração com a API de consulta de chamados do salesforce.
 */
public class ConsultaChamadosVo {
    @JsonProperty("totalSize")
    private Long totalItems;
    @JsonProperty("done")
    private Boolean sucesso;
    @JsonProperty("records")
    private List<ChamadoVo> chamados;

    public Long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Long totalItems) {
        this.totalItems = totalItems;
    }

    public Boolean getSucesso() {
        return sucesso;
    }

    public void setSucesso(Boolean sucesso) {
        this.sucesso = sucesso;
    }

    public List<ChamadoVo> getChamados() {
        return chamados;
    }

    public void setChamados(List<ChamadoVo> chamados) {
        this.chamados = chamados;
    }
}
