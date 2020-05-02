package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Encapsula as regras de negocio que envolvem a manipulacao de Precos Negociados
 */

@Component
public class PrecoNegociadoSd {

    @Autowired
    private Mensagens mensagens;

    /**
     * Valida se o desconto é igual a zero
     * @param desconto a ser dado no valor do preço
     * @param possuiDesconto indica se a negociação trata-se de um desconto ou acréscimo.
     * @throws ExcecaoValidacao Caso haja algum erro de validação
     */
    public void validaDesconto(BigDecimal desconto, Boolean possuiDesconto) throws ExcecaoValidacao {
        if(desconto != null){
            int comparacaoDesconto = desconto.compareTo(BigDecimal.ZERO);

            if((possuiDesconto && comparacaoDesconto > 0) || (!possuiDesconto && comparacaoDesconto < 0) || (comparacaoDesconto == 0)){
                throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO, mensagens.obterMensagem("precoNegociado.novoAcordo.validacao.desconto.invalido"));
            }
        }

    }
}
