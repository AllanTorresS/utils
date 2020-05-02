package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.AjusteCobranca;

/**
 * Interface responsável pela integração com o serviço VincularJurosBoleto do JDE.
 */
public interface IVincularJurosBoletoDados {

    /**
     * Realiza o vínculo de juros em um boleto D3 no JDE.
     *
     * @param ajusteCobranca O ajuste de cobrança que irá gerar o vínculo de juros.
     */
    void vincular(AjusteCobranca ajusteCobranca);
}
