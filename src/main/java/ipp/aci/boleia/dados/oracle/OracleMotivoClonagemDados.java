package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IMotivoClonagemDados;
import ipp.aci.boleia.dominio.MotivoClonagem;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Respositorio de entidades de
 * motivos de clonagem 
 */
@Repository
public class OracleMotivoClonagemDados extends OracleRepositorioBoleiaDados<MotivoClonagem> implements IMotivoClonagemDados {

    /**
     * Instancia o repositorio
     */
    public OracleMotivoClonagemDados() {
        super(MotivoClonagem.class);
    }

    @Override
    public List<MotivoClonagem> obterMotivosClonagemInterno() {
        return pesquisar( new ParametroOrdenacaoColuna("id"), new ParametroPesquisaIgual("validoInterno", 1));
    }
}
