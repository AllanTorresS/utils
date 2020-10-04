package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.Reembolso;
import ipp.aci.boleia.dominio.ReembolsoConectcar;

/**
 * Contrato para implementacao de reposiórios de Voucher do JDE.
 * 
 */
public interface IVoucherDados {

    /**
     * Método que cria um voucher novo no JDE.
     * 
     * @param reembolso Reembolso para qual será gerado o voucher.
     * @return Reembolso atualiado
     */
    Reembolso criar(Reembolso reembolso);

    /**
     * Método que cria um voucher novo no JDE.
     * 
     * @param reembolso Reembolso para qual será gerado o voucher.
     * @return Reembolso atualiado
     */
    ReembolsoConectcar criar(ReembolsoConectcar reembolso);

    /**
     * Método que busca um voucher existente no JDE.
     *
     * @param reembolso Reembolso para qual foi gerado o voucher.
     * @return Reembolso atualiado
     */
    Reembolso consultar(Reembolso reembolso);
}