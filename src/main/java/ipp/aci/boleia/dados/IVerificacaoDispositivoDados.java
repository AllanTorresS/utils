package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.DispositivoMotorista;
import ipp.aci.boleia.dominio.Motorista;
import ipp.aci.boleia.dominio.UsuarioMotorista;

/**
 * Contrato para implementacao de repositorio de verificacao de dispositivos
 */
public interface IVerificacaoDispositivoDados {

    /**
     * Cria/Atualiza um motorista, excluindo todos os devices que estiverem associados a esse usuário
     *
     * @param motorista o motorista cadastrado
     */
    void inserirMotorista(Motorista motorista);

    /**
     * Cria um device com as capacidades otp e sms para o motorista dono do dispositivo
     *
     * @param dispositivo o dispositivo a ser inserido
     */
    void inserirDispositivo(DispositivoMotorista dispositivo);

    /**
     * Cria/Atualiza um motorista, excluindo todos os devices que estiverem associados a esse usuário
     *
     * @param motorista o motorista cadastrado
     */
    void cadastrarUsernameMotorista(Motorista motorista);

    /**
     * Cria um device com as capacidades otp e sms para o motorista dono do dispositivo
     *
     * @param dispositivo o dispositivo a ser inserido
     */
    void cadastrarTokenDispositivoMotorista(DispositivoMotorista dispositivo);

    /**
     * Cria/Atualiza um motorista, excluindo todos os devices que estiverem associados a esse usuário
     *
     * @param usuarioMotorista Usuario do motorista
     */
    void cadastrarUsernameUsuarioMotorista(UsuarioMotorista usuarioMotorista);

    /**
     * Cadastra o token do allow me para o usuário de um motorista.
     *
     * @param usuarioMotorista Usuario do motorista
     * @param motorista o motorista
     */
    void cadastrarTokenUsuarioMotorista(UsuarioMotorista usuarioMotorista, Motorista motorista);

    /**
     * Realiza a ativação do OTP para um usuário motorista.
     *
     * @param usuarioMotorista O usuario motorista
     */
    void ativarOtpUsuarioMotorista(UsuarioMotorista usuarioMotorista);

    /**
     * Valida um token para dado dispositivo no momento de autorização de transação
     * @param motorista motorista do dispositivo
     * @param codigoAbastecimento codigoAbastecimento informado pelo motorista
     * @return True para token valido
     */
    Boolean validarTokenDispositivo(Motorista motorista, String codigoAbastecimento);

    /**
     * Valida um token para dado usuário motorista no momento de autorização de transação
     * @param cpf Cpf do usuário motorista
     * @param codigoAbastecimento codigoAbastecimento informado pelo motorista
     * @return True para token valido
     */
    Boolean validarTokenUsuarioMotorista(String cpf, String codigoAbastecimento);

    /**
     * Exclui um device cadastrado para um motorista
     *
     * @param dispositivo o dispositivo a ser excluído
     */
    void excluirDispositivo(DispositivoMotorista dispositivo);

    /**
     * Exclui um device cadastrado para um motorista
     *
     * @param usuarioMotorista o dispositivo a ser excluído
     * @param motorista o motorista do dispositivo
     */
    void excluirDispositivoAllowMe(UsuarioMotorista usuarioMotorista, Motorista motorista);

    /**
     * Retorna o userName utilizado para a integração com o AllowMe
     *
     * @param motorista o motorista dono do dispositivo
     * @return o userName
     */
    String montarMotoristaUsername(Motorista motorista);

    /**
     * Retorna o username utilizado para a integracao com o AlloMe
     *
     * @param cpf Cpf do motorista
     * @return username
     */
    String montarUsuarioUsername(String cpf);

    /**
     * cadastra o usuario e o dispositivo no momento de criação do pedido
     *
     * @param usuarioMotorista entidade do usuarioMotorista
     * @param motorista entidade de Motorista
     */
    void cadastrarUsernameUsuarioETokenDispositivoMotorista(UsuarioMotorista usuarioMotorista, Motorista motorista);

    /**
     * Busca o perfil do allow me para o usuário de um motorista, e o seu respectivo dispositivo
     *
     * @param usuarioMotorista Usuario do motorista
     * @return true se o usuario ja possuir cadastro
     */
    boolean buscarCadastroUsuarioAllowMe(UsuarioMotorista usuarioMotorista);

}
