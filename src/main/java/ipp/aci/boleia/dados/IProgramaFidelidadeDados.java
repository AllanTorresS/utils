package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.vo.KmvAcumuloVo;
import ipp.aci.boleia.dominio.vo.KmvVo;

import java.util.List;

/**
 * Contrato para implementacao de repositórios do programa de fidelidade Km de Vantagens.
 */
public interface IProgramaFidelidadeDados {

    /**
     * Registra o acúmulo de Km de vantagens
     *
     * @param acumulo Objeto que contêm os dados do acúmulo
     * @return O resultado da operacao de acumulo de pontos
     */
    KmvVo acumularPontos(KmvAcumuloVo acumulo);

    /**
     * Obtem os acúmulos de Km de Vantagens pendentes
     *
     * @param numeroDeRegistros Limita o número de registros retornados na consulta
     * @return A lista de acúmulos pendentes.
     */
    List<KmvAcumuloVo> obterAcumulosPendentes(Integer numeroDeRegistros);

    /**
     * Consulta um determinado cep
     *
     * @param cep O cep a ser consultado
     * @return O endereço encontrado a partir do cep
     */
    String pesquisaCep(String cep);

    /**
     * Retorna o saldo do Km de Vantagens do mostorista
     *
     * @param cpf o cpf do motorista registrado na KMV
     * @param senha a senha utilizada no KMV pelo motorista
     * @return O saldo do motorista no Km de Vantagens
     */
    Integer mostrarSaldo(String cpf, String senha);
}