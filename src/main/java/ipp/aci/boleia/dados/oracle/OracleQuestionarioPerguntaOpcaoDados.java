package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IQuestionarioPerguntaOpcaoDados;
import ipp.aci.boleia.dominio.QuestionarioPerguntaOpcao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Respositorio de entidades QuestionarioPerguntaOpcao
 */
@Repository
public class OracleQuestionarioPerguntaOpcaoDados extends OracleRepositorioBoleiaDados<QuestionarioPerguntaOpcao> implements IQuestionarioPerguntaOpcaoDados {

    /**
     * Instancia o repositorio
     */
    public OracleQuestionarioPerguntaOpcaoDados() {
        super(QuestionarioPerguntaOpcao.class);
    }

    @Override
    public List<QuestionarioPerguntaOpcao> obterRespostasPorPontoVenda(Long idPontoVenda) {
        ParametroPesquisaIgual parametroPesquisa = new ParametroPesquisaIgual("pontosDeVenda.id", idPontoVenda);
        return pesquisar((ParametroOrdenacaoColuna) null, parametroPesquisa);
    }
}