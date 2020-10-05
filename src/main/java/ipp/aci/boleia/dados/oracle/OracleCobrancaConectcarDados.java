package ipp.aci.boleia.dados.oracle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import ipp.aci.boleia.dados.ICobrancaConectcarDados;
import ipp.aci.boleia.dominio.CobrancaConectcar;
import ipp.aci.boleia.dominio.enums.StatusIntegracaoJde;
import ipp.aci.boleia.dominio.enums.StatusPagamentoCobranca;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaAnd;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenor;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDiferente;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaMaior;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaMenor;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaOr;
import ipp.aci.boleia.dominio.vo.EnumVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaCobrancaConectcarVo;
import ipp.aci.boleia.util.Ordenacao;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.UtilitarioFormatacaoData;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;

/**
 * Respositorio de entidades de Cobranca
 */
@Repository
public class OracleCobrancaConectcarDados extends OracleRepositorioBoleiaDados<CobrancaConectcar> implements ICobrancaConectcarDados {

	@Autowired
	private UtilitarioAmbiente ambiente;

	@Value("${frota.controle.cnpj}")
	private Long cnpjFrotaControle;

	/**
	 * Instancia o repositorio
	 */
	public OracleCobrancaConectcarDados() {
		super(CobrancaConectcar.class);
	}

