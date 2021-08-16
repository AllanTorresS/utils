package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.Motorista;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.UsuarioMotorista;

/**
 * Contrato para envio de transações para consolidação
 */
public interface IFilaMotoristaDados {

    /**
     * Envia o {@link Motorista} para a fila de anonimização
     *
     * @param motorista O motorista a ser anonimizado
     */
    void enviarMotoristaParaFilaDeAnonimizacao(Motorista motorista);

    /**
     * Envia o {@link Motorista} para a fila de anonimização e exclusão de dados da auditoria
     *
     * @param motorista O motorista a ter os dados anonimizados e exlcuidos da auditoria
     * @param motoristaAnonimo O motorista anônimo a anonimizar os dados do motorista
     * @param usuarioMotorista O usuario motorista a ter os dados anonimizados e exlcuidos da auditoria
     * @param usuario O usuario a ter os dados anonimizados e exlcuidos da auditoria
     */
    void enviarMotoristaParaFilaDeAnonimizacaoExclusaoAuditoria(Motorista motorista, Motorista motoristaAnonimo, UsuarioMotorista usuarioMotorista, Usuario usuario);
}
