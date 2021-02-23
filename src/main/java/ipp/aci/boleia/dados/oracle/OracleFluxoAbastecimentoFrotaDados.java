package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IFluxoAbastecimentoFrotaDados;
import ipp.aci.boleia.dominio.FluxoAbastecimentoFrotaConfig;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Respositorio de entidades FluxoAbastecimentoFrotaConfig
 */
@Repository
public class OracleFluxoAbastecimentoFrotaDados extends OracleRepositorioBoleiaDados<FluxoAbastecimentoFrotaConfig> implements IFluxoAbastecimentoFrotaDados {

    @Autowired
    private UtilitarioAmbiente ambiente;

    /**
     * Instancia o repositorio
     */
    public OracleFluxoAbastecimentoFrotaDados() {
        super(FluxoAbastecimentoFrotaConfig.class);
    }

    @Override
    public FluxoAbastecimentoFrotaConfig obterFluxoPorFrota(Long idFrota) {
        List<FluxoAbastecimentoFrotaConfig> result = pesquisar((ParametroOrdenacaoColuna) null, new ParametroPesquisaIgual("frota.id", idFrota));
        return result.isEmpty() ? null : result.get(0);
    }
}