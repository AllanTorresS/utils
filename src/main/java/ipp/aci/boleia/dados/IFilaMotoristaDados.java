package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.Motorista;

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
     * @param idMotorista O id motorista a ter os dados anonimizados e excluídos da auditoria
     * @param idMotoristaAnonimo O id motorista anônimo a anonimizar os dados do motorista
     * @param idUsuarioMotorista O id usuario motorista a ter os dados anonimizados e excluídos da auditoria
     * @param idUsuario O id usuario a ter os dados anonimizados e excluídos da auditoria
     */
    void enviarMotoristaParaFilaDeAnonimizacaoExclusaoAuditoria(Long idMotorista, Long idMotoristaAnonimo, Long idUsuarioMotorista, Long idUsuario);
}
