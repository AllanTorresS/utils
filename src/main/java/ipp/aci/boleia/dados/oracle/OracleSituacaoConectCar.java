package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.ISituacaoConectCar;
import ipp.aci.boleia.dominio.SituacaoConectCar;
import ipp.aci.boleia.dominio.enums.StatusFrota;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
	public SituacaoConectCar buscarPorFrota(Long idFrota) {	
		  List<ParametroPesquisa> parametros = new ArrayList<>();
	        parametros.add(new ParametroPesquisaIgual( "frota.id", idFrota));
	        return this.pesquisarUnicoSemIsolamentoDados(parametros.toArray(new ParametroPesquisa[parametros.size()]));
	}

	@Override
	public List<Long> buscarFrotasInativadas() {
        List<SituacaoConectCar> situacoes = pesquisar(new ParametroOrdenacaoColuna("frota.id"), new ParametroPesquisaIgual("status", StatusFrota.INATIVO.getValue()));
        return situacoes.stream().map(SituacaoConectCar::getIdFrota).collect(Collectors.toList());
	}

    /**
     * ESTA TABELA NÃO PODERÁ TER AÇÕES DE EXCLUSÃO POR SE TRATAR DE UM HISTÓRICO DE AÇÕES.
     */
    @Override
    public void excluir(Long... ids) {
    }

}