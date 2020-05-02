package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.Vip;

/**
 * Contrato para implementacao de repositorios de entidades Vip
 */
public interface IVipDados extends IRepositorioBoleiaDados<Vip> {

    /**
     * Localiza um Vip a partir do seu codigoVip
     *
     * @param codigoVip O cracha do Vip
     * @param codigoCorporativoPontoDeVenda o codigo corporativo do Ponto de Venda
     * @return O Vip encontrado
     */
    Vip obterVip(String codigoVip, Long codigoCorporativoPontoDeVenda);
}
