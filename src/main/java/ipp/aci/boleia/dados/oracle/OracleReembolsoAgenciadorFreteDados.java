package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IReembolsoAgenciadorFreteDados;
import ipp.aci.boleia.dominio.agenciadorfrete.Reembolso;
import org.springframework.stereotype.Repository;


/**
 * Respositorio de entidades Agenciador de Frete
 */
@Repository
public class OracleReembolsoAgenciadorFreteDados extends OracleRepositorioBoleiaDados<Reembolso> implements IReembolsoAgenciadorFreteDados {

    /**
     * Instancia o repositorio
     *
     */
    public OracleReembolsoAgenciadorFreteDados() {
        super(Reembolso.class);
    }
}
