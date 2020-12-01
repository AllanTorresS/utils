package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Representa a tabela de AtivacaoTag
 */
@Entity
@Audited
@Table(name = "TAG_CONECTCAR")
public class TagConectcar implements IPersistente {

	private static final long serialVersionUID = -8043835722308868542L;

	@Id
	@Column(name = "CD_TAG_CONECTCAR")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CD_FROTA")
	private Frota frota;

	@Size(max = 8)
	@Column(name = "DS_PLACA")
	private String placa;
	
	@Column(name = "DT_ATIVACAO")
	private Date dataAtivacao;
	
	@Column(name = "DT_BLOQUEIO")
	private Date dataBloqueio;
	         
	/**
	 * Construtor padrão da entidade.
	 */
	public TagConectcar() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Frota getFrota() {
		return frota;
	}

	public void setFrota(Frota frota) {
		this.frota = frota;
	}
	
	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Date getDataAtivacao() {
		return dataAtivacao;
	}

	public void setDataAtivacao(Date dataAtivacao) {
		this.dataAtivacao = dataAtivacao;
	}

	public Date getDataBloqueio() {
		return dataBloqueio;
	}

	public void setDataBloqueio(Date dataBloqueio) {
		this.dataBloqueio = dataBloqueio;
	}
	
	public boolean isAtivo(){
		if(dataAtivacao != null) {
			return true;
		}
		
		return false;
	}
	
	public boolean isBloqueado(){
		if(dataBloqueio != null) {
			return true;
		}
		
		return false;
	}
	
	public Integer getStatus() {
		//ativo
		if(isAtivo()){
			return 1;
		}
		
		//bloqueado
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TagConectcar other = (TagConectcar) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
