package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IAgenciadorFreteDados;
import ipp.aci.boleia.dados.IParametroNotaFiscalDados;
import ipp.aci.boleia.dominio.ParametroNotaFiscal;
import ipp.aci.boleia.dominio.SistemaExterno;
import ipp.aci.boleia.dominio.agenciadorfrete.AgenciadorFrete;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;


/**
 * Respositorio de entidades ParametroNotaFiscal
 */
@Repository
public class OracleParametroNotaFiscalDados extends OracleRepositorioBoleiaDados<ParametroNotaFiscal> implements IParametroNotaFiscalDados {

    /**
     * Instancia o repositorio
     *
     */
    public OracleParametroNotaFiscalDados() {
        super(ParametroNotaFiscal.class);
    }

    @Override
    public ParametroNotaFiscal obterPorFrota(Long frotaId) {
        return pesquisarUnico(new ParametroPesquisaIgual("frota.id", frotaId));
    }
}
