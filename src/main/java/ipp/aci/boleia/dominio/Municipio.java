package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Representa a tabela Municipio
 */
@Entity
@Table(name = "MUNICIPIO")
public class Municipio implements IPersistente {

    @Id
    @Column(name = "CD_MUNICIPIO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MUNICIPIO")
    @SequenceGenerator(name = "SEQ_MUNICIPIO", sequenceName = "SEQ_MUNICIPIO", allocationSize = 1)
    private Long id;

    @Column(name = "NM_MUNICIPIO")
    private String nome;

    @Column(name = "SG_UF")
    private String siglaUf;

    @Column(name = "CD_MUNICIPIO_IBGE")
    private Long codigoIbge;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSiglaUf() {
        return siglaUf;
    }

    public void setSiglaUf(String siglaUf) {
        this.siglaUf = siglaUf;
    }

    public Long getCodigoIbge() {
        return codigoIbge;
    }

    public void setCodigoIbge(Long codigoIbge) {
        this.codigoIbge = codigoIbge;
    }

    /**
     * Monta o nome de apresentacao de um Municipio,
     * @return O nome de apresentacao do Municipio em questão
     */
    @Transient
    public String getNomeApresentacao() {
        String  nomeApresentacao = this.getNome();
        String UF = this.getSiglaUf();
        nomeApresentacao += " - ";
        return nomeApresentacao + UF;
    }
}
