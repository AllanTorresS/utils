package ipp.aci.boleia.dominio;


import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Representa a tabela de Termos de contrato.
 */
@Entity
@Audited
@Table(name = "TERMOS_CONTRATO_CLOB")
public class TermosContrato implements IPersistente {

	private static final long serialVersionUID = -7912031563584456933L;

	@Id
    @Column(name = "CD_TC")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TERMOS_CONTRATO_CLOB")
    @SequenceGenerator(name = "SEQ_TERMOS_CONTRATO_CLOB", sequenceName = "SEQ_TERMOS_CONTRATO_CLOB", allocationSize = 1)
    private Long id;

    @Lob
    @Column(name="DS_MINUTA", columnDefinition="CLOB NOT NULL")
    private String contrato;

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
  
    public String getContrato() {
		return contrato;
	}

	public void setContrato(String contrato) {
		this.contrato = contrato;
	}

	public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }
}
