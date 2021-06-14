package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IConfiguracaoDistribuicaoAutomaticaDados;
import ipp.aci.boleia.dominio.ConfiguracaoDistribuicaoAutomatica;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;

import org.springframework.stereotype.Repository;

/**
 * Repositório de entidades ConfiguracaoDistribuicaoAutomatica
 */
@Repository
public class OracleConfiguracaoDistribuicaoAutomaticaDados extends OracleRepositorioBoleiaDados<ConfiguracaoDistribuicaoAutomatica> implements IConfiguracaoDistribuicaoAutomaticaDados {

    /**
     * Instancia o repositorio
     */
    public OracleConfiguracaoDistribuicaoAutomaticaDados() {
        super(ConfiguracaoDistribuicaoAutomatica.class);
    }

    @Override
    public ConfiguracaoDistribuicaoAutomatica obterConfiguracaoPorFrota(Long idFrota) {
        return pesquisarUnico(new ParametroPesquisaIgual("frota", idFrota));
    }
}
