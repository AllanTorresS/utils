package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.MotivoClonagem;

import java.util.List;

/**
 * Contrato para implementacao de repositorios
 * de entidades de motivo de clonagem
 */
public interface IMotivoClonagemDados extends IRepositorioBoleiaDados<MotivoClonagem> {

    /**
     * Pesquisa MotivoClonagem a partir do filtro informado
     *
     * @return Uma lista de entidades localizadas
     */
    List<MotivoClonagem> obterMotivosClonagemInterno();
}
