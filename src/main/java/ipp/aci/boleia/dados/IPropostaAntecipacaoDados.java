package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.PropostaAntecipacao;

/**
 * Contrato de implementação de repositório de Proposta Amtecipacao
 */
public interface IPropostaAntecipacaoDados extends IRepositorioBoleiaDados<PropostaAntecipacao> {

    PropostaAntecipacao obterPorIdParceiro(String idProposta);
}
