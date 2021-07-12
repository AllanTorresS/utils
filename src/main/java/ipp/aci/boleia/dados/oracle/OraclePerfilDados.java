package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IPerfilDados;
import ipp.aci.boleia.dominio.Perfil;
import ipp.aci.boleia.dominio.UsuarioPerfil;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaPerfilVo;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Respositorio de entidades Perfil
 */
@Repository
public class OraclePerfilDados extends OracleRepositorioBoleiaDados<Perfil> implements IPerfilDados {

    /**
     * Query utilizada para desvincular os perfis temporários
     * já expirados para um usuário.
     */
    private static final String DESVINCULAR_PERFIS_EXPIRADOS =
            "DELETE FROM UsuarioPerfil up " +
            "WHERE up.idUsuario = :idUsuario AND " +
            "      up.dataExpiracao IS NOT NULL AND " +
            "      up.dataExpiracao < :hoje";

    /**
     * Query utilizada para armazenar um registro na tabela UsuarioPerfil
     * já expirados para um usuário.
     */
    private static final String ARMAZENAR_USUARIO_PERFIL =
            "INSERT INTO BOLEIA_SCHEMA.USUARIO_PERFIL (CD_USUARIO, CD_PERFIL, DT_EXPIRACAO)  " +
            "VALUES (?1, ?2, ?3)";

    @Autowired
    private UtilitarioAmbiente ambiente;

    /**
     * Instancia o repositorio
     */
    public OraclePerfilDados() {
        super(Perfil.class);
    }

    @Override
    public List<Perfil> obterPerfis(FiltroPesquisaPerfilVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        ParametroOrdenacaoColuna parametro = new ParametroOrdenacaoColuna("nome");
        if(filtro.getTipoPerfil() != null) {
            parametros.add(new ParametroPesquisaIgual("tipoPerfil.id", filtro.getTipoPerfil().getId()));
        }
        if(filtro.getTemplate() != null){
            parametros.add(new ParametroPesquisaIgual("template", filtro.getTemplate()));
        }
        if(filtro.getIdFrota() != null) {
            parametros.add(new ParametroPesquisaIgual("frota.id", filtro.getIdFrota()));
        }
        if(filtro.getIdRede() != null) {
            parametros.add(new ParametroPesquisaIgual("rede.id", filtro.getIdRede()));
        }
        if(filtro.getIdUsuario() != null) {
            parametros.add(new ParametroPesquisaIgual("usuarios.id", filtro.getIdUsuario()));
        }

        return pesquisar(parametro, parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public ResultadoPaginado<Perfil> pesquisaPaginada(FiltroPesquisaPerfilVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        if(filtro.getGestor()!=null){
            parametros.add(new ParametroPesquisaIgual("gestor", filtro.getGestor()));
        }
        if(filtro.getTemplate()!=null){
            parametros.add(new ParametroPesquisaIgual("template",filtro.getTemplate()));
        }
        if(filtro.getTipoPerfil()!=null && filtro.getTipoPerfil().getId()!=null){
            parametros.add(new ParametroPesquisaIgual("tipoPerfil.id",filtro.getTipoPerfil().getId()));
        }

        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public void desvincularPerfisTemporariosExpirados(Long idUsuario) {
        Query query = getGerenciadorDeEntidade().createQuery(DESVINCULAR_PERFIS_EXPIRADOS);
        query.setParameter("idUsuario", idUsuario);
        query.setParameter("hoje", ambiente.buscarDataAmbiente());
        query.executeUpdate();
    }

    @Override
    public void armazenarUsuarioPerfil(UsuarioPerfil usuarioPerfil) {
        String queryArmazenar = ARMAZENAR_USUARIO_PERFIL;
        String[] st = {", DT_EXPIRACAO", ", ?3"};
        String[] rp = {"",""};
        if(usuarioPerfil.getDataExpiracao() == null) {
            queryArmazenar = StringUtils.replaceEach(queryArmazenar, st, rp);
        }
        Query query = getGerenciadorDeEntidade().createNativeQuery(queryArmazenar);
        query.setParameter(1, usuarioPerfil.getIdUsuario());
        query.setParameter(2, usuarioPerfil.getIdPerfil());
        if(usuarioPerfil.getDataExpiracao() != null) {
            query.setParameter(3, usuarioPerfil.getDataExpiracao());
        }
        query.executeUpdate();
    }

}