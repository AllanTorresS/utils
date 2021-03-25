package ipp.aci.boleia.dados.servicos.jde;

import ipp.aci.boleia.dominio.ReembolsoBase;

public interface IVoucherBaseDados {

    /**
     * Método que cria um voucher novo no JDE.
     *
     * @param reembolso Reembolso para qual será gerado o voucher.
     * @return Reembolso atualiado
     */
    <T extends ReembolsoBase> T criar(T reembolso);

    /**
     * Método que busca um voucher existente no JDE.
     *
     * @param reembolso Reembolso para qual foi gerado o voucher.
     * @return Reembolso atualiado
     */
    <T extends ReembolsoBase> T consultar(T reembolso);
}
