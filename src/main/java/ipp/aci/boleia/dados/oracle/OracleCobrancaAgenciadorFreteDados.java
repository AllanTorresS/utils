package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.ICobrancaAgenciadorFreteDados;
import ipp.aci.boleia.dominio.SistemaExterno;
import ipp.aci.boleia.dominio.agenciadorfrete.AgenciadorFrete;
import ipp.aci.boleia.dominio.agenciadorfrete.Cobranca;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;


/**
 * Respositorio de entidades Agenciador de Frete
 */
@Repository
public class OracleCobrancaAgenciadorFreteDados extends OracleRepositorioBoleiaDados<Cobranca> implements ICobrancaAgenciadorFreteDados {

    /**
     * Instancia o repositorio
     *
     */
    public OracleCobrancaAgenciadorFreteDados() {
        super(Cobranca.class);
    }
}
