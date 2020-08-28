package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.agenciadorfrete.AgenciadorFreteCobranca;
import ipp.aci.boleia.dominio.agenciadorfrete.MotoristaAutonomo;
import ipp.aci.boleia.dominio.agenciadorfrete.Transacao;
import ipp.aci.boleia.dominio.enums.StatusAutorizacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.agenciadorfrete.FiltroRelatorioTransacaoVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Interface responsável pelo acesso aos dados da Transacao do Agenciador de frete na base
 */
public interface IAgenciadorFreteTransacaoDados extends IRepositorioBoleiaDados<Transacao> {

    /**
     * Obtém transações a partir do número do pedido isolado pelo cpf do motorista
     * @param cpfMotorista Cpf do motorista
     * @param numeroPedido Número do pedido
     * @return Lista de transações pertencentes a um pedido
     */
    List<Transacao> obterPorPedido(String cpfMotorista, String numeroPedido);

    /**
     * Obtém transações a partir do número do pedido isolado pelo cpf do motorista
     * @param numeroPedido Número do pedido
     * @return Lista de transações pertencentes a um pedido
     */
    List<Transacao> obterPorPedido(String numeroPedido);

    /**
     * Obtém as transações a partir de um cpf do motorista
     * @param cpfMotorista Cpf do motorista
     * @param pagina Página atual
     * @param tamanho A quantidade de itens por página
     * @return Transações páginadas pertencentes a um motorista
     */
    ResultadoPaginado<Transacao> obterPorCpf(String cpfMotorista, Integer pagina, Integer tamanho);

    /**
     * Obtém as transações a partir de um filtro de um parâmetro
     * @param pontoDeVenda O ponto de venda
     * @param motorista O motorista
     * @param placaVeiculo A placa do veiculo
     * @param status O status de autorização
     * @param litragem A litragem
     * @param precoCombustivel O preço unitário
     * @return Uma lista de transações
     */
    List<Transacao> obterTransacao(PontoDeVenda pontoDeVenda, MotoristaAutonomo motorista, String placaVeiculo, StatusAutorizacao status, BigDecimal litragem, BigDecimal precoCombustivel);

    /**
     * Pesquisa todas as transações
     * @param filtro Filtro que representa um critério de busca por abastecimento
     * @return Resultado paginado das transações
     */
    ResultadoPaginado<Transacao> pesquisar(FiltroRelatorioTransacaoVo filtro);
}