package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.ICategoriaConectcarDados;
import ipp.aci.boleia.dominio.CategoriaConectcar;
import org.springframework.stereotype.Repository;

/**
 * Respositorio de entidades CategoriaConectcar
 */
@Repository
public class OracleCategoriaConectcarDados extends OracleRepositorioBoleiaDados<CategoriaConectcar> implements ICategoriaConectcarDados {

    /**
     * Instancia o repositorio
     */
    public OracleCategoriaConectcarDados() {
        super(CategoriaConectcar.class);
    }

}