package ipp.aci.boleia.dados.servicos.salesforce;

import ipp.aci.boleia.dados.ISalesForceInteresseAntecipacaoDados;
import ipp.aci.boleia.dominio.vo.SolicitacaoInteresseAntecipacaoVo;
import org.springframework.stereotype.Repository;

/**
 * Repositório para gerenciar solicitações de interesse no programa de antecipação de recebíveis
 */
@Repository
public class SalesForceInteresseAntecipacaoDados extends SalesForceAtualizacaoContaBase implements ISalesForceInteresseAntecipacaoDados {

    private static final String MSG_LOG_ERRO_AO_SOLICITAR_ANTECIPACAO = "Erro ao solicitar ao Salesforce participação no programa de antecipação de recebíveis";

    @Override
    public void atualizarCampoInteresseAntecipacao(Long cnpj, boolean novoValor) {
        SolicitacaoInteresseAntecipacaoVo corpo = new SolicitacaoInteresseAntecipacaoVo(novoValor);
        try {
            prepararRequisicaoAtualizacaoPosto(cnpj, corpo);
            if (!restDados.doPatchJson(this.endpointUrl, corpo, this.authorizationHeaders, super::tratarAtualizacaoConta)) {
                if (!restDados.doPatchJson(this.endpointUrl, corpo, this.authorizationHeaders, super::tratarAtualizacaoConta)) {
                    throw new RuntimeException(this.mensagem);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(MSG_LOG_ERRO_AO_SOLICITAR_ANTECIPACAO, e);
        }
    }
}
