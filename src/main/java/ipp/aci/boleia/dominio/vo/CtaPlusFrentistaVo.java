package ipp.aci.boleia.dominio.vo;

import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Vo para obtenção da entidade frentista enviada pelo CTA Plus
 */
public class CtaPlusFrentistaVo {

    private Long codigo;
    private String nome;
    private String id;
    private Boolean ativo;
    private String numeroRFID;
    private Boolean removido;
    private Long cpf;


    public String getNumeroRFID() {
        return numeroRFID;
    }

    @JsonSetter("numero_rfid")
    public void setNumeroRFID(String numeroRFID) {
        this.numeroRFID = numeroRFID;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean getRemovido() {
        return removido;
    }

    public void setRemovido(Boolean removido) {
        this.removido = removido;
    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
