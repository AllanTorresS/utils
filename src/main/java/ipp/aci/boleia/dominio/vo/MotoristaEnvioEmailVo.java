package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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


}
