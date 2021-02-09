package ipp.aci.boleia.dominio.historico;


import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Representa o histórico de integrações entre APCO e profrotas a ser verificado em tempo de execução.
 */
@Audited
@Entity
@Where(clause = "excluido = 0")
@Table(name = "HISTORICO_INTEGRACAO_APCO")
public class HistoricoIntegracaoAPCO  implements IPersistente {

	private static final long serialVersionUID = 2678513522907356519L;

	@Id
	@Column(name = "CD_OPERACAO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HISTORICO_INTEGRACAO_APCO")
	@SequenceGenerator(name = "SEQ_HISTORICO_INTEGRACAO_APCO", sequenceName = "SEQ_HISTORICO_INTEGRACAO_APCO", allocationSize = 1)
	private Long id;

	@NotNull
	@Column(name = "DT_OPERACAO")
	private Date dataOperacao;

	@NotNull
	@Column(name = "ID_STATUS")
	private Integer status;

	private static final Integer TAMANHO_MAXIMO_DESCRICAO_ERRO = 3000;

	@Column(name = "DS_ERRO")
	@Size(max=3000)
	private String descricaoErro;

	@Column(name = "ID_EXCLUIDO")
	private Boolean excluido;


	/**
	 * Construtor default
	 */
	public HistoricoIntegracaoAPCO() {
		// construtor default
	}
	 /**
	 * Construtor da classe.
	 *
	 * @param id o Identificador da classe
	 * @param dataOperacao a data da operação de integração
	 * @param status o status da operação
	 * @param descricaoErro a descrição do erro , caso ocorra falha de integração.
	 **/
	public HistoricoIntegracaoAPCO(Long id, Date dataOperacao, Integer status, String descricaoErro){
		this.id = id;
		this.dataOperacao = dataOperacao;
		this.status = status;
		this.descricaoErro = StringUtils.truncate(descricaoErro,TAMANHO_MAXIMO_DESCRICAO_ERRO);
		this.excluido = false;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataOperacao() {
		return dataOperacao;
	}

	public void setDataOperacao(Date dataOperacao) {
		this.dataOperacao = dataOperacao;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDescricaoErro() {
		return descricaoErro;
	}

	public void setDescricaoErro(String descricaoErro) {
		this.descricaoErro = StringUtils.truncate(descricaoErro,TAMANHO_MAXIMO_DESCRICAO_ERRO);
	}


}
