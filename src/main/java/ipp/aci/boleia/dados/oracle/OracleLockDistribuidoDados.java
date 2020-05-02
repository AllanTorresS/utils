package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.ILockDistribuidolDados;
import ipp.aci.boleia.dominio.LockDistribuido;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

/**
 * Respositorio de locks distribuidos
 */
@Repository
public class OracleLockDistribuidoDados extends OracleRepositorioBoleiaDados<LockDistribuido> implements ILockDistribuidolDados {

    /**
     * Instancia o repositorio
     */
    public OracleLockDistribuidoDados() {
        super(LockDistribuido.class);
    }

    @Override
    public LockDistribuido buscarPorNome(String nome) {
        return pesquisarUnico(new ParametroPesquisaIgual("nome", nome));
    }

    @Override
    public LockDistribuido armazenar(LockDistribuido entidade) {
        if(entidade.getId()==null) {
            getGerenciadorDeEntidade().persist(entidade);
        } else {
            entidade = getGerenciadorDeEntidade().merge(entidade);
        }
        getGerenciadorDeEntidade().flush();
        return entidade;
    }
}