package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.TipoPerfilUsuario;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaUsuarioVo;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades Usuario
 */
public interface IUsuarioDados extends IRepositorioBoleiaDados<Usuario> {

    /**
     * Pesquisa Usuarios a partir do filtro informado
     *
     * @param filtro O filtro da busca
     * @return Uma lista de entidades localizadas
     */
    ResultadoPaginado<Usuario> pesquisaPaginada(FiltroPesquisaUsuarioVo filtro);

    /**
     * Localiza um usuário a partir do seu CPF
     *
     * @param cpf O CPF do usuário
     * @param usuarioMotorista Indica se o usuário é do tipo motorista.
     * @return O usuário encontrado
     */
    Usuario obterPorCpf(Long cpf, Boolean usuarioMotorista);

    /**
     * Localiza um usuário a partir do seu CPF e tipo de perfil.
     *
     * @param cpf O CPF do usuário
     * @param tipoPerfilId Identificador do tipo de perfil do usuario.
     * @return O usuário encontrado
     */
    Usuario obterPorCpfTipoPerfil(Long cpf, Long tipoPerfilId);

    /**
     * Localiza o usuario que possui um dado login
     *
     * @param login O login
     * @return O usuario para dado login
     */
    Usuario obterPorLogin(String login);

    /**
     * Localiza o usuario que possui um dado email
     *
     * @param email O email
     * @return O usuario do email
     */
    Usuario obterPorEmail(String email);

    /**
     * Localiza o usuario que possui um dado token
     *
     * @param token token do usuario
     * @return O usuario do token
     */
    Usuario obterPorToken(String token);

    /**
     * Obtem uma lista de usuarios para dado perfil e tipo gestor
     *
     * @param perfil Perfil do usuario
     * @param isGestor Se usuario deve ser gestor
     * @return Lista de usuarios encontrados
     */
    List<Usuario> obterPorTipoPerfilGestor(TipoPerfilUsuario perfil, Boolean isGestor);

    /**
     * Obtem a lista de usuarios gestores de uma frota
     *
     * @param idFrota id da Frota
     * @return Usuarios gestores da frota
     */
    List<Usuario> obterGestorPorFrota(Long idFrota);

    /**
     * Obtém a lista de usuários gestores de um rede
     *
     * @param idRede id da Rede
     * @return Usuários gestores da Rede
     */
    List<Usuario> obterGestorPorRede(Long idRede);

    /**
     * Obtém a lista de usuários gestores das redes
     *
     * @param idsRedes ids das Redes
     * @return Usuários gestores das Redes
     */
    List<Usuario> obterGestorPorRedes(List<Long> idsRedes);

    /**
     * Obtém a lista de usuários de uma rede
     *
     * @param idRede id da Rede
     * @return Usuários da Rede
     */
    List<Usuario> obterUsuariosPorRede(Long idRede);

    /**
     * Obtem a lista de usuarios de um dado tipo de perfil e que possuam uma dada permissão
     *
     * @param idTipoPerfil o id do tipo de perfil
     * @param chavePermissao A chave da permissao
     * @return A lista de usuarios encontrados
     */
    List<Usuario> obterPorTipoPerfilPermissao(Long idTipoPerfil, String chavePermissao);

    /**
     * Obtem a lista de usuarios de uma dada Frota que possuam as permissoes informadas
     *
     * @param idFrota o id da frota desejada
     * @param chavesPermissao As chaves de permissao desejadas
     * @return A lista de usuarios encontrados
     */
    List<Usuario> obterPorFrotaPermissoes(Long idFrota, String... chavesPermissao);

    /**
     * Obtem a lista de usuarios associados a um dado ponto de venda que possuam as permissoes informadas
     *
     * @param idPtov o id do ponto de venda desejado
     * @param chavesPermissao As chaves de permissao desejadas
     * @return A lista de usuarios encontrados
     */
    List<Usuario> obterPorPontoVendaPermissoes(Long idPtov, String... chavesPermissao);

    /**
     * Desvincula os usuarios das unidades informadas
     * @param unidadeId os id da unidade em questão
     */
    void desvincularUnidades(Long unidadeId);

    /**
     * Obtem todos os usuarios de uma frota
     * @param id da frota
     * @return Usuarios da frota
     */
    List<Usuario> obterPorFrota(Long id);
}
