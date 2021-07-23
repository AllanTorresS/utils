package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IHistoricoFrotaParametroSistemaPostoAutorizadoAbastecimentoDados;
import ipp.aci.boleia.dominio.HistoricoFrotaParametroSistemaPostoAutorizadoAbastecimento;
import org.springframework.stereotype.Repository;

/**
 * Implementação do repositório de dados da entidade {@link HistoricoFrotaParametroSistemaPostoAutorizadoAbastecimento}.
 *
 * @author felipe.chaves
 */
@Repository
public class OracleHistoricoFrotaParametroSistemaPostoAutorizadoAbastecimentoDados extends OracleRepositorioBoleiaDados<HistoricoFrotaParametroSistemaPostoAutorizadoAbastecimento> implements IHistoricoFrotaParametroSistemaPostoAutorizadoAbastecimentoDados {

    public OracleHistoricoFrotaParametroSistemaPostoAutorizadoAbastecimentoDados() {
        super(HistoricoFrotaParametroSistemaPostoAutorizadoAbastecimento.class);
    }
}
