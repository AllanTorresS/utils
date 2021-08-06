package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IClonagemPerfilDados;
import ipp.aci.boleia.dominio.ClonagemPerfil;
import ipp.aci.boleia.dominio.enums.TipoPerfilUsuario;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaior;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDiferente;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIn;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ipp.aci.boleia.dados.ICategoriaConectcarDados;
import ipp.aci.boleia.dominio.CategoriaConectcar;

import java.util.List;

/**
 * Respositorio de entidades CategoriaConectcar
 */
@Repository
public class OracleClonagemPerfilDados extends OracleRepositorioBoleiaDados<ClonagemPerfil> implements IClonagemPerfilDados {

    @Autowired
    private UtilitarioAmbiente ambiente;

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
                new ParametroPesquisaDiferente("dataInativacao", null),
                new ParametroPesquisaDataMaior("dataInativacao", UtilitarioCalculoData.obterPrimeiroInstanteDia(ambiente.buscarDataAmbiente())));
    }
}