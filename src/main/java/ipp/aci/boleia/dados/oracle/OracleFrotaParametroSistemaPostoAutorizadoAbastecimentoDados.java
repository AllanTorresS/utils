package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IFrotaParametroSistemaPostoAutorizadoAbastecimentoDados;
import ipp.aci.boleia.dominio.FrotaParametroSistemaPostoAutorizadoAbastecimento;
import org.springframework.stereotype.Repository;

/**
 * Implementação oracle para {@link IFrotaParametroSistemaPostoAutorizadoAbastecimentoDados}
 */
@Repository
public class OracleFrotaParametroSistemaPostoAutorizadoAbastecimentoDados extends OracleRepositorioBoleiaDados<FrotaParametroSistemaPostoAutorizadoAbastecimento> implements IFrotaParametroSistemaPostoAutorizadoAbastecimentoDados {

    /**
     * Instancia o repositorio
     */
    public OracleFrotaParametroSistemaPostoAutorizadoAbastecimentoDados() {
        super(FrotaParametroSistemaPostoAutorizadoAbastecimento.class);
    }
}
