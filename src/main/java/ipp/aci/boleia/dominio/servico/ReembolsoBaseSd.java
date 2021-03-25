package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IReembolsoBaseDados;
import ipp.aci.boleia.dados.servicos.jde.IVoucherBaseDados;
import ipp.aci.boleia.dominio.Reembolso;
import ipp.aci.boleia.dominio.ReembolsoBase;
import ipp.aci.boleia.dominio.enums.StatusPagamentoReembolso;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

import static ipp.aci.boleia.dominio.enums.StatusPagamentoReembolso.ATRASADO;
import static ipp.aci.boleia.dominio.enums.StatusPagamentoReembolso.A_DESCONTAR;
import static ipp.aci.boleia.dominio.enums.StatusPagamentoReembolso.EM_ABERTO;
import static ipp.aci.boleia.util.UtilitarioCalculoData.obterPrimeiroInstanteDia;

/**
 * Serviços base de domínio da entidade {@link ipp.aci.boleia.dominio.Reembolso}.
 */
@Component
public class ReembolsoBaseSd<T extends ReembolsoBase> {

    protected static final int QUANTIDADE_DIAS_A_MAIS_REEMBOLSO = 2;

    @Autowired
    private IReembolsoBaseDados repositorioReembolsoBase;

    @Autowired
    private UtilitarioAmbiente utilitarioAmbiente;

    @Autowired
    private IVoucherBaseDados repositorioVoucher;

    /**
     * Comunica com o JDE, solicitando a criacao de um voucher de reembolso.
     *
     * @param reembolso Os dados do reembolso para a criacao do voucher
     * @return O reembolso contendo o voucher.
     */
    public T gerarVoucher(T reembolso) {
        T reembolsoComVoucher = repositorioVoucher.criar(reembolso);
        incrementarNumeroTentativasEnvio(reembolsoComVoucher);
        return (T) repositorioReembolsoBase.armazenar(reembolsoComVoucher);
    }

    /**
     * Incrementa o número de tentativas realizadas para a criação do voucher no JDE.
     *
     * @param reembolso Reembolso que terá o numero de tentativas incrementado.
     */
    private void incrementarNumeroTentativasEnvio(ReembolsoBase reembolso) {
        Integer numeroTentativasEnvio = reembolso.getNumeroTentativasEnvio();
        if (numeroTentativasEnvio != null) {
            reembolso.setNumeroTentativasEnvio(numeroTentativasEnvio + 1);
        } else {
            reembolso.setNumeroTentativasEnvio(1);
        }
    }

    /**
     * Atualiza o status de um reembolso.
     *
     * @param reembolso Reembolso que terá o status atualizado.
     */
    public void atualizarStatusReembolso(Reembolso reembolso) {
        StatusPagamentoReembolso statusReembolso = StatusPagamentoReembolso.PAGO;
        Date dataHoraCorrente = utilitarioAmbiente.buscarDataAmbiente();

        if(!reembolso.isPago()) {
            if(reembolso.getValorReembolso().compareTo(BigDecimal.ZERO) < 0) {
                statusReembolso = A_DESCONTAR;
            } else if(obterPrimeiroInstanteDia(reembolso.getDataVencimentoPgto()).before(obterPrimeiroInstanteDia(dataHoraCorrente))) {
                statusReembolso = ATRASADO;
            } else {
                statusReembolso = EM_ABERTO;
            }
        }
        reembolso.setStatus(statusReembolso.getValue());
        repositorioReembolsoBase.armazenar(reembolso);
    }


    /**
     * Define a data de vencimento do pagamento de um reembolso.
     *
     * @param dataReferencia Data de referência.
     * @return A data de vencimento do pagamento.
     */
    public Date definirDataVencimentoPagamento(Date dataReferencia) {
        Date dataAtual = utilitarioAmbiente.buscarDataAmbiente();
        Date dataVencimento = UtilitarioCalculoData.adicionarDiasData(dataReferencia, QUANTIDADE_DIAS_A_MAIS_REEMBOLSO);
        return obterPrimeiroInstanteDia(dataVencimento);
    }
}
