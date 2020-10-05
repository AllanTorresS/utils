package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IAgenciadorFreteDados;
import ipp.aci.boleia.dominio.SistemaExterno;
import ipp.aci.boleia.dominio.agenciadorfrete.AgenciadorFrete;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;


/**
 * Respositorio de entidades Agenciador de Frete
 */
@Repository
public class OracleAgenciadorFreteDados extends OracleRepositorioBoleiaDados<AgenciadorFrete> implements IAgenciadorFreteDados {

    /**
     * Instancia o repositorio
     *
     */
    public OracleAgenciadorFreteDados() {
        super(AgenciadorFrete.class);
    }

    @Override
    public AgenciadorFrete obterPorSistemaExterno(SistemaExterno sistemaExterno) {
        return pesquisarUnico(new ParametroPesquisaIgual("sistemaExterno.id", sistemaExterno.getId()));
    }
}
