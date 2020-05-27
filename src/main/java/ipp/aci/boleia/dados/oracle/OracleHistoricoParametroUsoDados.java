package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IHistoricoParametroUsoDados;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.HistoricoParametroUso;
import ipp.aci.boleia.dominio.enums.ParametroSistema;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaOr;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static ipp.aci.boleia.util.Ordenacao.DECRESCENTE;
import static ipp.aci.boleia.util.UtilitarioLambda.obterPrimeiroObjetoDaLista;

/**
 * Implementação do repositório de dados da entidade {@link HistoricoParametroUso}.
 *
 * @author pedro.silva
 */
@Repository
public class OracleHistoricoParametroUsoDados extends OracleRepositorioBoleiaDados<HistoricoParametroUso> implements IHistoricoParametroUsoDados {

    /**
     * Construtor do repositório.
     */
    public OracleHistoricoParametroUsoDados() {
        super(HistoricoParametroUso.class);
    }

    @Override
    public HistoricoParametroUso obterUltimoHistoricoAtivacao(FrotaParametroSistema parametroUso) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("frota.id", parametroUso.getFrota().getId()));
        parametros.add(new ParametroPesquisaIgual("parametroSistema", parametroUso.getParametroSistema()));
        parametros.add(new ParametroPesquisaIgual("dataInativacao", null));
        ParametroOrdenacaoColuna parametroOrdenacao = new ParametroOrdenacaoColuna("dataAtivacao", DECRESCENTE);
        return obterPrimeiroObjetoDaLista(pesquisar(parametroOrdenacao, parametros.toArray(new ParametroPesquisa[parametros.size()])));
    }

    @Override
    public HistoricoParametroUso obterHistorico(Frota frota, ParametroSistema parametroUso, Date data) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("frota.id", frota.getId()));
        parametros.add(new ParametroPesquisaIgual("parametroSistema", parametroUso.getCodigo()));
        parametros.add(new ParametroPesquisaDataMenorOuIgual("dataAtivacao", data));
        parametros.add(new ParametroPesquisaOr(new ParametroPesquisaDataMaiorOuIgual("dataInativacao", data), new ParametroPesquisaNulo("dataInativacao")));
        return obterPrimeiroObjetoDaLista(pesquisar((ParametroOrdenacaoColuna) null, parametros.toArray(new ParametroPesquisa[parametros.size()])));
    }
}
