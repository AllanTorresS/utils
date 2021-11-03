package ipp.aci.boleia.dominio.pesquisa.comum;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ipp.aci.boleia.util.Ordenacao;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Informacoes relativas a paginacao de uma pesquisa
 */
public class InformacaoPaginacao {

	private static final int TAMANHO_PAGINA_PADRAO = 25;

	private Integer pagina;
	private Integer tamanhoPagina;
	private List<ParametroOrdenacaoColuna> parametrosOrdenacaoColuna = new ArrayList<>();

	/**
	 * Construtor default, necessario para serializacao JSON
	 */
	public InformacaoPaginacao() {
		this.tamanhoPagina = TAMANHO_PAGINA_PADRAO;
		this.pagina = 1;
	}

	/**
	 * Construtor
	 *
	 * @param pagina        A pagina corrente
	 * @param tamanhoPagina O numero de registros por pagina
	 */
	public InformacaoPaginacao(Integer pagina, Integer tamanhoPagina) {
		this.pagina = pagina;
		this.tamanhoPagina = tamanhoPagina;
	}

	/**
	 * Construtor
	 *
	 * @param colunaOrdenacao A coluna de ordenacao
	 * @param ordenacao O sentido da ordenacao
	 */
	public InformacaoPaginacao(String colunaOrdenacao, Ordenacao ordenacao) {
		this((Integer) null, null);
		this.parametrosOrdenacaoColuna.add(new ParametroOrdenacaoColuna(colunaOrdenacao, ordenacao));
	}

	/**
	 * Construtor
	 *
	 * @param colunaOrdenacao A coluna de ordenacao
	 */
	public InformacaoPaginacao(ParametroOrdenacaoColuna colunaOrdenacao) {
		this(0, 0);
		this.parametrosOrdenacaoColuna.add(colunaOrdenacao);
	}

	/**
	 * Construtor
	 *
	 * @param colunasOrdenacao As colunas de ordenacao
	 */
	public InformacaoPaginacao(ParametroOrdenacaoColuna[] colunasOrdenacao) {
		this((Integer) null, null);
		this.parametrosOrdenacaoColuna = Arrays.asList(colunasOrdenacao);
	}

	/**
	 * Construtor
	 * @param pagina           A paguina corrente
	 * @param tamanhoPagina    O numero de registros da pagina
	 * @param colunasOrdenacao As colunas de ordenacao
	 */
	public InformacaoPaginacao(Integer pagina, Integer tamanhoPagina, ParametroOrdenacaoColuna... colunasOrdenacao) {
		this(pagina, tamanhoPagina);
		this.parametrosOrdenacaoColuna = Arrays.asList(colunasOrdenacao);
	}

	/**
	 * Construtor
	 *
	 * @param pagina          A pagina corrente
	 * @param tamanhoPagina   O tamanho da pagina
	 * @param colunaOrdenacao A coluna de ordenacao
	 * @param ordenacao    	  O sentido da ordenacao desejada
	 */
	public InformacaoPaginacao(Integer pagina, Integer tamanhoPagina, String colunaOrdenacao, Ordenacao ordenacao) {
		this(pagina, tamanhoPagina);
		this.parametrosOrdenacaoColuna.add(new ParametroOrdenacaoColuna(colunaOrdenacao, ordenacao));
	}

	/**
	 * Construtor
	 *
	 * @param pagina          A pagina corrente
	 * @param tamanhoPagina   O tamanho da pagina
	 * @param colunaOrdenacao A coluna de ordenacao
	 */
	public InformacaoPaginacao(Integer pagina, Integer tamanhoPagina, String colunaOrdenacao) {
		this(pagina, tamanhoPagina);
		this.parametrosOrdenacaoColuna.add(new ParametroOrdenacaoColuna(colunaOrdenacao, Ordenacao.CRESCENTE));
	}

	/**
	 * Obtem a pagina corrente
	 *
	 * @return A pagina corrente
	 */
	public Integer getPagina() {
		return pagina;
	}

	/**
	 * Atribui a pagina corrente
	 * \
	 * @param pagina A pagina corrente
	 */
	public void setPagina(Integer pagina) {
		this.pagina = pagina;
	}

	/**
	 * Obtem o tamanho da pagina
	 *
	 * @return O tamanho da pagina
	 */
	public Integer getTamanhoPagina() {
		return tamanhoPagina;
	}

	/**
	 * Atribui o tamanho da pagina
	 *
	 * @param tamanhoPagina O tamanho da pagina
	 */
	public void setTamanhoPagina(Integer tamanhoPagina) {
		this.tamanhoPagina = tamanhoPagina;
	}

	/**
	 * Obtem as colunas de ordenacao
	 *
	 * @return As colunas de ordenacao
	 */
	public List<ParametroOrdenacaoColuna> getParametrosOrdenacaoColuna() {
		return parametrosOrdenacaoColuna;
	}

	/**
	 * Atribui as colunas de ordenacao
	 *
	 * @param parametrosOrdenacaoColuna Os parametros de ordenacao
	 */
	public void setParametrosOrdenacaoColuna(List<ParametroOrdenacaoColuna> parametrosOrdenacaoColuna) {
		this.parametrosOrdenacaoColuna = parametrosOrdenacaoColuna;
	}

	/**
	 * Indica se existem parâmetros de ordenação de Coluna
	 *
	 * @return Se existem os parâmetros
	 */
	public boolean existemParametrosOrdenacaoColuna() {
		return parametrosOrdenacaoColuna != null && !parametrosOrdenacaoColuna.isEmpty();
	}

	/**
	 * Retorna o valor de offset que deverá ser utilizado na consulta paginada com base na pagina atual e tamanho da pagina.
	 *
	 * @return valor de offset.
	 */
	@Transient
	@JsonIgnore
	public Integer getOffset() {
		return (pagina * tamanhoPagina) - tamanhoPagina;
	}
}
