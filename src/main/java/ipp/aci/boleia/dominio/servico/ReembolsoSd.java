package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IReembolsoDados;
import ipp.aci.boleia.dados.IVoucherDados;
import ipp.aci.boleia.dominio.Reembolso;
import ipp.aci.boleia.dominio.TransacaoConsolidada;
import ipp.aci.boleia.dominio.enums.StatusLiberacaoReembolsoJde;
import ipp.aci.boleia.dominio.enums.StatusPagamentoReembolso;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static ipp.aci.boleia.dominio.enums.StatusLiberacaoReembolsoJde.APROVADO_PAGAMENTO;
import static ipp.aci.boleia.dominio.enums.StatusLiberacaoReembolsoJde.SUSPENSO_PAGAMENTO;
import static ipp.aci.boleia.dominio.enums.StatusPagamentoReembolso.AGUARDANDO_NF;
import static ipp.aci.boleia.dominio.enums.StatusPagamentoReembolso.ATRASADO;
import static ipp.aci.boleia.dominio.enums.StatusPagamentoReembolso.DEBITO_EM_ABERTO;
import static ipp.aci.boleia.dominio.enums.StatusPagamentoReembolso.EM_ABERTO;
import static ipp.aci.boleia.dominio.enums.StatusPagamentoReembolso.NF_ATRASADA;
import static ipp.aci.boleia.dominio.enums.StatusTransacaoConsolidada.FECHADA;
import static ipp.aci.boleia.util.UtilitarioCalculoData.isHojeOuPosterior;

/**
 * Serviços de domínio da entidade {@link ipp.aci.boleia.dominio.Reembolso}.
 */
@Component
public class ReembolsoSd {
    private static final Integer NUMERO_MAXIMO_TENTATIVAS_ENVIO = 5;

    @Autowired
    private IReembolsoDados repositorioReembolso;

    @Autowired
    private IVoucherDados repositorioVoucher;

    @Autowired
    private UtilitarioAmbiente utilitarioAmbiente;

    /**
     * Define qual o status de liberação do JDE de um reembolso que será gerado.
     *
     * @param transacoesConsolidadas Transações consolidadas do reembolso.
     * @return Status de liberação do reembolso.
     */
    public StatusLiberacaoReembolsoJde definirStatusLiberacaoParaReembolso(List<TransacaoConsolidada> transacoesConsolidadas) {
        boolean possuiCicloPendente = transacoesConsolidadas
                .stream()
                .anyMatch(tc -> !tc.esta(FECHADA) || tc.pendenteNotaFiscal());
        if(!possuiCicloPendente){
            return APROVADO_PAGAMENTO;
        }
        return SUSPENSO_PAGAMENTO;
    }

    /**
     * Atualiza o status de um reembolso.
     *
     * @param reembolso Reembolso que terá o status atualizado.
     */
    public void atualizarStatusReembolso(Reembolso reembolso) {
        StatusPagamentoReembolso statusReembolso = StatusPagamentoReembolso.PAGO;
        List<TransacaoConsolidada> transacoesConsolidadas = reembolso.getTransacoesConsolidadas();
        Date dataHoraCorrente = utilitarioAmbiente.buscarDataAmbiente();

        if(!reembolso.isPago()) {
            if(reembolso.possuiPendenciaNotaFiscal()) {
                boolean possuiAtrasoEmissaoNota = transacoesConsolidadas.stream().anyMatch(tc -> !isHojeOuPosterior(dataHoraCorrente, tc.getDataPrazoEmissaoNfe()));
                if(!possuiAtrasoEmissaoNota) {
                    statusReembolso = AGUARDANDO_NF;
                } else if (reembolso.getValorReembolso().compareTo(BigDecimal.ZERO) < 0) {
                    statusReembolso = DEBITO_EM_ABERTO;
                } else {
                    statusReembolso = NF_ATRASADA;
                }
            } else {
                if(reembolso.getValorReembolso().compareTo(BigDecimal.ZERO) < 0) {
                    statusReembolso = DEBITO_EM_ABERTO;
                } else if(reembolso.getDataVencimentoPagto().before(dataHoraCorrente)) {
                    statusReembolso = ATRASADO;
                } else {
                    statusReembolso = EM_ABERTO;
                }
            }
        }
        reembolso.setStatus(statusReembolso.getValue());
        repositorioReembolso.armazenar(reembolso);
    }

    /**
     * Retorna uma lista de reembolsos disponíveis para tentativa de reenvio ao JDE.
     *
     * @return lista de reembolsos para reenvio.
     */
    public List<Reembolso> obterReembolsosParaReenvio() {
        return repositorioReembolso.obterReembolsosErroEnvio(NUMERO_MAXIMO_TENTATIVAS_ENVIO);
    }

    /**
     * Comunica com o JDE, solicitando a criacao de um voucher de reembolso.
     *
     * @param reembolso Os dados do reembolso para a criacao do voucher
     * @return O reembolso contendo o voucher.
     */
    public Reembolso gerarVoucher(Reembolso reembolso) {
        Reembolso reembolsoComVoucher = repositorioVoucher.criar(reembolso);
        incrementarNumeroTentativasEnvio(reembolsoComVoucher);
        return repositorioReembolso.armazenar(reembolsoComVoucher);
    }

    /**
     * Incrementa o número de tentativas realizadas para a criação do voucher no JDE.
     *
     * @param reembolso Reembolso que terá o numero de tentativas incrementado.
     */
    private void incrementarNumeroTentativasEnvio(Reembolso reembolso) {
        Integer numeroTentativasEnvio = reembolso.getNumeroTentativasEnvio();
        if (numeroTentativasEnvio != null) {
            reembolso.setNumeroTentativasEnvio(numeroTentativasEnvio + 1);
        } else {
            reembolso.setNumeroTentativasEnvio(1);
        }
    }
}
