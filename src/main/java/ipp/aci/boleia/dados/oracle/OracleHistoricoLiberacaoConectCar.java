package ipp.aci.boleia.dados.oracle;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import ipp.aci.boleia.dados.IHistoricoLiberacaoConectCar;
import ipp.aci.boleia.dominio.HistoricoLiberacaoConectCar;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;

/**
 * Repositório de entidades Historico Liberacao ConectCar
 */

@Repository
public class OracleHistoricoLiberacaoConectCar extends OracleRepositorioBoleiaDados<HistoricoLiberacaoConectCar> implements IHistoricoLiberacaoConectCar {

    /**
     * Instancia o repositório
     */
    public OracleHistoricoLiberacaoConectCar() {
        super(HistoricoLiberacaoConectCar.class);
    }
    
	public HistoricoLiberacaoConectCar buscarPorFrota(Long idFrota) {	
		  List<ParametroPesquisa> parametros = new ArrayList<>();
	        parametros.add(new ParametroPesquisaIgual( "frota.id", idFrota));
	        return this.pesquisarUnicoSemIsolamentoDados(parametros.toArray(new ParametroPesquisa[parametros.size()]));
	}
	
    /**
     * ESTA TABELA NÃO PODERÁ TER AÇÕES DE EXCLUSÃO POR SE TRATAR DE UM HISTÓRICO DE AÇÕES.
     */
    @Override
    public void excluir(Long... ids) {
    	//super.excluir(ids);
    }

}