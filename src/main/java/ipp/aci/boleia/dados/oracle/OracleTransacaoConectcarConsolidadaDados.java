package ipp.aci.boleia.dados.oracle;

import org.springframework.stereotype.Repository;

import ipp.aci.boleia.dados.ITransacaoConectcarConsolidadaDados;
import ipp.aci.boleia.dominio.TransacaoConectcarConsolidada;

/**
 * Respositorio de entidades AutorizacaoPagamento Consolidada
 */
@Repository
public class OracleTransacaoConectcarConsolidadaDados extends OracleRepositorioBoleiaDados<TransacaoConectcarConsolidada>
        implements ITransacaoConectcarConsolidadaDados {

   
    /**
     * Instancia o repositorio
     */
    public OracleTransacaoConectcarConsolidadaDados() {
        super(TransacaoConectcarConsolidada.class);
    }

    
}
