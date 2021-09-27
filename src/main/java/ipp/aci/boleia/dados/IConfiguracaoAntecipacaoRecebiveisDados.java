package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.ConfiguracaoAntecipacaoRecebiveis;

/**
 * Interface para manipulação do repositório de parâmetros do programa de antecipação de recebíveis
 */
public interface IConfiguracaoAntecipacaoRecebiveisDados extends IRepositorioBoleiaDados<ConfiguracaoAntecipacaoRecebiveis> {

    /**
     * Obtém a configuração atual do programa de antecipação de recebíveis
     *
     * @return configuração vigente do programa de antecipação
     */
    ConfiguracaoAntecipacaoRecebiveis obterConfiguracaoVigente();
}
