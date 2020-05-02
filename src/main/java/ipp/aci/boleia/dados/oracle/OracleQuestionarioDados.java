package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IQuestionarioDados;
import ipp.aci.boleia.dominio.Questionario;
import ipp.aci.boleia.dominio.enums.TipoQuestionario;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Respositorio de entidades Questionario
 */
@Repository
public class OracleQuestionarioDados extends OracleRepositorioBoleiaDados<Questionario> implements IQuestionarioDados {

    /**
     * Instancia o repositorio
     */
    public OracleQuestionarioDados() {
        super(Questionario.class);
    }

    @Override
    public List<Questionario> obterPorTipo(TipoQuestionario tipoQuestionario) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("tipo",tipoQuestionario.getValue()));
        InformacaoPaginacao paginacao = new InformacaoPaginacao();
        paginacao.getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna("nome"));
        return pesquisar(paginacao, parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
    }
}