package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.ContaBancaria;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;

/**
 * Repositorio para manipulacao de dados bancarios residentes em sistemas externos
 */
@FunctionalInterface
public interface IContaBancariaIntegracaoDados {

    /**
     *  Realiza o cadastro das informações de Conta Corrente no
     *  registro do Ponto de Venda na integração JDE
     *
     * @param conta Conta Bancaria a ser cadastrada
     * @param numeroJdeInterno Identificador do Ponto de Venda no JDE
     * @throws ExcecaoValidacao Caso haja algum erro de validação
     */
    void cadastrarContaBancaria(ContaBancaria conta, Integer numeroJdeInterno) throws ExcecaoValidacao;
}
