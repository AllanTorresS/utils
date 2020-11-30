package ipp.aci.boleia.dados.oracle;

import org.springframework.stereotype.Repository;

import ipp.aci.boleia.dados.ICategoriaConectcarDados;
import ipp.aci.boleia.dominio.CategoriaConectcar;

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