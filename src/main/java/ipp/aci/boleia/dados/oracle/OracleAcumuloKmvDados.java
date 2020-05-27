package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IAcumuloKmvDados;
import ipp.aci.boleia.dominio.acumulo.AcumuloKmv;
import ipp.aci.boleia.dominio.enums.StatusAcumuloKmv;
import ipp.aci.boleia.dominio.enums.StatusAutorizacao;
import ipp.aci.boleia.dominio.enums.TipoAcumuloKmv;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaMaior;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Respositorio de entidades AcumuloKmv
 */
@Repository
public class OracleAcumuloKmvDados extends OracleRepositorioBoleiaDados<AcumuloKmv> implements IAcumuloKmvDados {

	 /**
	 * Instancia o repositorio
	 */
	public OracleAcumuloKmvDados() {
		super(AcumuloKmv.class);
	}


	@Override
	public Integer recuperarKmAcumuladoMensalDonoFrota(Long cpfDono, Date dataProcessamento) {

		List<AcumuloKmv> acumulosDoMes = pesquisar((ParametroOrdenacaoColuna) null,
				new ParametroPesquisaIgual("frota.cpfDonoFrota", cpfDono),
				new ParametroPesquisaDataMaiorOuIgual("dataEnvio", UtilitarioCalculoData.obterPrimeiroDiaMes(dataProcessamento)),
				new ParametroPesquisaDataMenorOuIgual("dataEnvio", UtilitarioCalculoData.obterUltimoDiaMes(dataProcessamento)),
				new ParametroPesquisaIgual("statusAcumulo", StatusAcumuloKmv.ACUMULADO.getValue()),
				new ParametroPesquisaIgual("tipoAcumulo", TipoAcumuloKmv.DONO_FROTA.getValue())
		);
		if(acumulosDoMes.isEmpty()){
			return 0;
		} else {
			return acumulosDoMes.stream().filter(acumuloKmv -> acumuloKmv.getValorAcumulo() != null).mapToInt(AcumuloKmv::getValorAcumulo).sum();
		}

	}

	@Override
	public Integer recuperarKmAcumuladoMensalDonoFrotaIndividual(Long cpfDono, Long idFrota, Date dataProcessamento) {
		List<AcumuloKmv> acumulosDoMes = pesquisar((ParametroOrdenacaoColuna) null,
				new ParametroPesquisaIgual("frota.cpfDonoFrota", cpfDono),
				new ParametroPesquisaIgual("frota.id", idFrota),
				new ParametroPesquisaDataMaiorOuIgual("dataEnvio", UtilitarioCalculoData.obterPrimeiroDiaMes(dataProcessamento)),
				new ParametroPesquisaDataMenorOuIgual("dataEnvio", UtilitarioCalculoData.obterUltimoDiaMes(dataProcessamento)),
				new ParametroPesquisaIgual("statusAcumulo", StatusAcumuloKmv.ACUMULADO.getValue()),
				new ParametroPesquisaIgual("tipoAcumulo", TipoAcumuloKmv.DONO_FROTA.getValue())
		);
		if(acumulosDoMes.isEmpty()){
			return 0;
		} else {
			return acumulosDoMes.stream().filter(acumuloKmv -> acumuloKmv.getValorAcumulo() != null).mapToInt(AcumuloKmv::getValorAcumulo).sum();
		}
	}

	@Override
	public AcumuloKmv recuperarAcumuloPendentePorAbastecimento(Long idAbastecimento, Integer tipoAcumulo) {
		return pesquisarUnico(  new ParametroPesquisaIgual("autorizacaoPagamento.id", idAbastecimento),
				new ParametroPesquisaIgual("statusAcumulo", StatusAcumuloKmv.PENDENTE.getValue()),
				new ParametroPesquisaIgual("tipoAcumulo", tipoAcumulo));
	}

	@Override
	public AcumuloKmv recuperarAcumuloRealizadoPorAbastecimentoMotorista(Long idAbastecimento) {
		return pesquisarUnico(  new ParametroPesquisaIgual("autorizacaoPagamento.id", idAbastecimento),
								new ParametroPesquisaIgual("statusAcumulo", StatusAcumuloKmv.ACUMULADO.getValue()),
							    new ParametroPesquisaIgual("tipoAcumulo", TipoAcumuloKmv.MOTORISTA.getValue()));
	}

	@Override
	public AcumuloKmv recuperarAcumuloDonoFrotaJaCriado(Long idAbastecimento) {
		return pesquisarUnico(  new ParametroPesquisaIgual("autorizacaoPagamento.id", idAbastecimento),
				new ParametroPesquisaIgual("tipoAcumulo", TipoAcumuloKmv.DONO_FROTA.getValue()));
	}

	@Override
	public List<AcumuloKmv> obterAcumulosPendentes(Integer numeroDeRegistros) {
		List<ParametroPesquisa> parametros = new ArrayList<>();
		parametros.add(new ParametroPesquisaIgual("statusAcumulo", StatusAcumuloKmv.PENDENTE.getValue()));
		parametros.add(new ParametroPesquisaMaior("autorizacaoPagamento.valorUnitarioAbastecimento", BigDecimal.ZERO));
		parametros.add(new ParametroPesquisaMaior("autorizacaoPagamento.totalLitrosAbastecimento", BigDecimal.ZERO));
		parametros.add(new ParametroPesquisaIgual("autorizacaoPagamento.status", StatusAutorizacao.AUTORIZADO.getValue()));
		InformacaoPaginacao paginacao = new InformacaoPaginacao();
		paginacao.setPagina(1);
		paginacao.setTamanhoPagina(numeroDeRegistros);
		paginacao.getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna("dataEnvio"));
		return pesquisar(paginacao, parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
	}
}
