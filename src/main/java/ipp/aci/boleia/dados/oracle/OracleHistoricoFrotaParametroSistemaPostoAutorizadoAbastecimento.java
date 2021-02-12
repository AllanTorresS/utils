package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IHistoricoFrotaParametroSistemaPostoAutorizadoAbastecimento;
import ipp.aci.boleia.dominio.HistoricoFrotaParametroSistemaPostoAutorizadoAbastecimento;

/**
 * Implementação do repositório de dados da entidade {@link HistoricoFrotaParametroSistemaPostoAutorizadoAbastecimento}.
 *
 * @author felipe.chaves
 */
public class OracleHistoricoFrotaParametroSistemaPostoAutorizadoAbastecimento extends OracleRepositorioBoleiaDados<HistoricoFrotaParametroSistemaPostoAutorizadoAbastecimento> implements IHistoricoFrotaParametroSistemaPostoAutorizadoAbastecimento {

    public OracleHistoricoFrotaParametroSistemaPostoAutorizadoAbastecimento() {
        super(HistoricoFrotaParametroSistemaPostoAutorizadoAbastecimento.class);
    }
}
