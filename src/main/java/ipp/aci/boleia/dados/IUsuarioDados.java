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
     * Obtém um usuário frotista a partir do CPF e CNPJ.
     *
     * @param cpfUsuario CPF do usuário.
     * @param cnpjFrota CNPJ da frota
     * @return usuário frotista encontrado.
     */
    Usuario obterFrotistaPorCpfECnpj(Long cpfUsuario, Long cnpjFrota);

    /**
     * Obtém um usuário revendedor a partir do CPF e CNPJ.
     *
     * @param cpfUsuario CPF do usuário.
     * @param cnpjRevenda CNPJ da revenda
     * @return usuário revendedor encontrado.
     */
    Usuario obterRevendedorPorCpfECnpj(Long cpfUsuario, Long cnpjRevenda);

    /**
     * Obtém um usuário interno a partir do CPF.
     *
     * @param cpfUsuario CPF do usuário.
     * @return usuário interno encontrado.
     */
    Usuario obterInternoPorCpf(Long cpfUsuario);

    /**
     * Pesquisa Usuarios a partir do filtro informado
     *
     * @param filtro O filtro da busca
     * @return Uma lista de entidades localizadas
     */
    ResultadoPaginado<Usuario> pesquisaPaginada(FiltroPesquisaUsuarioVo filtro);

    /**
     * Pesquisa Usuarios a partir do filtro informado, sem isolamento de dados
     * @param filtro O filtro da busca
     * @return Uma lista de entidades localizadas
     */
    ResultadoPaginado<Usuario> pesquisaPaginadaSemIsolamento(FiltroPesquisaUsuarioVo filtro);

    /**
     * Pesquisa um usuario pelo nome
     *
     * @param filtro o filtro que contém o nome desejado
     * @return Lista de usuarios encontrados
     */
    List<Usuario> pesquisarPorNome(FiltroPesquisaUsuarioVo filtro);

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
     * Localiza o usuario que possui um dado email e tipo de perfil especificado
     *
     * @param email O email
     * @param tipoPerfil O tipo de perfil
     * @return O usuario encontrado
     */
    Usuario obterPorEmailTipoPerfil(String email, TipoPerfilUsuario tipoPerfil);

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

    /**
     * Obtem quantidade total de usuarios ativos
     * @return Quantidade total
     */
    Long obterQuantidadeTotal();

    /**
     * Obtem quantidade total de usuarios ativos de um tipo de perfil
     * @param tipoPerfilUsuario Tipo de perfil do usuário
     * @return Quantidade total
     */
    Long obterQuantidadeTotalAtivosDeTipoPerfil(TipoPerfilUsuario tipoPerfilUsuario);

    /**
     * Obtem todos os asessores de uma coordenadoria.
     * @param id da coordenadoria
     * @return Assessores da coordenadoria
     */
    List<Usuario> obterPorCoordenadoria(final Long id);

    /**
     * Obtém o total de usuários que são donos de frota
     *
     * @return Quantidade total de usuários
     */
    Long obterTotalUsuariosDonosFrota();
}
