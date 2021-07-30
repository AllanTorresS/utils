package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IHistoricoFrotaPtovPrecoDados;
import ipp.aci.boleia.dominio.HistoricoFrotaPtovPreco;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenor;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.util.Ordenacao;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Respositorio de registro de histórico de preços negociados
 */
@Repository
public class OracleHistoricoFrotaPtovPrecoDados extends OracleRepositorioBoleiaDados<HistoricoFrotaPtovPreco> implements IHistoricoFrotaPtovPrecoDados {

    /**
     * Construtor do repositório.
     */
    public OracleHistoricoFrotaPtovPrecoDados(){
        super(HistoricoFrotaPtovPreco.class);
    }

    @Override
    public HistoricoFrotaPtovPreco obterPorDataFrotaPvCombustivel(Long idFrota, Long idPv, Long idCombustivel, Date dataAbastecimento) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("frotaPtov.frota.id", idFrota));
        parametros.add(new ParametroPesquisaIgual("frotaPtov.pontoVenda.id", idPv));
        parametros.add(new ParametroPesquisaIgual("tipoCombustivel.id", idCombustivel));
        parametros.add(new ParametroPesquisaDataMenor("dataVigencia", dataAbastecimento));

        ParametroOrdenacaoColuna ordenacao = new ParametroOrdenacaoColuna("dataVigencia", Ordenacao.DECRESCENTE);
        return pesquisar(ordenacao, parametros.toArray(new ParametroPesquisa[parametros.size()])).stream().findFirst().orElse(null);
    }
}
