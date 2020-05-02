package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IBancoDados;
import ipp.aci.boleia.dominio.Banco;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaOr;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Respositorio de entidades Banco
 */
@Repository
public class OracleBancoDados extends OracleRepositorioBoleiaDados<Banco> implements IBancoDados {

    /**
     * Instancia o repositorio
     */
    public OracleBancoDados() {
        super(Banco.class);
    }

    @Override
    public List<Banco> pesquisar(String termo) {
        ParametroPesquisa nome =  new ParametroPesquisaLike("nome",termo);
        ParametroPesquisa codigoInstituicao = new ParametroPesquisaLike("codigoInstituicao", termo);
        ParametroOrdenacaoColuna ordenacao = new ParametroOrdenacaoColuna("codigoInstituicao");
        return pesquisar(ordenacao, new ParametroPesquisaOr(nome, codigoInstituicao));
    }
}
