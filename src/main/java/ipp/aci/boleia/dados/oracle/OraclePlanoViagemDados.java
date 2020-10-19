package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IPlanoViagemDados;
import ipp.aci.boleia.dominio.PlanoViagem;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Repositório de Plano de Viagem
 */
@Repository
public class OraclePlanoViagemDados extends OracleRepositorioBoleiaDados<PlanoViagem> implements IPlanoViagemDados {

    /**
     * Instancia o repositorio
     */
    public OraclePlanoViagemDados() {
        super(PlanoViagem.class);
    }

    @Override
    public List<PlanoViagem> obterPorRota(Long idRota) {
        return pesquisar((ParametroOrdenacaoColuna) null, new ParametroPesquisaIgual("rota.id", idRota));
    }

}
