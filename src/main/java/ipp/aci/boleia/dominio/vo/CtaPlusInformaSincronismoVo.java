package ipp.aci.boleia.dominio.vo;

import com.fasterxml.jackson.annotation.JsonGetter;

/**
 * Vo com o conteudo do request para envio da requisição de Informa Sincronismo
 */
public class CtaPlusInformaSincronismoVo {

    private Long id;
    private String status;
    private String motivoErro;

    /**
     * Construtor padrao para serialização
     */
    public CtaPlusInformaSincronismoVo(){
    }

    /**
     * Construtor especializado.
     * @param id identificador do sinc
     * @param status status do sinc
     * @param motivoErro motivo do erro caso haja
     */
    public CtaPlusInformaSincronismoVo(Long id, String status, String motivoErro) {
        this.id = id;
        this.status = status;
        this.motivoErro = motivoErro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonGetter("motivo-erro")
    public String getMotivoErro() {
        return motivoErro;
    }

    public void setMotivoErro(String motivoErro) {
        this.motivoErro = motivoErro;
    }
}
