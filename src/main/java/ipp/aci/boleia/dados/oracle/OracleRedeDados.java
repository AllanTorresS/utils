package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IRedeDados;
import ipp.aci.boleia.dominio.Rede;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgualIgnoreCase;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


/**
 * Respositorio de entidades Rede
 */
@Repository
public class OracleRedeDados extends OracleRepositorioBoleiaDados<Rede> implements IRedeDados {

    /**
     * Instancia o repositorio
     */
    public OracleRedeDados() {
        super(Rede.class);
    }

    @Override
    public Rede buscarPorNome(String nome) {
        return pesquisarUnico(new ParametroPesquisaIgualIgnoreCase("nomeRede", nome));
    }

    @Override
    public List<Rede> pesquisar(String termo) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaLike("nomeRede",termo));
        ParametroOrdenacaoColuna ordenacao = new ParametroOrdenacaoColuna("nomeRede");
        return pesquisar(ordenacao, parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }
}
