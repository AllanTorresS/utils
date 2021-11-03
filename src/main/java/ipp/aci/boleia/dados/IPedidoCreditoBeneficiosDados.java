package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.beneficios.PedidoCreditoBeneficios;

import java.util.List;

/**
 * Contrato para implementação de repositórios de entidades PedidoCreditoBeneficios.
 */
public interface IPedidoCreditoBeneficiosDados extends IRepositorioBoleiaDados<PedidoCreditoBeneficios> {

    /**
     * Obtém os pedidos de crédito para benefícios que estejam Pendentes ou Atrasados da frota autenticada.
     *
     * @return Retorna os pedidos da frota.
     */
    List<PedidoCreditoBeneficios> obterPedidosCreditoBeneficioAbertos();

    /**
     * Obtém todos os pedidos de crédito para benefícios que estejam Pendentes ou Atrasados.
     *
     * @return Retorna todos os pedidos abertos.
     */
    List<PedidoCreditoBeneficios> obterPedidosAbertos();

}
