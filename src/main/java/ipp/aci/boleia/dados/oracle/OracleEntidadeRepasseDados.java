package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IEntidadeRepasseDados;
import ipp.aci.boleia.dominio.EntidadeRepasse;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

/**
 * Repositório de entidades EntidadeRepasse
 */
@Repository
public class OracleEntidadeRepasseDados extends OracleRepositorioBoleiaDados<EntidadeRepasse>
        implements IEntidadeRepasseDados {

    private static final Long CNPJ_IPIRANGA = 33337122000127L;

    /**
     * Construtor padrão
     */
    public OracleEntidadeRepasseDados(){super(EntidadeRepasse.class);}


    @Override
    public EntidadeRepasse obtemEntidadeDeRepassePadrao(){
        return pesquisarUnico(new ParametroPesquisaIgual("cnpj",CNPJ_IPIRANGA));
    }
}
