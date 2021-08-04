package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IClonagemPerfilDados;
import ipp.aci.boleia.dominio.ClonagemPerfil;
import ipp.aci.boleia.dominio.enums.TipoPerfilUsuario;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIn;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import org.springframework.stereotype.Repository;

import ipp.aci.boleia.dados.ICategoriaConectcarDados;
import ipp.aci.boleia.dominio.CategoriaConectcar;

import java.util.List;

/**
 * Respositorio de entidades CategoriaConectcar
 */
@Repository
public class OracleClonagemPerfilDados extends OracleRepositorioBoleiaDados<ClonagemPerfil> implements IClonagemPerfilDados {

    /**
     * Instancia o repositorio
     */
    public OracleClonagemPerfilDados() {
        super(ClonagemPerfil.class);
    }

    @Override
    public List<ClonagemPerfil> obterClonagensPerfisTemporariosPorUsuario(Long idUsuario) {
        return pesquisarSemIsolamentoDados((ParametroOrdenacaoColuna) null,
                new ParametroPesquisaIgual("usuarioDestino", idUsuario),
                new ParametroPesquisaNulo("dataInativacao", true));
    }

    /**
     * Faz uma pesquisa filtrando por somente perfis temporarios, removendo os definitivo da pesquisa
     * @param idUsuario
     * @return retorna dados com clonagem temporaria
     */
    @Override
    public List<ClonagemPerfil> obterClonagensSomentePerfisTemporariosPorUsuario(Long idUsuario) {
        return pesquisarSemIsolamentoDados((ParametroOrdenacaoColuna) null,
                new ParametroPesquisaIgual("usuarioDestino", idUsuario),
                new ParametroPesquisaNulo("dataInativacao", true),
                new ParametroPesquisaIgual("isDefinitiva", false));
    }

}