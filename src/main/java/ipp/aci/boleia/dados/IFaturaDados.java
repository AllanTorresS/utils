package ipp.aci.boleia.dados;

import ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws.IncluirJDEReq;
import ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws.IncluirJDEResp;
import ipp.aci.boleia.dominio.AjusteCobranca;
import ipp.aci.boleia.dominio.CicloRepasse;
import ipp.aci.boleia.dominio.Cobranca;
import ipp.aci.boleia.dominio.PedidoCreditoFrota;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;

import java.util.List;

/**
 * Contrato para implementacao de reposiórios de Fatura do JDE.
 * 
 */
public interface IFaturaDados {

    /**
     * Método que inclui uma fatura nova no JDE.
     * 
     * @param requisicao a requisição de inclusão de fatura que deve ser enviada ao JDE
     * @return resposta do JDE
     */
    IncluirJDEResp incluir(IncluirJDEReq requisicao);


    /**
     * Método que inclui crédito a uma frota no JDE.
     *
     * @param pedido de inclusão de crédito pago pela frota.
     * @return pedido atualizado
     */
    PedidoCreditoFrota incluir(PedidoCreditoFrota pedido);

    /**
     * Método que consulta a cobrança gerada no JDE e atualiza a
     * entidade de Cobrança com o status encontrado no registro da integração.
     *
     * @param cobranca Cobrança que será consultada.
     * @return Cobrança atualizada
     */
    Cobranca consultarCobranca(Cobranca cobranca);

    /**
     * Método que inclui ciclos de repasse no JDE.
     *
     * @param ciclosRepasse os ciclos de repasse em questão
     * @return ciclo de repasse atualizado
     */
    List<CicloRepasse> incluir(List<CicloRepasse> ciclosRepasse);

    /**
     * Método que consulta o ciclo de repasse gerado no JDE e atualiza a
     * entidade de CicloCobranca com o status encontrado no registro da integração.
     *
     * @param cicloRepasse CicloRepasse que será consultada.
     * @return CicloRepasse atualizado
     */
    CicloRepasse consultarCicloRepasse(CicloRepasse cicloRepasse);

    /**
     * Cria uma requisicao para inclusao de cobranca no JDE.
     *
     * @param cobranca A cobranca em questao
     * @param cobrancaAnterior A cobrança anterior à cobrança em questão
     * @return Um objeto de requisicao conforme o contrato de uso do JDE
     */
    IncluirJDEReq obterRequisicaoIncluir(Cobranca cobranca, Cobranca cobrancaAnterior);

    /**
     * Método que alterar a data de vencimento em um documento
     *
     * @param ajusteCobranca O ajuste realizado na cobrança
     * @throws ExcecaoValidacao caso ocorra erro durante o processo.
     */
    void prorrogarVencimento(AjusteCobranca ajusteCobranca) throws ExcecaoValidacao;
}