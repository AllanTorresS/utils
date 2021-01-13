package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IHistoricoEmpresaAgregadaDados;
import ipp.aci.boleia.dominio.historico.HistoricoEmpresaAgregada;
import org.springframework.stereotype.Repository;

/**
 * Implementação do repositório de dados da entidade {@link HistoricoEmpresaAgregada}.
 */
@Repository
public class OracleHistoricoEmpresaAgregadaDados extends OracleRepositorioBoleiaDados<HistoricoEmpresaAgregada> implements IHistoricoEmpresaAgregadaDados {

    /**
     * Construtor do repositório.
     */
    public OracleHistoricoEmpresaAgregadaDados() {
        super(HistoricoEmpresaAgregada.class);
    }
}
