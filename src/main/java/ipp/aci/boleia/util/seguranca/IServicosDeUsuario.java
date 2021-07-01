package ipp.aci.boleia.util.seguranca;

import ipp.aci.boleia.dominio.Usuario;

/**
 * Interface para implementação da classe de serviços do usuário.
 */
public interface IServicosDeUsuario {

    /**
     * Localiza o usuario que possui um dado cpf. Carrega os relacionamentos
     * com perfil e permissoes.
     *
     * @param cpf O cpf.
     * @param usuarioMotorista Indica se o usuário é do tipo motorista.
     * @return Um usuario ou nulo caso nenhum encontrado.
     */
    Usuario obterPorCpfComPermissoes(String cpf, Boolean usuarioMotorista);

    /**
     * Localiza o usuario que possui um dado login. Carrega os relacionamentos
     * com perfil e permissoes.
     *
     * @param login O login
     * @return Um usuario ou nulo caso nenhum encontrado.
     */
    Usuario obterPorLoginComPermissoes(String login);

    /**
     * Localiza o usuario que possui um dado email. Carrega os relacionamentos
     * com perfil e permissoes.
     *
     * @param email O email
     * @return Um usuario ou nulo caso nenhum encontrado.
     */
    Usuario obterPorEmailComPermissoes(String email);

    /**
     * Verifica se o usuario esta bloqueado temporariamente por ter excedido o limite de tentativa
     * frustradas de login na aplicacao.
     * @param idUsuario identificador do usuario
     * @return se possui ou nao bloqueio temporario
     */
    boolean possuiBloqueioTemporario(Long idUsuario);

    /**
     * Registra uma tentativa bem sucedida de autenticacao
     * @param idUsuario identificador do usuario
     * @return True para primeiro acesso
     */
    Boolean registrarSucessoAutenticacao(Long idUsuario);

    /**
     * Registra uma tentativa frustrada de autenticacao
     *
     * @param idUsuario O id do usuario
     * @return true caso o usuario esteja bloqueado temporariamente
     */
    boolean registrarErroAutenticacao(Long idUsuario);

    /**
     * Localiza o usuario por id. Carrega os relacionamentos
     * com perfil e permissoes.
     *
     * @param id O id
     * @return Um usuario ou nulo caso nenhum encontrado.
     */
    Usuario obterPorIdComPermissoes(long id);

    /**
     * Povoa o objeto usuario com as permissoes localizadas para ele
     *
     * @param usuario O usuario
     * @return O usuario com as permissoes pertinentes
     */
    Usuario povoarPermissoesUsuario(Usuario usuario);

    /**
     * Retorna true caso as credenciais informadas estejam compativeis com aquelas armazenadas em banco de dados
     *
     * @param usuario o usuario localizado no banco de dados
     * @param senha A senha informada pelo usuario
     * @return True caso as credenciais estejam de acordo com o esperado
     */
    boolean credenciaisValidas(Usuario usuario, String senha);

    /**
     * Carrega o usuário por id sem isolamento. Carrega os relacionamentos
     * com perfil e permissões
     * @param id Id do usuário
     * @return O usuário ou nulo caso nenhum encontrado
     */
    Usuario obterPorIdSemIsolamentoComPermissoes(long id);

    /**
     * Desvincular os perfis temporários já expirados para um usuário.
     *
     * @param usuario usuário que terá os perfis desvinculados.
     */
    Usuario desvincularPerfisTemporariosExpirados(Usuario usuario);
}
