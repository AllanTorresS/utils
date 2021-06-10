package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IFrotaPontoVendaDados;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.FrotaPontoVenda;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.StatusAtivacao;
import ipp.aci.boleia.dominio.enums.StatusBloqueio;
import ipp.aci.boleia.dominio.enums.StatusFrota;
import ipp.aci.boleia.dominio.enums.StatusHabilitacaoPontoVenda;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaior;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDiferente;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.vo.AutorizacaoPagamentoOrfaVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaPostoCredenciadoVo;
import ipp.aci.boleia.util.Ordenacao;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Respositorio de entidades FrotaPontoVenda
 */
@Repository
public class OracleFrotaPontoVendaDados extends OracleRepositorioBoleiaDados<FrotaPontoVenda> implements IFrotaPontoVendaDados {
	@Autowired
	private UtilitarioAmbiente ambiente;
	/**
	 * Obtém a lista de autorizações de pagamentos orfãs.
	 *
	 * Nota: Uma autorização de pagamento orfã é aquela que não está atrelada a nenhuma transação consolidada.
	 */
	private static final String QUERY_FROTAS_PV_SEM_CONSOLIDADO =
		" SELECT A.frota.id, A.pontoVenda.id, min(A.dataProcessamento) as dataProcessamento " +
		" FROM AutorizacaoPagamento A " +
		" WHERE NOT EXISTS " +
		" ( " +
		"    SELECT 1 " +
		"    FROM TransacaoConsolidada TC " +
		"    JOIN TC.frotaPtov FP " +
		"    WHERE FP.frota.id = A.frota.id " +
		"    AND FP.pontoVenda.id = A.pontoVenda.id " +
		" )" +
		" AND A.frota IS NOT NULL AND A.pontoVenda IS NOT NULL AND A.status = 1 " +
		" GROUP BY A.frota.id, A.pontoVenda.id";

	/**
	 * Instancia o repositorio
	 */
	public OracleFrotaPontoVendaDados() {
		super(FrotaPontoVenda.class);
	}

