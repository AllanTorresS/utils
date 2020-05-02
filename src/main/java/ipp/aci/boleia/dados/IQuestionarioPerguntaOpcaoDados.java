package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.QuestionarioPerguntaOpcao;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades de QuestionarioPerguntaOpcao
 */
public interface IQuestionarioPerguntaOpcaoDados extends IRepositorioBoleiaDados<QuestionarioPerguntaOpcao> {

    /**
     * Retorna uma lista de respostas para um ponto de venda.
     *
     * @param idPontoVenda Identificador do ponto de venda.
     * @return lista das respostas encontradas.
     */
    List<QuestionarioPerguntaOpcao> obterRespostasPorPontoVenda(Long idPontoVenda);
}
