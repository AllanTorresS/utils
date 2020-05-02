package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IAvaliacaoAbastecimentoDados;
import ipp.aci.boleia.dominio.AvaliacaoAbastecimento;
import org.springframework.stereotype.Repository;

/**
 * Respositorio de entidades AvaliacaoAbastecimento
 */
@Repository
public class OracleAvaliacaoAbastecimentoDados extends OracleRepositorioBoleiaDados<AvaliacaoAbastecimento> implements IAvaliacaoAbastecimentoDados {

    /**
     * Instancia o repositorio
     */
    public OracleAvaliacaoAbastecimentoDados() {
        super(AvaliacaoAbastecimento.class);
    }

}