    @Override
    public ResultadoPaginado<FrotaPontoVenda> pesquisarPostosCredenciados(FiltroPesquisaPostoCredenciadoVo filtro) {
		Usuario usuarioLogado = ambiente.getUsuarioLogado();

		List<ParametroPesquisa> parametros = new ArrayList<>();
        povoarParametroIgual("frota.id", filtro.getFrota() != null ? filtro.getFrota().getId() : null, parametros);
        povoarParametroIgual("pontoVenda.id", filtro.getPontoVenda() != null ? filtro.getPontoVenda().getId() : null, parametros);
        povoarParametroLike("pontoVenda.municipio", filtro.getCidade(), parametros);
        povoarParametroIgual("pontoVenda.uf", filtro.getUf() != null ? filtro.getUf().getName() : null, parametros);
        parametros.add(new ParametroPesquisaIgual("pontoVenda.status", StatusAtivacao.ATIVO.getValue()));
        parametros.add(new ParametroPesquisaIgual("pontoVenda.statusHabilitacao", StatusHabilitacaoPontoVenda.HABILITADO.getValue()));
        if (filtro.getStatusBloqueio() != null && filtro.getStatusBloqueio().getName() != null) {
            parametros.add(new ParametroPesquisaIgual("statusBloqueio", StatusBloqueio.valueOf(filtro.getStatusBloqueio().getName()).getValue()));
        }
			return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

	@Override
	public ResultadoPaginado<FrotaPontoVenda> pesquisarPostosCredenciadosValidacaoSegregacao(FiltroPesquisaPostoCredenciadoVo filtro, Usuario usuario) {

		List<ParametroPesquisa> parametros = new ArrayList<>();
		povoarParametroIgual("frota.id", filtro.getFrota() != null ? filtro.getFrota().getId() : null, parametros);
		povoarParametroIgual("pontoVenda.id", filtro.getPontoVenda() != null ? filtro.getPontoVenda().getId() : null, parametros);
		povoarParametroLike("pontoVenda.municipio", filtro.getCidade(), parametros);
		povoarParametroIgual("pontoVenda.uf", filtro.getUf() != null ? filtro.getUf().getName() : null, parametros);
		parametros.add(new ParametroPesquisaIgual("pontoVenda.status", StatusAtivacao.ATIVO.getValue()));
		parametros.add(new ParametroPesquisaIgual("pontoVenda.statusHabilitacao", StatusHabilitacaoPontoVenda.HABILITADO.getValue()));
		if (filtro.getStatusBloqueio() != null && filtro.getStatusBloqueio().getName() != null) {
			parametros.add(new ParametroPesquisaIgual("statusBloqueio", StatusBloqueio.valueOf(filtro.getStatusBloqueio().getName()).getValue()));
		}
			return pesquisarSemIsolamentoDados(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
	}

	@Override
	public List<FrotaPontoVenda> buscarPorMicromercado(Long idMicromercado) {
		return pesquisar((ParametroOrdenacaoColuna) null, new ParametroPesquisaIgual("pontoVenda.micromercado.id", idMicromercado));
	}

	@Override
	public List<AutorizacaoPagamentoOrfaVo> obterFrotaPVSemConsolidado() {
		Query query = getGerenciadorDeEntidade().createQuery(QUERY_FROTAS_PV_SEM_CONSOLIDADO);
		List<Object[]> result = query.getResultList();
		return result.stream().map(array -> {
			AutorizacaoPagamentoOrfaVo novaAutorizacaoPagto = new AutorizacaoPagamentoOrfaVo();
			novaAutorizacaoPagto.setIdFrota((new BigDecimal(String.valueOf(array[0])).longValue()));
			novaAutorizacaoPagto.setIdPontoVenda(new BigDecimal(String.valueOf(array[1])).longValue());
			novaAutorizacaoPagto.setDataProcessamento((Date) array[2]);
			return novaAutorizacaoPagto;
		}).collect(Collectors.toList());
	}

	@Override
	public FrotaPontoVenda obterFrotaPontoVenda(Long idFrota, Long idPontoVenda){
		return pesquisarUnico(new ParametroPesquisaIgual("frota.id", idFrota), new ParametroPesquisaIgual("pontoVenda.id", idPontoVenda));
	}

	@Override
	public List<FrotaPontoVenda> buscarPorPontoVenda(Long idPontoVenda) {
		return pesquisarSemIsolamentoDados((ParametroOrdenacaoColuna) null, new ParametroPesquisaIgual("pontoVenda.id", idPontoVenda));
	}

	@Override
	public List<FrotaPontoVenda> armazenarListaFrotaPV(List<FrotaPontoVenda> listaFrotaPV)  {
		List<FrotaPontoVenda> armazenados = new ArrayList<>();
		if(listaFrotaPV != null) {
			for (FrotaPontoVenda frotaPontoVenda : listaFrotaPV) {
				FrotaPontoVenda frotaPontoVendaBase = obterFrotaPontoVenda(frotaPontoVenda.getFrota().getId(), frotaPontoVenda.getPontoVenda().getId());
				armazenados.add(frotaPontoVendaBase != null ? frotaPontoVendaBase : armazenar(frotaPontoVenda));
			}
		}
		return armazenados;
	}

	@Override
	public List<FrotaPontoVenda> buscarPorDataMaisRecente(Date dataReferencia){
		List<ParametroPesquisa> parametros = new ArrayList<>();
		parametros.add(new ParametroPesquisaDataMaior("dataAtualizacao", dataReferencia));
		parametros.add(new ParametroPesquisaDiferente("frota.status", StatusFrota.PRE_CADASTRO.getValue()));
		return pesquisar(new ParametroOrdenacaoColuna("dataAtualizacao", Ordenacao.CRESCENTE), parametros.toArray(new ParametroPesquisa[parametros.size()]));
	}

	@Override
	public List<FrotaPontoVenda> buscarPorFrota(Frota frota) {
		List<ParametroPesquisa> parametros = new ArrayList<>();
		parametros.add(new ParametroPesquisaDiferente("frota.id", frota.getId()));
		InformacaoPaginacao paginacao = new InformacaoPaginacao();
		paginacao.setPagina(1);
		paginacao.setTamanhoPagina(1);
		paginacao.getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna("dataAtualizacao", Ordenacao.CRESCENTE));
		return pesquisar(paginacao, parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
	}

    @Override
    public FrotaPontoVenda buscarPorCnpjFrotaCnpjPv(Long cnpjFrota, Long cnpjPv) {
		return pesquisarUnico(new ParametroPesquisaIgual("frota.cnpj", cnpjFrota), new ParametroPesquisaIgual("pontoVenda.cnpj", cnpjPv));
    }

}
