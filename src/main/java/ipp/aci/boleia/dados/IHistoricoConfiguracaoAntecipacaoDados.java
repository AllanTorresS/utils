package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.HistoricoConfiguracaoAntecipacao;

/**
 * Interface do repositório da entidade {@link HistoricoConfiguracaoAntecipacao}
 */
public interface IHistoricoConfiguracaoAntecipacaoDados extends IRepositorioBoleiaDados<HistoricoConfiguracaoAntecipacao> {

    /**
     * Obtém o registro mais recente do histórico de configuração
     * @return o registro de configuração
     */
    HistoricoConfiguracaoAntecipacao obterRegistroMaisRecente();
}
