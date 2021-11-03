package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IUsuarioPerfilDados;
import ipp.aci.boleia.dominio.UsuarioPerfil;
import ipp.aci.boleia.dominio.UsuarioPerfilPk;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import ipp.aci.pdsiframework.dados.oracle.OracleRepositorioGenericoDados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

/**
 * Implementação do repositório de dados do UsuarioPerfil.
 *
 * @author pedro.silva
 */
@Repository
public class OracleUsuarioPerfilDados extends OracleRepositorioGenericoDados<UsuarioPerfil, UsuarioPerfilPk> implements IUsuarioPerfilDados {

    /**
     * Query utilizada para desvincular os perfis temporários
     * já expirados para um usuário.
     */
    private static final String DESVINCULAR_PERFIS_EXPIRADOS =
            "DELETE FROM UsuarioPerfil up " +
                    "WHERE up.idUsuario = :idUsuario AND " +
                    "      up.dataExpiracao IS NOT NULL AND " +
                    "      up.dataExpiracao < :hoje";

    @Autowired
    private UtilitarioAmbiente ambiente;

    /**
     * Construtor do repositório.
     */
    public OracleUsuarioPerfilDados() {
        super(UsuarioPerfil.class);
    }

    @Override
    public UsuarioPerfil armazenar(UsuarioPerfil usuarioPerfil) {
        if (usuarioPerfil != null && usuarioPerfil.getId() != null) {
            usuarioPerfil = getGerenciadorDeEntidade().merge(usuarioPerfil);
        } else {
            getGerenciadorDeEntidade().persist(usuarioPerfil);
        }
        getGerenciadorDeEntidade().flush();
        return usuarioPerfil;
    }

    @Override
    public void desvincularPerfisTemporariosExpirados(Long idUsuario) {
        Query query = getGerenciadorDeEntidade().createQuery(DESVINCULAR_PERFIS_EXPIRADOS);
        query.setParameter("idUsuario", idUsuario);
        query.setParameter("hoje", ambiente.buscarDataAmbiente());
        query.executeUpdate();
    }
}
