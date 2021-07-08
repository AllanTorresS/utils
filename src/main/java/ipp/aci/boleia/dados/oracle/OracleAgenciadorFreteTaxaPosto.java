package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IAgenciadorFreteTaxaPostoDados;
import ipp.aci.boleia.dominio.agenciadorfrete.AgenciadorFreteTaxaPosto;
import org.springframework.stereotype.Repository;


/**
 * Respositorio de entidades Agenciador de Frete
 */
@Repository
public class OracleAgenciadorFreteTaxaPosto extends OracleRepositorioBoleiaDados<AgenciadorFreteTaxaPosto> implements IAgenciadorFreteTaxaPostoDados {

    /**
     * Instancia o repositorio
     */
    public OracleAgenciadorFreteTaxaPosto() {
        super(AgenciadorFreteTaxaPosto.class);
    }

}