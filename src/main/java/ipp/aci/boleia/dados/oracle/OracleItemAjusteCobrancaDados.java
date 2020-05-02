package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IItemAjusteCobrancaDados;
import ipp.aci.boleia.dominio.ItemAjusteCobranca;
import org.springframework.stereotype.Repository;

/**
 * Implementação do repositório de dados da entidade {@link ItemAjusteCobranca}.
 */
@Repository
public class OracleItemAjusteCobrancaDados extends OracleRepositorioBoleiaDados<ItemAjusteCobranca> implements IItemAjusteCobrancaDados {

    /**
     * Construtor do repositório.
     */
    public OracleItemAjusteCobrancaDados() {
        super(ItemAjusteCobranca.class);
    }
}
