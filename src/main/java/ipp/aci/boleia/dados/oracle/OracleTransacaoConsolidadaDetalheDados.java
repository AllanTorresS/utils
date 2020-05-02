package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.ITransacaoConsolidadaDetalheDados;
import ipp.aci.boleia.dominio.TransacaoConsolidadaDetalhe;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaTransacaoConsolidadaDetalheVo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Respositorio de entidades AutorizacaoPagamento Consolidada Detalhe
 */
@Repository
public class OracleTransacaoConsolidadaDetalheDados extends OracleRepositorioBoleiaDados<TransacaoConsolidadaDetalhe> implements ITransacaoConsolidadaDetalheDados {

	private static final String CONSULTA_DETALHES_POR_FROTA_PV_DATA =
		" select d " +
		" from TransacaoConsolidadaDetalhe d" +
		"     join d.transacaoConsolidada t  " +
		"     join t.frotaPtov fpv  " +
		"     join fpv.frota f  " +
		"     join fpv.pontoVenda pv  " +
		" where " +
		"     d.codigoItem = :idTipoCombustivel " +
		"     and f.id = :idFrota  " +
		"     and pv.id = :idPontoVenda  " +
		"     and t.dataFimPeriodo = " +
		"     ( " +
		"     	  select MAX(tc.dataFimPeriodo) " +
		"         from TransacaoConsolidada tc " +
		"    	      join tc.frotaPtov fptov  " +
		"    	      join fptov.frota fr  " +
		"    	      join fptov.pontoVenda ptov  " +
		"         where " +
		"    	     fr.id = :idFrota  " +
		"    	     and ptov.id = :idPontoVenda  " +
		"    	     and tc.dataFimPeriodo <= :data" +
		"     )";

    /**
     * Instancia o repositorio
     */
	public OracleTransacaoConsolidadaDetalheDados() {
		super(TransacaoConsolidadaDetalhe.class);
	}

	@Override
	public ResultadoPaginado<TransacaoConsolidadaDetalhe> pesquisar(FiltroPesquisaTransacaoConsolidadaDetalheVo filtro) {
		List<ParametroPesquisa> parametros = new ArrayList<>();

		if(filtro.getIdConsolidado() != null){
            parametros.add(new ParametroPesquisaIgual("transacaoConsolidada.id",filtro.getIdConsolidado()));
        }

        filtro.setPaginacao(new InformacaoPaginacao());
		filtro.getPaginacao().getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna("nomeItem"));
		filtro.getPaginacao().getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna("valorUnitario"));

		return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
	}

	@Override
	public List<TransacaoConsolidadaDetalhe> obterDetalhesTransacaoPorFrotaPvCombustivel(Long idFrota, Long idPontoVenda, Long idTipoCombustivel, Date data) {
		return pesquisar(null, CONSULTA_DETALHES_POR_FROTA_PV_DATA,
				new ParametroPesquisaIgual("idFrota", idFrota),
				new ParametroPesquisaIgual("idPontoVenda", idPontoVenda),
				new ParametroPesquisaIgual("idTipoCombustivel", idTipoCombustivel.toString()),
				new ParametroPesquisaIgual("data", data)
		).getRegistros();
	}

}
