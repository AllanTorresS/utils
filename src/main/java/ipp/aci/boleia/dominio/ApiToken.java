package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.enums.StatusTermoAceite;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;
import org.hibernate.envers.Audited;

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
import javax.persistence.Version;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Representa a tabela de API Token
 */
@Entity
@Audited
@Table(name = "API_TOKEN")
public class ApiToken implements IPersistente, IPertenceFrota {

    private static final long serialVersionUID = -7407867528637474403L;

    @Id
    @Column(name = "CD_API_TOKEN")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_API_TOKEN")
    @SequenceGenerator(name = "SEQ_API_TOKEN", sequenceName = "SEQ_API_TOKEN", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA")
    private Frota frota;

    @Column(name = "DS_TOKEN")
    private String token;

    @Column(name = "DT_EXPIRACAO")
    private Date dataExpiracao;

    @Column(name = "ID_STATUS")
    private Integer status;

    @Column(name = "ID_CONTINGENCIA")
    private Boolean contingencia;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    @Column(name = "ID_EXPIRACAO_PROX")
    private Boolean expiracaoProxima;

    @Column(name = "ID_TERMO_ACEITE")
    private Integer statusTermoAceite;

    /**
     * Construtor
     */
    public ApiToken(){
        this.statusTermoAceite = StatusTermoAceite.SEM_PENDENCIA.getValue();
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(Date dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    @Override
    public List<Frota> getFrotas() {
        return Arrays.asList(getFrota());
    }

	/**
	 * @return the contingencia
	 */
	public Boolean getContingencia() {
		return contingencia;
	}

	/**
	 * @param contingencia the contingencia to set
	 */
	public void setContingencia(Boolean contingencia) {
		this.contingencia = contingencia;
    }
    
    public boolean isContingencia() {
        return contingencia != null && contingencia;
    }

    public Boolean getExpiracaoProxima() {
        return expiracaoProxima;
    }

    public void setExpiracaoProxima(Boolean expiracaoProxima) {
        this.expiracaoProxima = expiracaoProxima;
    }

    public Integer getStatusTermoAceite() {
        return statusTermoAceite;
    }

    public void setStatusTermoAceite(Integer statusTermoAceite) {
        this.statusTermoAceite = statusTermoAceite;
    }
}