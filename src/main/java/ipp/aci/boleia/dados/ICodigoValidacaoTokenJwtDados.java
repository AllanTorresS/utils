package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.CodigoValidacaoTokenJwt;
import ipp.aci.boleia.dominio.Usuario;

/**
 * Repositório de dados do objeto {@link CodigoValidacaoTokenJwt}.
 *
 * @author pedro.silva
 */
public interface ICodigoValidacaoTokenJwtDados extends IRepositorioBoleiaDados<CodigoValidacaoTokenJwt> {

    /**
     * Obtém um objeto {@link CodigoValidacaoTokenJwt} a partir de um usuário.
     *
     * @param usuario Usuario utilizado na busca
     * @return Objeto encontrado.
     */
    CodigoValidacaoTokenJwt obterPorUsuario(Usuario usuario);

    /**
     * excluir um objeto {@link CodigoValidacaoTokenJwt} a partir de um usuário.
     *
     * @param usuario Usuario utilizado para deletar o token
     */
    void excluirPorUsuario(Usuario usuario);
}
