package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.enums.TipoPerfilUsuario;
import ipp.aci.boleia.util.excecao.ExcecaoAutenticacaoRemota;

/**
 * Contrato para implementacao de reposiórios de autenticacoa de usuarios.
 */
public interface IAutenticacaoUsuarioDados {

    /**
     * Autentica um usuatio
     * @param login o login
     * @param senha a senha
     * @return True caso autenticado com sucesso
     * @throws ExcecaoAutenticacaoRemota em caso de falha de comunicacao com o provedor de autenticacao
     */
    boolean autenticar(String login, String senha) throws ExcecaoAutenticacaoRemota;

    /**
     * Verifica se o usuario em questao possui um dado perfil
     *
     * @param login o login do usuario em questao
     * @param perfilEsperado O perfil desejado
     * @return true caso possua o perfil
     * @throws ExcecaoAutenticacaoRemota em caso de falha de comunicacao com o provedor de autenticacao
     */
    boolean possuiPerfil(String login, TipoPerfilUsuario perfilEsperado) throws ExcecaoAutenticacaoRemota;
}