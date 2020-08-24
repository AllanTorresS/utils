package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.vo.RequisicaoServicoConciliacaoPosAbasteceAiVo;
import ipp.aci.boleia.dominio.vo.RespostaPosAbasteceAiVo;

import java.util.List;

/**
 * Repositório com informações de conciliacao de transacoes junto ao POS/Abastece Aí
 *
 */
public interface IConciliacaoPosAbasteceAiDados {

    /**
     * Obter status de um lote de transacoes para conciliacao
     * @param requisicaoPOSAbasteceAi O lote de transacoes para conciliacao
     * @return lista de transacoes com seus respectivos status
     */
    RespostaPosAbasteceAiVo obterStatusTransacoes(List<RequisicaoServicoConciliacaoPosAbasteceAiVo> requisicaoPOSAbasteceAi);
}
