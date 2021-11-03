package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IAgenciadorFreteTaxaPostoDados;
import ipp.aci.boleia.dominio.agenciadorfrete.AgenciadorFreteTaxaPosto;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;


/**
 * Respositorio de entidades Agenciador de Frete
 */
@Repository
public class OracleAgenciadorFreteTaxaPosto extends OracleRepositorioBoleiaDados<AgenciadorFreteTaxaPosto> implements IAgenciadorFreteTaxaPostoDados {

    /**
     * Instancia o repositorio
     */
    public OracleAgenciadorFreteTaxaPosto() {
        super(AgenciadorFreteTaxaPosto.class);
    }

    @Override
    public AgenciadorFreteTaxaPosto obterPorPontoVenda(Long id) {
        if (id != null) {
            return pesquisarUnico(new ParametroPesquisaIgual("pontoVenda", id) );
        }
        return null;
    }
}