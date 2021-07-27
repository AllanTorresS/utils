package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.Motorista;

/**
 * Contrato para envio de transações para consolidação
 */
public interface IFilaMotoristaDados {

    /**
     * Envia o {@link Motorista} para a fila de exclusão
     *
     * @param motorista O motorista a ser excluído
     */
    void enviarMotoristaParaFilaDeExclusao(Motorista motorista);
}
