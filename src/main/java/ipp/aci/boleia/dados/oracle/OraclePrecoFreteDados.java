package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IPrecoFreteDados;
import ipp.aci.boleia.dominio.agenciadorfrete.PrecoFrete;
import org.springframework.stereotype.Repository;


/**
 * Respositorio de entidades Agenciador de Frete
 */
@Repository
public class OraclePrecoFreteDados extends OracleRepositorioBoleiaDados<PrecoFrete> implements IPrecoFreteDados {

    /**
     * Instancia o repositorio
     */
    public OraclePrecoFreteDados() {
        super(PrecoFrete.class);
    }

}