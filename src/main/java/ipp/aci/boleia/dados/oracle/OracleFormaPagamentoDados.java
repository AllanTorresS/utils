package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IFormaPagamentoDados;
import ipp.aci.boleia.dominio.FormaPagamento;
import org.springframework.stereotype.Repository;

/**
 * Respositorio de entidades de
 * Forma de Pagamento
 */
@Repository
public class OracleFormaPagamentoDados extends OracleRepositorioBoleiaDados<FormaPagamento> implements IFormaPagamentoDados {

    /**
     * Instancia o repositorio
     */
    public OracleFormaPagamentoDados() {
        super(FormaPagamento.class);
    }
}
