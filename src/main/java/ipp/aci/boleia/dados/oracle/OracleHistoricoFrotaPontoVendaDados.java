package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IHistoricoFrotaPontoVendaDados;
import ipp.aci.boleia.dominio.HistoricoFrotaPontoVenda;
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
 * Implementacao do repositorio de dados da entidade {@link HistoricoFrotaPontoVenda}.
 * 
 * @author Rodrigo Salvatore
 */
@Repository
public class OracleHistoricoFrotaPontoVendaDados extends OracleRepositorioBoleiaDados<HistoricoFrotaPontoVenda> implements IHistoricoFrotaPontoVendaDados {
    
    /**
     * Construtor do repositorio.
     */
    public OracleHistoricoFrotaPontoVendaDados() {
        super(HistoricoFrotaPontoVenda.class);
    }    
    
    @Override
    public HistoricoFrotaPontoVenda obterHistoricoFrotaPontoVendaPorData(Long cdFrotaPontoDeVenda, Date data) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("frotaPontoVenda.id", cdFrotaPontoDeVenda));
        parametros.add(new ParametroPesquisaDataMenorOuIgual("dataHistorico", data));
        return obterPrimeiroObjetoDaLista(pesquisar(new ParametroOrdenacaoColuna("dataHistorico", Ordenacao.DECRESCENTE), parametros.toArray(new ParametroPesquisa[parametros.size()])));
    }  
}
