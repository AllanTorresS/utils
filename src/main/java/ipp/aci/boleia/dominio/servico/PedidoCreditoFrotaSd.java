package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IFrotaDados;
import ipp.aci.boleia.dados.IMotivoAlteracaoStatusFrotaDados;
import ipp.aci.boleia.dados.IPedidoCreditoFrotaDados;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.MotivoAlteracaoStatusFrota;
import ipp.aci.boleia.dominio.PedidoCreditoFrota;
import ipp.aci.boleia.dominio.enums.StatusPedidoCredito;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Implementa as regras de negocio relacionadas com os pedidos de credito pre pago
 */
@Component
public class PedidoCreditoFrotaSd {

    @Autowired
    private Mensagens mensagens;

    @Autowired
    private IPedidoCreditoFrotaDados repositorioPedido;

    @Autowired
    private TransacaoFrotaSd transacaoFrotaSd;

    @Autowired
    private EmailSd emailSd;

    @Autowired
    private IFrotaDados repositorio;

    @Autowired
    private UtilitarioAmbiente ambiente;

    @Autowired
    private FrotaSd regrasFrota;

    @Autowired
    private MotivoAlteracaoStatusFrotaSd motivoAlteracaoStatusFrotaSd;

    @Autowired
    private IMotivoAlteracaoStatusFrotaDados repositorioMotivo;

    /**
     * Valida o status de pagamento do pedido no momento da aprovacao
     *
     * @param pedido O pedido
     * @throws ExcecaoValidacao Caso o status do pedido nao possibilite aprovacao
     */
    public void validarStatusPagamento(PedidoCreditoFrota pedido) throws ExcecaoValidacao {
        if(!pedido.getStatus().equals(StatusPedidoCredito.PAGOAMAIS.getValue()) && !pedido.getStatus().equals(StatusPedidoCredito.PAGOAMENOS.getValue())) {
            throw new ExcecaoValidacao(mensagens.obterMensagem("frota.servico.credito.prepago.erro.status"));
        }
    }

    /**
     * Dado o estado do pedido de credito informado, dispara os processos de negocio associados:
     * Concessao do credito no saldo da frota (caso pago) ou
     * Envio de email com lembrete de pagamento (caso atrasado).
     *
     * @param pedido O pedido em questao
     * @return o pedido atualizado
     */
    public PedidoCreditoFrota tratarAtualizacaoStatusPedidocredito(PedidoCreditoFrota pedido) {
        if (pedido.getStatus().equals(StatusPedidoCredito.PAGO.getValue())) {
            pedido.setValidadePedido(UtilitarioCalculoData.adicionarDiasData(ambiente.buscarDataAmbiente(), 180));
            pedido = repositorioPedido.armazenar(pedido);
            transacaoFrotaSd.concederPagamentoCreditoPrePago(pedido);
            if (regrasFrota.isFrotaInativadaPorSaldoZerado(pedido.getFrota())) {
                ativarFrotaPrePaga(pedido.getFrota());
            }
            if(pedido.getFrota().getPrimeiraCompra() == null || !pedido.getFrota().getPrimeiraCompra()) {
                atualizarPrimeiraCompraFrota(pedido.getFrota().getId(),pedido.getCodigoPedido());
            }
        } else {
            Date diaAnteriorAoVencimento =  UtilitarioCalculoData.adicionarDiasData(pedido.getValidadePedido(), -1);
            if ((pedido.getStatus().equals(StatusPedidoCredito.PENDENTE.getValue())) && UtilitarioCalculoData.isHoje(ambiente.buscarDataAmbiente(), diaAnteriorAoVencimento)) {
                emailSd.enviarEmailLembreteVencimentoCobrancaCredito(pedido.getFrota().getId(), pedido.getCodigoPedido());
            }
            repositorioPedido.armazenar(pedido);
        }
        return pedido;
    }

    /**
     * Caso a frota possua dois ou mais pedidos de credito pendentes, lanca uma excecao impedindo a compra de um novo pedido
     * @throws ExcecaoValidacao Caso a frota possua 2 ou mais pedidos pendentes
     */
    public void validarPedidosPendentesCreditoFrota() throws ExcecaoValidacao {
        List<PedidoCreditoFrota> pedidosPendentes24Horas = repositorioPedido.obterPendentesAposData(UtilitarioCalculoData.adicionarDiasData(ambiente.buscarDataAmbiente(), -1));
        if(pedidosPendentes24Horas.size() >= 2) {
            throw new ExcecaoValidacao("frota.servico.pedidocredito.pendentes.invalido");
        }
    }

    /**
     * Atualiza uma frota, informando que ela realizou sua primeira compra de creditos pre pagos
     * @param idFrota O id da frota
     * @param codigoPedido O pedido de credito
     */
    public void atualizarPrimeiraCompraFrota(Long idFrota, String codigoPedido) {
        Frota frota = repositorio.obterPorId(idFrota);
        frota.setPrimeiraCompra(true);
        repositorio.armazenar(frota);
        emailSd.enviarEmailPrimeiraCompraFrota(idFrota, codigoPedido);
    }

    /**
     * Reativacao de uma frota pre paga que estava previamente inativada
     * @param frota a ser reativada
     */
    public void ativarFrotaPrePaga(Frota frota) {
        Date dataCorrente = ambiente.buscarDataAmbiente();
        MotivoAlteracaoStatusFrota motivo = frota.getUltimoMotivoSaldoZeradoVigente();
        if(motivo != null) {
            motivo.setDataInicio(motivo.getDataCriacao());
            motivo.setDataFim(dataCorrente);
            repositorioMotivo.armazenar(motivo);
            frota.setDataSaldoZerado(null);
            frota = repositorio.armazenar(frota);
        }
    }
}
