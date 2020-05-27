package ipp.aci.boleia.dominio;


import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.envers.Audited;

import ipp.aci.boleia.dominio.interfaces.IPersistente;

/**
 * Representa a tabela de Taxa do perfil.
 */
@Entity
@Audited
@Table(name = "TAXA_PERFIL")
public class TaxaPerfil implements IPersistente {

	private static final long serialVersionUID = -2285599856589422350L;

	@Id
    @Column(name = "CD_TAXA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TAXA_PERFIL")
    @SequenceGenerator(name = "SEQ_TAXA_PERFIL", sequenceName = "SEQ_TAXA_PERFIL", allocationSize = 1)
    private Long id;
	
	@NotNull
    @Size(max=40)
    @Column(name = "NM_TAXA")
    private String nome;
	
	@NotNull
	@Column(name = "ID_TIPO_TAXA")
    private Integer tipo;
	
	@NotNull
    @Column(name = "VA_MDR")
    private BigDecimal mdr;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    @Override
    public Long getId() {
        return this.id;
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

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	
	public BigDecimal getMdr() {
		return mdr;
	}

	public void setMdr(BigDecimal mdr) {
		this.mdr = mdr;
	}

	public Long getVersao() {
		return versao;
	}

	public void setVersao(Long versao) {
		this.versao = versao;
	}
    
}
