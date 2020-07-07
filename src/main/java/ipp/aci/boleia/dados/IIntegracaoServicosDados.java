package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.vo.RespostaIntegracaoVo;
import org.apache.http.client.methods.CloseableHttpResponse;

/**
 * Contrato de implementação para integração entre serviços do Pró-Frotas
 */
public interface IIntegracaoServicosDados {

    /**
     * Requisita token JWT de autenticação a partir de par client/secret
     * @return O token gerado
     */
    String obterTokenIntegracao(String client, String secret);

    /**
     * Consumidor para respostas de requisições oriundas da integração
     */
    RespostaIntegracaoVo consumirResposta(CloseableHttpResponse resposta);
}
