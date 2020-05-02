package ipp.aci.boleia.dominio;


import ipp.aci.boleia.dominio.interfaces.IPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Representa a tabela de Lock Distribuido
 */
@Entity
@Table(name = "LOCK_DISTRIBUIDO")
public class LockDistribuido implements IPersistente {

    private static final long serialVersionUID = -6221183171670142610L;

    @Id
    @Column(name = "CD_LOCK")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LOCK_DISTRIBUIDO")
    @SequenceGenerator(name = "SEQ_LOCK_DISTRIBUIDO", sequenceName = "SEQ_LOCK_DISTRIBUIDO", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "DS_NOME")
    private String nome;

    @NotNull
    @Column(name = "DT_INICIO")
    private Date inicio;

    @NotNull
    @Column(name = "DT_FIM")
    private Date fim;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFim() {
        return fim;
    }

    public void setFim(Date fim) {
        this.fim = fim;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }
}
