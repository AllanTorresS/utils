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
public class ReembolsoBaseSd {

    @Autowired
    private IReembolsoBaseDados repositorioReembolsoBase;

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
        repositorioReembolsoBase.armazenar(reembolso);
    }
}
