package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.EntidadeRepasse;

/**
 * Contrato de implementação de repositorios de entidades EntidadeRepasse
 */
public interface IEntidadeRepasseDados extends IRepositorioBoleiaDados<EntidadeRepasse> {

    /**
     * Obtém a {@link EntidadeRepasse} que representa a Ipiranga
     *
     * @return A entidade de repasse da Ipiranga
     */
    EntidadeRepasse obtemEntidadeDeRepassePadrao();
}
