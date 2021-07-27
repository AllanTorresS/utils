package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.beneficios.PedidoCreditoBeneficios;

import java.util.List;

/**
 * Contrato para implementação de repositórios de entidades PedidoCreditoBeneficios.
 */
public interface IPedidoCreditoBeneficiosDados extends IRepositorioBoleiaDados<PedidoCreditoBeneficios> {

    /**
     * Retorna todos os pedidos d
     *
     * @param idFrota
     * @return
     */
    List<PedidoCreditoBeneficios> obterPedidosCreditoBeneficioAbertosPorFrota(Long idFrota);

}
