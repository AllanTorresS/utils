package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IPedidoTagPrazoEntregaDados;
import ipp.aci.boleia.dominio.PedidoTagPrazoEntrega;
import ipp.aci.pdsiframework.dados.oracle.OracleRepositorioGenericoDados;
import org.springframework.stereotype.Repository;

/**
 * Repositório de entidades PedidoTag
 */

@Repository
public class OraclePedidoTagPrazoEntregaDados extends OracleRepositorioGenericoDados<PedidoTagPrazoEntrega, String> implements IPedidoTagPrazoEntregaDados {

    /**
     * Instancia o repositório
     */
    public OraclePedidoTagPrazoEntregaDados() {
        super(PedidoTagPrazoEntrega.class);
    }   
}