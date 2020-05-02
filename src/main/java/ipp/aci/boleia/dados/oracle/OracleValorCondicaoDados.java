package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IValorCondicaoDados;
import ipp.aci.boleia.dominio.campanha.ValorCondicao;
import org.springframework.stereotype.Repository;

/**
 * Respositorio de entidades ValorCondicao
 */
@Repository
public class OracleValorCondicaoDados extends OracleRepositorioBoleiaDados<ValorCondicao> implements IValorCondicaoDados {

    /**
     * Instancia o repositorio
     */
    public OracleValorCondicaoDados() {
        super(ValorCondicao.class);
    }
}
