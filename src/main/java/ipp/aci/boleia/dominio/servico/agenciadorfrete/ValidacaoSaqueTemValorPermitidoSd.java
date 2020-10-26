package ipp.aci.boleia.dominio.servico.agenciadorfrete;

import ipp.aci.boleia.dominio.agenciadorfrete.Transacao;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Classe que valida se o valor de saque está dentro do limite permitido
 */
@Component
public class ValidacaoSaqueTemValorPermitidoSd {

    @Autowired
    private Mensagens mensagens;

    /**
     * Valida se o valor de saque está dentro do limite permitido
     * @param transacao A trasação a ser validada
     * @throws ExcecaoValidacao Caso a validação falhe
     */
    public void validar(Transacao transacao) throws ExcecaoValidacao {
        if(transacao.getSaque() != null) {
            BigDecimal valorAbastecimento = (transacao.getAbastecimento().getPrecoCombustivel().multiply(transacao.getAbastecimento().getLitragem()));
            BigDecimal divisorMetade = new BigDecimal(2);
            BigDecimal valorMaximoSaque = valorAbastecimento.divide(divisorMetade , RoundingMode.HALF_UP)
                    .setScale(2, RoundingMode.HALF_UP);

            if(transacao.getSaque().getValorSolicitado().compareTo(valorMaximoSaque) > 0){
                throw new ExcecaoValidacao(mensagens.obterMensagem("agentefrete.api.validacao.saque.valorPemitido",
                        UtilitarioFormatacao.formatarCnpjApresentacao(transacao.getMotorista().getAgenciadorFrete().getSistemaExterno().getCnpj())));
            }
        }
    }
}
