package ipp.aci.boleia.dominio.historico;


import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Representa o histórico de erros das operações intermediárias em lote da integração entre APCO e profrotas.
 */
@Audited
@Entity
@Table(name = "HISTORICO_ERROS_APCO")
public class HistoricoErrosOperacaoAPCO implements IPersistente {

	@Id
	@Column(name = "CD_HIST_ERRO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HISTORICO_ERROS_APCO")
	@SequenceGenerator(name = "SEQ_HISTORICO_ERROS_APCO", sequenceName = "SEQ_HISTORICO_ERROS_APCO", allocationSize = 1)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "CD_OPERACAO")
	private HistoricoIntegracaoAPCO integracaoAssociada;

	@Column(name="ID_TIPO")
	private Integer tipoOperacao;

	@NotNull
	@Column(name = "DT_INICIO_LOTE")
	private Date dataInicioLote;

	@NotNull
	@Column(name = "DT_FIM_LOTE")
	private Date dataFimLote;

	private static final Integer TAMANHO_MAXIMO_DESCRICAO_ERRO = 3000;
	@Column(name = "DS_ERRO")
	@Size(max=3000)
	private String descricaoErro;


	/**
	 * Construtor default
	 */
	public HistoricoErrosOperacaoAPCO() {
		// construtor default
	}
	/**
	 * Construtor da classe.
	 *
	 * @param id o Identificador da classe
	 * @param operacaoOriginal as informações originais da tentativa da operação de integração
	 * @param tipo o tipo de operação intermediaria
	 * @param dataInicioLote a informação do intervalo de inicio daquele subconjunto de abastecimentos/vendas
	 * @param dataFimLote a informação do intervalo de fim daquele subconjunto de abastecimentos/vendas
	 * @param descricaoErro a descrição do erro.
	 **/
	public HistoricoErrosOperacaoAPCO(Long id, HistoricoIntegracaoAPCO operacaoOriginal, Integer tipo, Date dataInicioLote, Date dataFimLote, String descricaoErro){
		this.id = id;
		this.integracaoAssociada = operacaoOriginal;
		this.tipoOperacao = tipo;
		this.dataInicioLote = dataInicioLote;
		this.dataFimLote = dataFimLote;
		this.descricaoErro = StringUtils.truncate(descricaoErro,TAMANHO_MAXIMO_DESCRICAO_ERRO);
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public HistoricoIntegracaoAPCO getIntegracaoAssociada() {
		return integracaoAssociada;
	}

	public void setIntegracaoAssociada(HistoricoIntegracaoAPCO integracaoAssociada) {
		this.integracaoAssociada = integracaoAssociada;
	}

	public Integer getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(Integer tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	public Date getDataInicioLote() {
		return dataInicioLote;
	}

	public void setDataInicioLote(Date dataInicioLote) {
		this.dataInicioLote = dataInicioLote;
	}

	public Date getDataFimLote() {
		return dataFimLote;
	}

	public void setDataFimLote(Date dataFimLote) {
		this.dataFimLote = dataFimLote;
	}

	public String getDescricaoErro() {
		return descricaoErro;
	}

	public void setDescricaoErro(String descricaoErro) {
		this.descricaoErro = StringUtils.truncate(descricaoErro,TAMANHO_MAXIMO_DESCRICAO_ERRO);
	}
}
