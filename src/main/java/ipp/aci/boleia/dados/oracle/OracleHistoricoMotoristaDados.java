package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IHistoricoMotoristaDados;
import ipp.aci.boleia.dominio.HistoricoMotorista;
import ipp.aci.boleia.dominio.Motorista;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.util.Ordenacao;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static ipp.aci.boleia.util.UtilitarioLambda.obterPrimeiroObjetoDaLista;

/**
 * Implementacao do repositorio de dados da entidade {@link HistoricoMotorista}.
 * 
 * @author Allan Torres
 */
@Repository
public class OracleHistoricoMotoristaDados extends OracleRepositorioBoleiaDados<HistoricoMotorista> implements IHistoricoMotoristaDados {

    /**
     * Construtor do repositorio.
     */
    public OracleHistoricoMotoristaDados() {
        super(HistoricoMotorista.class);
    }

    @Override
    public HistoricoMotorista obterHistoricoMotoristaPorData(Long cdMotorista, Date data) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("motorista.id", cdMotorista));
        parametros.add(new ParametroPesquisaDataMenorOuIgual("dataAlteracao", data));
        return obterPrimeiroObjetoDaLista(pesquisar(new ParametroOrdenacaoColuna("dataAlteracao", Ordenacao.DECRESCENTE), parametros.toArray(new ParametroPesquisa[parametros.size()])));
    }

    @Override
    public List<HistoricoMotorista> obterTodosPorMotorista(Motorista motorista) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("motorista.id", motorista.getId()));
        return pesquisar(new ParametroOrdenacaoColuna("dataAlteracao", Ordenacao.DECRESCENTE), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }
}
