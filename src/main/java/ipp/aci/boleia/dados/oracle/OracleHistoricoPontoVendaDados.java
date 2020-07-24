package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IHistoricoPontoVendaDados;
import ipp.aci.boleia.dominio.HistoricoPontoVenda;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.util.Ordenacao;
import static ipp.aci.boleia.util.UtilitarioLambda.obterPrimeiroObjetoDaLista;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * Implementacao do repositorio de dados da entidade {@link HistoricoPontoVenda}.
 * 
 * @author Rodrigo Salvatore
 */
@Repository
public class OracleHistoricoPontoVendaDados extends OracleRepositorioBoleiaDados<HistoricoPontoVenda> implements IHistoricoPontoVendaDados{

    /**
     * Construtor do repositorio.
     */
    public OracleHistoricoPontoVendaDados() {
        super(HistoricoPontoVenda.class);
    }

    @Override
    public HistoricoPontoVenda obterPontoDeVendaPorData(Long cdPontoDeVenda, Date data) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("pontoDeVenda.id", cdPontoDeVenda));
        parametros.add(new ParametroPesquisaDataMenorOuIgual("dataHistorico", data));
        return obterPrimeiroObjetoDaLista(pesquisar(new ParametroOrdenacaoColuna("dataHistorico", Ordenacao.DECRESCENTE), parametros.toArray(new ParametroPesquisa[parametros.size()])));
    }    
}
