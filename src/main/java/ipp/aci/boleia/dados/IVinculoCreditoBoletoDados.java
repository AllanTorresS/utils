package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.AjusteCobranca;

/**
 * Interface responsável pela integração com o serviço
 * VinculoCreditoBoleto do JDE.
 */
public interface IVinculoCreditoBoletoDados {

    /**
     * Realiza o vínculo de crédito em um boleto D3 no JDE.
     *
     * @param ajusteCobranca O ajuste de cobrança que irá gerar o vínculo de crédito.
     */
    void vincular(AjusteCobranca ajusteCobranca);
}
