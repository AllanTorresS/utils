package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IReembolsoDados;
import ipp.aci.boleia.dominio.Reembolso;
import ipp.aci.boleia.dominio.enums.StatusIntegracaoJde;
import ipp.aci.boleia.dominio.enums.StatusIntegracaoReembolsoJde;
import ipp.aci.boleia.dominio.enums.StatusLiberacaoReembolsoJde;
import ipp.aci.boleia.dominio.enums.StatusPagamentoReembolso;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaAnd;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenor;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDiferente;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaEmpty;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIn;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaMaior;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaMenor;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaOr;
import ipp.aci.boleia.dominio.vo.EnumVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaReembolsoVo;
import ipp.aci.boleia.util.Ordenacao;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.UtilitarioFormatacaoData;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Respositorio de entidades de Reembolso
 */
@Repository
public class OracleReembolsoDados extends OracleRepositorioBoleiaDados<Reembolso> implements IReembolsoDados {

	@Autowired
	private UtilitarioAmbiente ambiente;

	@Value("${frota.controle.cnpj}")
	private Long cnpjFrotaControle;

	/**
	 * Consulta para buscar todos os reembolsos pendente para liberação de pagamento em JDE.
	 * Os reembolsos são posteriomente filtrados para validar regras de liberação,
	 * 	então ulitizamos o FETCH para otimizar acesso a base.
	 */
	private static final String CONSULTA_REEMBOLSOS_SUSPENSOS_PAGAMENTO =
			"SELECT r " +
			"FROM Reembolso r " +
			"JOIN FETCH r.transacoesConsolidadas tcs " +
			"WHERE r.statusLiberacaoPagamento = " + StatusLiberacaoReembolsoJde.SUSPENSO_PAGAMENTO.getValue() + " AND " +
				"r.statusIntegracao IN (" + StatusIntegracaoReembolsoJde.REALIZADO.getValue() + ", " + StatusIntegracaoReembolsoJde.AGUARDANDO_LIBERACAO.getValue() + ") AND " +
				"r.numeroDocumento IS NOT NULL";

	/**
	 * Instancia o repositorio
	 */
	public OracleReembolsoDados() {
		super(Reembolso.class);
	}

	@Override
	public ResultadoPaginado<Reembolso> pesquisar(FiltroPesquisaReembolsoVo filtro) {
		List<ParametroPesquisa> parametros = criarParametrosPesquisa(filtro);
		filtro.getPaginacao().getParametrosOrdenacaoColuna().add(
				new ParametroOrdenacaoColuna("dataPagamento", Ordenacao.DECRESCENTE));
		return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
	}

	@Override
	public List<Reembolso> pesquisarParaExportacao(FiltroPesquisaReembolsoVo filtro) {
		List<ParametroPesquisa> parametros = criarParametrosPesquisa(filtro);
		return pesquisar(
				new ParametroOrdenacaoColuna("dataVencimentoPagto", Ordenacao.DECRESCENTE),
				parametros.toArray(new ParametroPesquisa[parametros.size()]));
	}

	/**
	 * Cria uma lista de parametros para a montagem da consulta de reembolso a ser exibida no grid
	 *
	 * @param filtro O filtro informado pelo usuario
	 * @return Uma lista de parametros
	 */
	private List<ParametroPesquisa> criarParametrosPesquisa(FiltroPesquisaReembolsoVo filtro) {
		List<ParametroPesquisa> parametros = new ArrayList<>();
		povoarParametroDataPeriodo(filtro, parametros);
		if (filtro.getStatusPagamento() != null && !filtro.getStatusPagamento().isEmpty()) {
			List<ParametroPesquisa> parametrosOr = new ArrayList<>();
			for(EnumVo enumVo:filtro.getStatusPagamento()) {
				StatusPagamentoReembolso status = StatusPagamentoReembolso.valueOf(enumVo.getName());
				povoarParametroStatusPagamento(parametrosOr, status);
			}
			if(parametrosOr.size()>1) {
				parametros.add(new ParametroPesquisaOr(parametrosOr.toArray(new ParametroPesquisa[parametrosOr.size()])));
			} else {
				parametros.add(parametrosOr.get(0));
			}
		} else {
			povoarParametroStatus(parametros);
		}
		if (filtro.getStatusIntegracao() != null && !filtro.getStatusIntegracao().isEmpty()) {
			parametros.add(new ParametroPesquisaIn("statusIntegracao",filtro.getStatusIntegracao().stream().map(f-> StatusIntegracaoJde.valueOf(f.getName()).getValue()).collect(Collectors.toList())));
		}
		povoarParametroIgual("transacoesConsolidadas.frotaPtov.pontoVenda.id", filtro.getPontoDeVenda() != null ? filtro.getPontoDeVenda().getId() : null, parametros);
		povoarParametroIgual("transacoesConsolidadas.frotaPtov.frota.id", filtro.getFrota() != null ? filtro.getFrota().getId() : null, parametros);
		povoarParametroLike("numeroDocumento", filtro.getNumeroDocumento(), parametros);
		parametros.add(new ParametroPesquisaMaior("valorReembolso", BigDecimal.ZERO));

		if(ambiente.getUsuarioLogado().getFrota() == null || !ambiente.getUsuarioLogado().getFrota().getCnpj().equals(cnpjFrotaControle)){
			parametros.add(
					new ParametroPesquisaDiferente(
							"transacoesConsolidadas.frotaPtov.frota.cnpj", cnpjFrotaControle
					));
		}

		return parametros;
	}

