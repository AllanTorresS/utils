package ipp.aci.boleia.dados.oracle;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import ipp.aci.boleia.dados.ISituacaoConectCar;
import ipp.aci.boleia.dominio.HistoricoLiberacaoConectCar;
import ipp.aci.boleia.dominio.SituacaoConectCar;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.util.Ordenacao;

/**
 * Repositório da view da Situacao ConectCar da Frota
 */

@Repository
public class OracleSituacaoConectCar extends OracleRepositorioBoleiaDados<SituacaoConectCar> implements ISituacaoConectCar {

    /**
     * Instancia o repositório
     */
    public OracleSituacaoConectCar() {
        super(SituacaoConectCar.class);
    }
    
	public SituacaoConectCar buscarPorFrota(Long idFrota) {	
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