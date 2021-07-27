package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.ICodigoValidacaoTokenJwtDados;
import ipp.aci.boleia.dominio.CodigoValidacaoTokenJwt;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

/**
 * Implementação do repositório de dados do objeto {@link CodigoValidacaoTokenJwt}.
 *
 * @author pedro.silva
 */
@Repository
public class OracleCodigoValidacaoTokenJwtDados extends OracleRepositorioBoleiaDados<CodigoValidacaoTokenJwt> implements ICodigoValidacaoTokenJwtDados {
    private static final String EXCLUIR_TOKEN_POR_USUARIO = "DELETE FROM ipp.aci.boleia.dominio.CodigoValidacaoTokenJwt AS t " +
            "WHERE t.usuario.id = :idUsuario ";
    /**
     * Construtor do repositório.
     */
    public OracleCodigoValidacaoTokenJwtDados() {
        super(CodigoValidacaoTokenJwt.class);
    }

    @Override
    public CodigoValidacaoTokenJwt obterPorUsuario(Usuario usuario) {
        return pesquisarUnico(new ParametroPesquisaIgual("usuario.id", usuario.getId()));
    }

    @Override
    public void excluirPorUsuario(Usuario usuario) {
        Query query = getGerenciadorDeEntidade().createQuery(EXCLUIR_TOKEN_POR_USUARIO);
        query.setParameter("idUsuario", usuario.getId());
        query.executeUpdate();
    }
}
