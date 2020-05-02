package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;

/**
 * Contrato para implementacao de reposiórios de Pessoa do JDE.
 */
public interface IPessoaDados {

    /**
     * Servico responsavel por cadastrar um novo Ponto de venda no JDE
     *
     * @param pontoDeVenda Ponto de venda a ser salvo
     * @param usuarioGestor Usuario gestor da Revenda
     * @return Codigo do novo cadastro JDE
     * @throws ExcecaoValidacao Quando ocorre algum erro na integração do JDE
     */
    Long cadastro(PontoDeVenda pontoDeVenda, Usuario usuarioGestor) throws ExcecaoValidacao;

    /**
     * Servico responsavel por cadastrar uma nova Frota no JDE
     *
     * @param frota Frotaa ser salva
     * @param usuarioGestor Usuario gestor da Frota
     * @return Codigo do novo cadastro JDE
     * @throws ExcecaoValidacao Quando ocorre algum erro na integração do JDE
     */
    Long cadastro(Frota frota, Usuario usuarioGestor) throws ExcecaoValidacao;

    /**
     * Servico responsavel por atualizar um cadastro de Ponto de venda no JDE
     *
     * @param pontoDeVenda Ponto de venda a ser atualizado
     * @param usuarioGestor Usuario gestor da Revenda
     * @return Codigo do cadastro no JDE
     * @throws ExcecaoValidacao Quando ocorre algum erro na integração do JDE
     */
    Long atualizar(PontoDeVenda pontoDeVenda, Usuario usuarioGestor) throws ExcecaoValidacao;

    /**
     * Servico responsavel por atualizar um cadastro de Frota no JDE
     *
     * @param frota Frota a ser atualizada
     * @param usuarioGestor Usuario gestor da Frota
     * @throws ExcecaoValidacao Quando ocorre algum erro na integração do JDE
     */
    void atualizar(Frota frota, Usuario usuarioGestor) throws ExcecaoValidacao;

    /**
     * Servico responsavel por inativar um cadastro de Frota no JDE
     *
     * @param frota Frota a ser inativada
     * @throws ExcecaoValidacao Quando ocorre algum erro na integração do JDE
     */
    void excluir(Frota frota) throws ExcecaoValidacao;
}