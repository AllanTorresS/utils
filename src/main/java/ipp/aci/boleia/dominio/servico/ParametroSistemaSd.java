package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.FrotaParametroSistemaPostoAutorizadoAbastecimento;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Serviços de domínio do parametro de sistema.
 *
 * @author pedro.silva
 */
@Component
public class ParametroSistemaSd {

    /**
     * Informa se houve uma violação do limite de abastecimento cadastrado no parametro Postos Permitidos.
     *
     * @param autorizacaoPagamento Abastecimento verificado.
     * @param autorizacaoPosto Permissão de abastecimento no posto
     * @param emLitros Informa se o limite de abastecimento é do tipo litragem.
     * @return True, caso tenha ocorrido alguma violação.
     */
    public boolean violouLimiteAbastecimento(AutorizacaoPagamento autorizacaoPagamento, FrotaParametroSistemaPostoAutorizadoAbastecimento autorizacaoPosto, boolean emLitros) {
        if(emLitros) {
            return autorizacaoPosto.getMaximoLitros() != null && autorizacaoPagamento.getTotalLitrosAbastecimento() != null &&
                    autorizacaoPosto.getMaximoLitros().compareTo(BigDecimal.ZERO) > 0 &&
                    autorizacaoPagamento.getTotalLitrosAbastecimento().compareTo(autorizacaoPosto.getMaximoLitros()) > 0;
        }
        return autorizacaoPosto.getMaximoValor() != null && autorizacaoPagamento.getValorTotalAbastecimento() != null &&
                autorizacaoPosto.getMaximoValor().compareTo(BigDecimal.ZERO) > 0 &&
                autorizacaoPagamento.getValorTotalAbastecimento().compareTo(autorizacaoPosto.getMaximoValor()) > 0;

    }
}
