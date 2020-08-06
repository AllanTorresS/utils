package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IModuloInternoDados;
import ipp.aci.boleia.dominio.ModuloInterno;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Respositório de entidades {@link ModuloInterno}
 */
@Repository
public class OracleModuloInternoDados extends OracleRepositorioBoleiaDados<ModuloInterno> implements IModuloInternoDados {

    /**
     * Instancia o repositório.
     */
    public OracleModuloInternoDados() {
        super(ModuloInterno.class);
    }

    @Override
    public ModuloInterno obterPorClientESecret(String client, String secret) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("client", client));
        parametros.add(new ParametroPesquisaIgual("secret", secret));
        return pesquisarUnico(parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public ModuloInterno obterPorNome(String nome) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("nomeModulo", nome));
        return pesquisarUnico(parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }
}
