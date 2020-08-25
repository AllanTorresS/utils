package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IReembolsoAgenciadorFreteDados;
import ipp.aci.boleia.dominio.agenciadorfrete.AgenciadorFreteReembolso;
import org.springframework.stereotype.Repository;


/**
 * Respositorio de entidades Agenciador de Frete
 */
@Repository
public class OracleReembolsoAgenciadorFreteDados extends OracleRepositorioBoleiaDados<AgenciadorFreteReembolso> implements IReembolsoAgenciadorFreteDados {

    /**
     * Instancia o repositorio
     *
     */
    public OracleReembolsoAgenciadorFreteDados() {
        super(AgenciadorFreteReembolso.class);
    }
}
