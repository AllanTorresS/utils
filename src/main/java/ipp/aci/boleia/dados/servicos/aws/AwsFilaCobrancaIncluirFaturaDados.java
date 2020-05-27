package ipp.aci.boleia.dados.servicos.aws;

import ipp.aci.boleia.dados.IFilaCobrancaIncluirFaturaDados;
import ipp.aci.boleia.dominio.vo.IncluirFaturaRequisicaoVo;
import ipp.aci.boleia.dominio.vo.IncluirFaturaRespostaVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Repository;

/**
 * Implementação do repositorio da fila de autorização de pagamento consolidado
 */
@Repository
public class AwsFilaCobrancaIncluirFaturaDados implements IFilaCobrancaIncluirFaturaDados {

    // Nomes das filas que serão acessadas
    @Value("${aws.sqs.cobranca.requisicao-incluir-fatura}")
    private String nomeFilaCobrancaRequisicaoFatura;

    @Value("${aws.sqs.cobranca.resposta-incluir-fatura}")
    private String nomeFilaCobrancaRespostaFatura;

    // Gerenciador de acesso à fila
    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @Override
    public void enviarRequisicaoIncluirFatura(IncluirFaturaRequisicaoVo requisicao) {
        this.queueMessagingTemplate.convertAndSend(nomeFilaCobrancaRequisicaoFatura, requisicao);
    }

    @Override
    public void enviarRespostaIncluirFatura(IncluirFaturaRespostaVo resposta) {
        this.queueMessagingTemplate.convertAndSend(nomeFilaCobrancaRespostaFatura, resposta);
    }
}
