package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.Rede;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades Rede
 */
public interface IRedeDados extends IRepositorioBoleiaDados<Rede> {

    /**
     * Busca por nome igual mantendo unicidade
     * @param nomeRede nome da rede
     * @return Rede encontrada
     */
    Rede buscarPorNome(String nomeRede);

    /**
     * Pesquisar redes pelo termo dentro do nome
     *
     * @param termo a pesquisar no nome da rede
     * @return lista de redes localizadas
     */
    List<Rede> pesquisar(String termo);

    /**
     * Obtém a rede de um ponto de venda
     * @param idPv Identificador do ponto de venda
     * @return A rede correspondente
     */
    Rede obterPorPontoDeVenda(Long idPv);
}
