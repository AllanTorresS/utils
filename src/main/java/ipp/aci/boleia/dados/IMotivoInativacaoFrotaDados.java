package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.MotivoInativacaoFrota;

import java.util.List;

/**
 * Contrato para implementacao de repositorios dos motivo de inativacao e ativacao da Frota
 */
public interface IMotivoInativacaoFrotaDados extends IRepositorioBoleiaDados<MotivoInativacaoFrota> {

    /**
     * Busca os motivos de alteracao da frota
     * @param idFrota identificador da frota
     * @return Motivos de alteracao
     */
    List<MotivoInativacaoFrota> buscarMotivosDaFrota(Long idFrota);

    /**
     * Busca o motivo atual de inativacao da frota
     * @param idFrota identificador da frota
     * @return Motivo de inativacao atual
     */
    MotivoInativacaoFrota buscarMotivoInativacaoAtualFrota(Long idFrota);

    /**
     * Busca o motivo atual de reativacao da frota
     * @param idFrota identificador da frota
     * @return Motivo de reativacao atual
     */
    MotivoInativacaoFrota buscarMotivoReativacaoAtualFrota(Long idFrota);
}