	/**
	 * Método para povoar o parametro Status
	 * @param parametros lista de parametros a ser povoada
	 */
	private void povoarParametroStatus(List<ParametroPesquisa> parametros) {
		parametros.add(new ParametroPesquisaIn("status", Arrays.asList(
				StatusPagamentoReembolso.AGUARDANDO_NF.getValue(),
				StatusPagamentoReembolso.ATRASADO.getValue(),
				StatusPagamentoReembolso.EM_ABERTO.getValue(),
				StatusPagamentoReembolso.NF_ATRASADA.getValue())));
	}

	/**
	 * Método para povoar o parametro StatusPagamento
	 * @param parametrosOr Lista de parametros Or a ser povoada
	 * @param status O parametro StatusPagamento
	 */
	private void povoarParametroStatusPagamento(List<ParametroPesquisa> parametrosOr, StatusPagamentoReembolso status) {
		if (status.equals(StatusPagamentoReembolso.ATRASADO)) {
			parametrosOr.add(new ParametroPesquisaIgual("status", StatusPagamentoReembolso.ATRASADO.getValue()));
			parametrosOr.add(new ParametroPesquisaAnd(
					new ParametroPesquisaDataMenor("dataVencimentoPagto", ambiente.buscarDataAmbiente()),
					new ParametroPesquisaIgual("status", StatusPagamentoReembolso.EM_ABERTO.getValue())));
		} else if(status.equals(StatusPagamentoReembolso.EM_ABERTO)) {
			parametrosOr.add(new ParametroPesquisaAnd(
					new ParametroPesquisaDataMaiorOuIgual("dataVencimentoPagto", ambiente.buscarDataAmbiente()),
					new ParametroPesquisaIgual("status", StatusPagamentoReembolso.EM_ABERTO.getValue())));
		} else {
			parametrosOr.add(new ParametroPesquisaIgual("status",status.getValue()));
		}
	}

	/**
	 * Método para povoar o parametro de Período (inicio e fim)
	 * @param filtro filtro aplicado
	 * @param parametros lista de parametros a ser povoada
	 */
	private void povoarParametroDataPeriodo(FiltroPesquisaReembolsoVo filtro, List<ParametroPesquisa> parametros) {
		if (filtro.getDe() != null) {
			Date dataInicioPeriodo = UtilitarioCalculoData.obterPrimeiroDiaMes(UtilitarioFormatacaoData.lerDataMesAno(filtro.getDe()));
			parametros.add(new ParametroPesquisaDataMaiorOuIgual("transacoesConsolidadas.dataInicioPeriodo", dataInicioPeriodo));
		}
		if (filtro.getAte() != null) {
			Date dataFimPeriodo = UtilitarioCalculoData.obterUltimoDiaMes(UtilitarioFormatacaoData.lerDataMesAno(filtro.getAte()));
			parametros.add(new ParametroPesquisaDataMenorOuIgual("transacoesConsolidadas.dataFimPeriodo", UtilitarioCalculoData.obterUltimoDiaMes(dataFimPeriodo)));
		}
	}

	@Override
	public List<Reembolso> buscarReembolsosParaConsultarAvisoCredito() {
		return pesquisar(new ParametroOrdenacaoColuna("dataVencimentoPagto", Ordenacao.DECRESCENTE),
				new ParametroPesquisaNulo("numeroDocumento", true),
				new ParametroPesquisaDiferente("status", StatusPagamentoReembolso.PAGO.getValue()),
				new ParametroPesquisaEmpty("transacoesConsolidadas", true));
	}

	@Override
	public Reembolso obterUltimoReembolso(Long idPtov) {
		List<ParametroPesquisa> parametros = new ArrayList<>();
		parametros.add(new ParametroPesquisaIgual("transacoesConsolidadas.frotaPtov.pontoVenda.id", idPtov));
		InformacaoPaginacao paginacao = new InformacaoPaginacao();
		paginacao.setPagina(1);
		paginacao.setTamanhoPagina(1);
		paginacao.getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna("id", Ordenacao.DECRESCENTE));
		List<Reembolso> reembolsos = pesquisar(paginacao, parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
		if(reembolsos != null && !reembolsos.isEmpty()){
			return reembolsos.get(0);
		}
		return null;
	}

	@Override
	public List<Reembolso> obterReembolsosSuspensosParaPagamento() {
		return pesquisarSemIsolamentoDados(null, CONSULTA_REEMBOLSOS_SUSPENSOS_PAGAMENTO).getRegistros();
	}

	@Override
	public List<Reembolso> obterReembolsosErroEnvio(Integer numeroTentativas) {
		List<ParametroPesquisa> parametros = new ArrayList<>();
		parametros.add(new ParametroPesquisaIgual("statusIntegracao", StatusIntegracaoReembolsoJde.ERRO_ENVIO.getValue()));
		parametros.add(new ParametroPesquisaMenor("numeroTentativasEnvio", new BigDecimal(numeroTentativas)));
		return pesquisar(new ParametroOrdenacaoColuna("dataVencimentoPagto", Ordenacao.CRESCENTE), parametros.toArray(new ParametroPesquisa[parametros.size()]));
	}
}
