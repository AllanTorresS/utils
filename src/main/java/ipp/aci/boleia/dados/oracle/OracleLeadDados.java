package ipp.aci.boleia.dados.oracle;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import ipp.aci.boleia.dados.ILeadDados;
import ipp.aci.boleia.dominio.Lead;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.util.Ordenacao;


/**
 * Respositorio de entidades de
 * ConectCar
 */
@Repository
public class OracleLeadDados extends OracleRepositorioBoleiaDados<Lead> implements ILeadDados {

    /**
     * Instancia o repositorio 
     */
    public OracleLeadDados() {
        super(Lead.class);
    }

	@Override
	public Lead pesquisarLeadPendentePelaFrota(Long idFrota) {
		List<ParametroPesquisa> parametros = new ArrayList<>();
		povoarParametroIgual("frota.id", idFrota, parametros);
		povoarParametroNulo("statusAprovacao", Boolean.TRUE, false, parametros);

		return pesquisarUnico(parametros.toArray(new ParametroPesquisa[parametros.size()]));
	}
	
	@Override
	public Lead pesquisarUltimoLeadParaFrota(Long idFrota) {
		List<Lead> leads = pesquisar(new ParametroOrdenacaoColuna("dataInclusao", Ordenacao.DECRESCENTE), new ParametroPesquisaIgual("frota.id", idFrota));
		if(leads != null && !leads.isEmpty()) {
			return leads.iterator().next();
		}
		return null;
	}

}