package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IControleAcessoPortalDados;
import ipp.aci.boleia.dominio.ControleAcesso;
import ipp.aci.boleia.dominio.enums.TipoAcesso;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static ipp.aci.boleia.util.UtilitarioLambda.obterPrimeiroObjetoDaLista;

/**
 * Implementação do repositório de dados da entidade {@link ControleAcesso}.
 *
 * @author pedro.silva
 */
@Repository
public class OracleControleAcessoPortalDados extends OracleRepositorioBoleiaDados<ControleAcesso> implements IControleAcessoPortalDados {

    /**
     * Instancia o repositorio
     */
    public OracleControleAcessoPortalDados() {
        super(ControleAcesso.class);
    }

    @Override
    public ControleAcesso obterPorLogin(String login, TipoAcesso tipoAcesso) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("login", login));
        parametros.add(new ParametroPesquisaIgual("tipoAcesso", tipoAcesso.getValue()));
        return obterPrimeiroObjetoDaLista(pesquisar((ParametroOrdenacaoColuna) null, parametros.toArray(new ParametroPesquisa[parametros.size()])));
    }
}
