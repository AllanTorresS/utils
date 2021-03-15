package ipp.aci.boleia.dados.oracle;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import ipp.aci.boleia.dados.IHistoricoTagConectcarDados;
import ipp.aci.boleia.dominio.HistoricoTagConectcar;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.util.Ordenacao;

/**
 * Repositório de entidades HistoricoTagConectcar
 */
@Repository
public class OracleHistoricoTagConectcarDados extends OracleRepositorioBoleiaDados<HistoricoTagConectcar> implements IHistoricoTagConectcarDados {

    /**
     * Instancia o repositório
     */
    public OracleHistoricoTagConectcarDados() {
        super(HistoricoTagConectcar.class);
    }

    @Override
    public List<HistoricoTagConectcar> pesquisar(String placa, Long frotaId) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        
    	parametros.add(new ParametroPesquisaIgual("frota.id", frotaId));
        parametros.add(new ParametroPesquisaIgual("placa", placa));
        
        return pesquisar(new ParametroOrdenacaoColuna("dataExclusao", Ordenacao.DECRESCENTE), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

}