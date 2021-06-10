package ipp.aci.boleia.dados.servicos.jde;

import ipp.aci.boleia.dominio.ReembolsoBase;

/**
 * Inferface base para integração com o serviço de Voucher.
 */
public interface IVoucherBaseDados {

    /**
     * Método que cria um voucher novo no JDE.
     *
     * @param reembolso Reembolso para qual será gerado o voucher.
     * @param <T> Tipo de reembolso utilizado na criação do voucher.
     * @return Reembolso atualizado.
     */
    <T extends ReembolsoBase> T criar(T reembolso);

    /**
     * Método que busca um voucher existente no JDE.
     *
     * @param reembolso Reembolso para qual foi gerado o voucher.
     * @param <T> tipo de reembolso que está sendo consultado.
     * @return Reembolso atualiado.
     */
    <T extends ReembolsoBase> T consultar(T reembolso);
}