	@Override
	public ResultadoPaginado<CobrancaConectcar> pesquisar(FiltroPesquisaCobrancaConectcarVo filtro) {
		List<ParametroPesquisa> parametros = criarParametrosPesquisa(filtro);
		filtro.getPaginacao().getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna("dataInicioPeriodo"));
		filtro.getPaginacao().getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna("id"));
		return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
	}

	/**
	 * Cria uma lista de parametros para a montagem da consulta de cobranças a ser exibida no grid
	 *
	 * @param filtro O filtro informado pelo usuario
	 * @return Uma lista de parametros
	 */
	private List<ParametroPesquisa> criarParametrosPesquisa(FiltroPesquisaCobrancaConectcarVo filtro) {

		List<ParametroPesquisa> parametros = new ArrayList<>();

		povoarParametroDataMaiorIgual("transacoesConsolidadas.dataInicioPeriodo", UtilitarioCalculoData.obterPrimeiroDiaMes(UtilitarioFormatacaoData.lerDataMesAno(filtro.getDe())), parametros);
		povoarParametroDataMenorIgual("transacoesConsolidadas.dataFimPeriodo", UtilitarioCalculoData.obterUltimoDiaMes(UtilitarioFormatacaoData.lerDataMesAno(filtro.getAte())), parametros);
		povoarParametrosStatusPagamento(filtro, parametros);

		if (filtro.getStatusIntegracao() != null && filtro.getStatusIntegracao().getName() != null) {
			parametros.add(new ParametroPesquisaIgual("statusIntegracaoJDE", StatusIntegracaoJde.valueOf(filtro.getStatusIntegracao().getName()).getValue()));
		}

		if (filtro.getFrota() != null && filtro.getFrota().getId() != null) {
			parametros.add(new ParametroPesquisaIgual("transacoesConsolidadas.frota.id", filtro.getFrota().getId()));
		}

		if (StringUtils.isNotBlank(filtro.getNumeroDocumento())) {
			parametros.add(new ParametroPesquisaLike("numeroDocumento", filtro.getNumeroDocumento()));
		}

		parametros.add(new ParametroPesquisaMaior("valorTotal", BigDecimal.ZERO));

        if (filtro.getIgnorarFrotaControle()) {
			parametros.add(
					new ParametroPesquisaDiferente(
							"transacoesConsolidadas.frota.cnpj", cnpjFrotaControle
					));
		}

		if (filtro.getTipo() != null) {
			parametros.add(new ParametroPesquisaIgual("tipo", filtro.getTipo()));
		}

		return parametros;
	}

	@Override
	public CobrancaConectcar obterUltimaCobranca(Long idFrota) {
		List<ParametroPesquisa> parametros = new ArrayList<>();
		parametros.add(new ParametroPesquisaIgual("transacoesConsolidadas.frota.id", idFrota));
		InformacaoPaginacao paginacao = new InformacaoPaginacao();
		paginacao.setPagina(1);
		paginacao.setTamanhoPagina(1);
		paginacao.getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna("id",Ordenacao.DECRESCENTE));
		List<CobrancaConectcar> cobrancas = pesquisar(paginacao, parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
		if(cobrancas != null && !cobrancas.isEmpty()){
			return cobrancas.get(0);
		}
		return null;
	}

	@Override
	public CobrancaConectcar obterUltimaCobrancaCiclo(Long idFrota) {
		List<ParametroPesquisa> parametros = new ArrayList<>();
		parametros.add(new ParametroPesquisaIgual("transacoesConsolidadas.frota.id", idFrota));
		parametros.add(new ParametroPesquisaIgual("transacoesConsolidadas.tipo", 2));
		InformacaoPaginacao paginacao = new InformacaoPaginacao();
		paginacao.setPagina(1);
		paginacao.setTamanhoPagina(1);
		paginacao.getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna("id",Ordenacao.DECRESCENTE));
		List<CobrancaConectcar> cobrancas = pesquisar(paginacao, parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
		if(cobrancas != null && !cobrancas.isEmpty()){
			return cobrancas.get(0);
		}
		return null;
	}

    /**
     * Povoa os parametros de consulta pertinentes ao status de pagamento da cobranca
     * @param filtro O filtro de pesquisa
     * @param parametros A lista corrente de parametros
     */
    private void povoarParametrosStatusPagamento(FiltroPesquisaCobrancaConectcarVo filtro, List<ParametroPesquisa> parametros) {
        if (CollectionUtils.isNotEmpty(filtro.getStatusPagamento())) {
        	ParametroPesquisaOr parametrosStatusOr = new ParametroPesquisaOr();

			for (EnumVo statusPagamento : filtro.getStatusPagamento()) {
				if(statusPagamento.getName() != null) {
					if (StatusPagamentoCobranca.valueOf(statusPagamento.getName()).getValue().equals(StatusPagamentoCobranca.VENCIDO.getValue())){
						parametrosStatusOr.addParametro(new ParametroPesquisaAnd(
							new ParametroPesquisaOr(new ParametroPesquisaIgual("status", StatusPagamentoCobranca.EM_ABERTO.getValue()),new ParametroPesquisaIgual("status", StatusPagamentoCobranca.VENCIDO.getValue())),
							new ParametroPesquisaDataMenor("dataVencimentoPagto", UtilitarioCalculoData.obterPrimeiroInstanteDia(ambiente.buscarDataAmbiente()))
						));
					} else {
						ParametroPesquisaAnd parametrosPesquisaAnd = new ParametroPesquisaAnd();

						parametrosPesquisaAnd.addParametro(new ParametroPesquisaIgual("status", StatusPagamentoCobranca.valueOf(statusPagamento.getName()).getValue()));

						if (StatusPagamentoCobranca.valueOf(statusPagamento.getName()).getValue().equals(StatusPagamentoCobranca.EM_ABERTO.getValue())){
							parametrosPesquisaAnd.addParametro(new ParametroPesquisaOr(
								new ParametroPesquisaDataMaiorOuIgual("dataVencimentoPagto", ambiente.buscarDataAmbiente()),
								new ParametroPesquisaNulo("dataVencimentoPagto"))
							);
						}

						parametrosStatusOr.addParametro(parametrosPesquisaAnd);
					}
				}
			}

			parametros.add(parametrosStatusOr);
        }
    }

    @Override
    public CobrancaConectcar obterCobrancaAnterior(CobrancaConectcar cobranca) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("transacoesConsolidadas.frota.id", cobranca.getFrota().getId()));
        parametros.add(new ParametroPesquisaDataMenor("dataInicioPeriodo", cobranca.getDataInicioPeriodo()));

        InformacaoPaginacao paginacao = new InformacaoPaginacao();
        paginacao.setPagina(1);
        paginacao.setTamanhoPagina(1);
        paginacao.getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna("id", Ordenacao.DECRESCENTE));

        List<CobrancaConectcar> cobrancas = pesquisar(paginacao, parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
        if (cobrancas != null && !cobrancas.isEmpty()) {
            return cobrancas.stream().findFirst().get();
        }
        return null;
    }

    @Override
    public List<CobrancaConectcar> obterCobrancasErroEnvio(Integer numeroTentativas) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaOr(
				new ParametroPesquisaIgual("statusIntegracaoJDE", StatusIntegracaoJde.ERRO_ENVIO.getValue()),
				new ParametroPesquisaNulo("statusIntegracaoJDE")
		));
        parametros.add(new ParametroPesquisaMenor("numeroTentativasEnvio", new BigDecimal(numeroTentativas)));
        return pesquisar(new ParametroOrdenacaoColuna("dataFimPeriodo", Ordenacao.CRESCENTE), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public CobrancaConectcar desanexar(CobrancaConectcar cobranca) {
    	return super.desanexar(cobranca);
    }

}