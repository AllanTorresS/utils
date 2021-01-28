package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IReembolsoDados;
import ipp.aci.boleia.dominio.Reembolso;
import ipp.aci.boleia.dominio.enums.StatusPagamentoReembolso;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static ipp.aci.boleia.dominio.enums.StatusPagamentoReembolso.ATRASADO;
import static ipp.aci.boleia.dominio.enums.StatusPagamentoReembolso.A_DESCONTAR;
import static ipp.aci.boleia.dominio.enums.StatusPagamentoReembolso.EM_ABERTO;
import static ipp.aci.boleia.util.UtilitarioCalculoData.obterPrimeiroInstanteDia;

/**
 * Serviços base de domínio da entidade {@link ipp.aci.boleia.dominio.Reembolso}.
 */
@Component
public class ReembolsoBaseSd {

    private static final int QUANTIDADE_DIAS_A_MAIS_REEMBOLSO = 2;
    private static final Integer NUMERO_MAXIMO_TENTATIVAS_ENVIO = 5;

    @Autowired
    private IReembolsoDados repositorioReembolso;

    @Autowired
    private UtilitarioAmbiente utilitarioAmbiente;

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
