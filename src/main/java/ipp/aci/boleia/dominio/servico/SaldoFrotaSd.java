package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IAutorizacaoPagamentoDados;
import ipp.aci.boleia.dados.ISaldoFrotaDados;
import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.SaldoFrota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Oferece funcionalidades para o saldo da frota
 */
@Component
public class SaldoFrotaSd {

    @Autowired
    private CampanhaSd campanhaSd;

    @Autowired
    protected IAutorizacaoPagamentoDados autorizacaoPagamentoRepositorio;

    @Autowired
    private ISaldoFrotaDados repositorioSaldo;

    /**
     * Atualiza o saldo da frota.
     * @param frota a frota que deve ter o saldo alterado
     * @param valorTotal o valor total que deve ser adicionado ao saldo
     */
    public void atualizarSaldoFrota(Frota frota, BigDecimal valorTotal){
        SaldoFrota saldoFrotaAlterado = repositorioSaldo.obterSaldoPorIdFrota(frota.getId());
        saldoFrotaAlterado.setSaldoCorrente(saldoFrotaAlterado.getSaldoCorrente().add(valorTotal));
        repositorioSaldo.armazenar(saldoFrotaAlterado);
    }

    /**
     * Valida o saldo da frota.
     * @param autorizacaoOriginal abastecimento com dados originais
     * @param frota dados alterados da frota
     * @param valorAbastecimentoAlterado valor a ser alterado
     * @param valorItensAbastecimento valor total dos itens de abastecimento alterados
     * @return se a frota tem saldo suficiente
     */
    public boolean saldoFrotaSuficiente(AutorizacaoPagamento autorizacaoOriginal, Frota frota, BigDecimal valorAbastecimentoAlterado, BigDecimal valorItensAbastecimento) {
        SaldoFrota saldoFrota = repositorioSaldo.obterSaldoPorIdFrota(frota.getId());
        BigDecimal descontoRecalculado = campanhaSd.obterDescontoNominalEmRelacaoAutorizacaoPagamento(autorizacaoOriginal);
        BigDecimal valorAbastecimentoEditadoComDesconto = valorAbastecimentoAlterado.add(valorItensAbastecimento).subtract(descontoRecalculado);
        BigDecimal saldoCalculado = frota.getId().equals(autorizacaoOriginal.getFrota().getId()) ?
                saldoFrota.getSaldoCorrente().add(autorizacaoOriginal.getValorTotalComDesconto()) :
                saldoFrota.getSaldoCorrente();
        return saldoCalculado.subtract(valorAbastecimentoEditadoComDesconto).compareTo(BigDecimal.ZERO) >= 0;
    }
}
