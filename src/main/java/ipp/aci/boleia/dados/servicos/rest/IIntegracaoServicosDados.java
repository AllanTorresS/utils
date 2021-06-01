package ipp.aci.boleia.dados.servicos.rest;

import ipp.aci.boleia.dominio.vo.IRespostaModuloInternoVo;
import org.apache.http.client.methods.CloseableHttpResponse;

/**
 * Contrato de implementação para integração entre serviços do Pró-Frotas
 */
public interface IIntegracaoServicosDados {

    /**
     * Requisita token JWT de autenticação a partir de par client/secret
     * @param client Client utilizado na autenticação do serviço
     * @param secret Secret utilizado na autenticação do serviço
     * @return O token gerado
     */
    String obterTokenIntegracao(String client, String secret);

    /**
     * Consumidor para respostas de requisições oriundas da integração
     * @param resposta Resposta da requisição
     * @return Objeto com os dados de resposta
     */
    IRespostaModuloInternoVo consumirResposta(CloseableHttpResponse resposta);
}
