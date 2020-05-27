package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.vo.IncluirFaturaRequisicaoVo;
import ipp.aci.boleia.dominio.vo.IncluirFaturaRespostaVo;

/**
 * Contrato de envio de mensagens às filas de integração de cobranças
 */
public interface IFilaCobrancaIncluirFaturaDados {
    /**
     * Envia para uma fila a requisição com os daods enviados ao JDE para criação de fatura
     *
     * @param requisicao a requisição que deve ser enviada à fila
     */
    void enviarRequisicaoIncluirFatura(IncluirFaturaRequisicaoVo requisicao);

    /**
     * Envia para uma fila a resposta do JDE à requisição de criação de fatura
     *
     * @param resposta a resposta que deve ser enviada à fila
     */
    void enviarRespostaIncluirFatura(IncluirFaturaRespostaVo resposta);
}
