package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.agenciadorfrete.MotoristaAutonomo;
import ipp.aci.boleia.dominio.agenciadorfrete.Pedido;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;

import java.util.Date;
import java.util.List;

/**
 * Contrato para implementacao de acesso aos dados do pedido do motorista autonomo
 */
public interface IPedidoMotoristaAutonomoDados extends IRepositorioBoleiaDados<Pedido> {
    /**
     * Obter pedido por numero
     * @param numero O numero do pedido
     * @return O pedido
     */
    Pedido obterPorNumero(String numero);

    /**
     * Obter os pedidos pelo cpf do motorista autonomo
     * @param cpf O cpf do motorista autonomo
     * @param pagina pagina atual na busca
     * @param tamanho quantidade máxima de itens por pagina
     * @return Lista de pedidos paginada
     */
    ResultadoPaginado<Pedido> obterPorCpf(String cpf, int pagina, int tamanho);

    /**
     * Exclui o pedido do motorista Autonomo
     * @param motoristaAutonomo O motorista autonomo
     */
    void excluirPedidoPorMotorista(MotoristaAutonomo motoristaAutonomo);

    /**
     * Obter os pedidos pelo cpf e data de expiração do motorista autonomo
     * @param cpf O motorista autonomo
     * @param dataExpiracao A data de expiração do pedido
     */
    List<Pedido> obterPorCpfDataExpiracao(String cpf, Date dataExpiracao);

}
