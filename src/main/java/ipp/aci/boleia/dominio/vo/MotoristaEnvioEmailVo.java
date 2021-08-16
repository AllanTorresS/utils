package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.Frota;

import java.util.Date;

/**
 * Representa o VO do Motorista Envio Email
 */
public class MotoristaEnvioEmailVo{

    private Long idFrota;
    private String nome;
    private Integer dddTelefoneCelular;
    private Integer telefoneCelular;
    private String email;
    private Date dataCriacao;
    private Date dataExclusao;

    /**
     * Construtor padrão da entidade.
     */
    public MotoristaEnvioEmailVo() {}

    /**
     * Construtor da classe
     *
     * @param nome Nome do motorista
     * @param frota Frota do motorista
     * @param dddTelefoneCelular Número DDD do telefone
     * @param telefoneCelular Número do telefone
     * @param dataCriacao Data de criação
     * @param dataExclusao Data de exclusão
     */
    public MotoristaEnvioEmailVo(Long id, String nome, Frota frota, Integer dddTelefoneCelular, Integer telefoneCelular, Date dataCriacao, Date dataExclusao) {
        this.nome = nome;
        this.idFrota = frota.getId();
        this.dddTelefoneCelular = dddTelefoneCelular;
        this.telefoneCelular = telefoneCelular;
        this.dataCriacao = dataCriacao;
        this.dataExclusao = dataExclusao;
    }

    public Long getIdFrota() {
        return idFrota;
    }

    public void setIdFrota(Long idFrota) {
        this.idFrota = idFrota;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getDddTelefoneCelular() {
        return dddTelefoneCelular;
    }

    public void setDddTelefoneCelular(Integer dddTelefoneCelular) {
        this.dddTelefoneCelular = dddTelefoneCelular;
    }

    public Integer getTelefoneCelular() {
        return telefoneCelular;
    }

    public void setTelefoneCelular(Integer telefoneCelular) {
        this.telefoneCelular = telefoneCelular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataExclusao() {
        return dataExclusao;
    }

    public void setDataExclusao(Date dataExclusao) {
        this.dataExclusao = dataExclusao;
    }
}
