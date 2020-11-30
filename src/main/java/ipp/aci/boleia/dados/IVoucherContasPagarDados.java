package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.Reembolso;
import ipp.aci.boleia.dominio.ReembolsoConectcar;

/**
 * Interface de acesso para as operações do serviço Voucher Contas a Pagar do JDE.
 *
 * @author pedro.silva
 */
public interface IVoucherContasPagarDados {

    /**
     * Libera um reembolso para pagamento no JDE.
     *
     * @param reembolso Reembolso para que será liberado.
     * @return Reembolso liberado
     */
    Reembolso liberar(Reembolso reembolso);

    /**
     * Libera um reembolso para pagamento no JDE.
     *
     * @param reembolso Reembolso para que será liberado.
     * @return Reembolso liberado
     */
    ReembolsoConectcar liberar(ReembolsoConectcar reembolso);
}