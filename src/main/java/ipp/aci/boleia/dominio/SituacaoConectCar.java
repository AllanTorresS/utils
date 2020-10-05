package ipp.aci.boleia.dominio;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Formula;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import ipp.aci.boleia.dominio.enums.MotivoLiberacaoConectCar;
import ipp.aci.boleia.dominio.enums.StatusFrota;
import ipp.aci.boleia.dominio.interfaces.IPersistente;

/**
 * Representa a tabela de Condicoes Comerciais
 */
@Entity
@Audited
@Table(name = "V_SITUACAO_FROTA_CONECTCAR")
public class SituacaoConectCar implements IPersistente {

	private static final long serialVersionUID = 6138727610068518914L;

    @Id
    @Column(name = "CD_LIBERACAO")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CD_FROTA")
	private Frota frota;

    @NotNull
    @Column(name = "ID_STATUS")
    private Integer status;

    @NotAudited
    @Formula(StatusFrota.DECODE_FORMULA)
    private String statusConvertido;

    @NotNull
    @Column(name="DT_OPERACAO")
    private Date operacao;

    @NotNull
    @Column(name = "ID_MOTIVO")
    private Integer motivo;

    @NotAudited
    @Formula(MotivoLiberacaoConectCar.DECODE_FORMULA)
    private String motivoConvertido;

	/**
     * Construtor default
     */
    public SituacaoConectCar() {
        // construtor default
    }

    @Override
	public Long getId() {
		return id;
	}

    @Override
	public void setId(Long id) {
		this.id = id;
	}

	public Frota getFrota() {
		return frota;
	}

	public void setFrota(Frota frota) {
		this.frota = frota;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatusConvertido() {
		return statusConvertido;
	}

	public void setStatusConvertido(String statusConvertido) {
		this.statusConvertido = statusConvertido;
	}

	public Date getOperacao() {
		return operacao;
	}

	public void setOperacao(Date operacao) {
		this.operacao = operacao;
	}

	public Integer getMotivo() {
		return motivo;
	}

	public void setMotivo(Integer motivo) {
		this.motivo = motivo;
	}

	public String getMotivoConvertido() {
		return motivoConvertido;
	}

	public void setMotivoConvertido(String motivoConvertido) {
		this.motivoConvertido = motivoConvertido;
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
		SituacaoConectCar other = (SituacaoConectCar) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}