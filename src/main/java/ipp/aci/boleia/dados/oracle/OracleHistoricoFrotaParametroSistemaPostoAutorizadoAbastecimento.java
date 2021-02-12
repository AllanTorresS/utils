package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IHistoricoFrotaParametroSistemaPostoAutorizadoAbastecimento;
import ipp.aci.boleia.dominio.HistoricoFrotaParametroSistemaPostoAutorizadoAbastecimento;
import org.springframework.stereotype.Repository;

/**
 * Implementação do repositório de dados da entidade {@link HistoricoFrotaParametroSistemaPostoAutorizadoAbastecimento}.
 *
 * @author felipe.chaves
 */
@Repository
public class OracleHistoricoFrotaParametroSistemaPostoAutorizadoAbastecimento extends OracleRepositorioBoleiaDados<HistoricoFrotaParametroSistemaPostoAutorizadoAbastecimento> implements IHistoricoFrotaParametroSistemaPostoAutorizadoAbastecimento {

    public OracleHistoricoFrotaParametroSistemaPostoAutorizadoAbastecimento() {
        super(HistoricoFrotaParametroSistemaPostoAutorizadoAbastecimento.class);
    }
}
