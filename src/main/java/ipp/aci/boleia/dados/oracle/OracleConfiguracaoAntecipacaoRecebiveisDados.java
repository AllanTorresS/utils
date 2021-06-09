package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IConfiguracaoAntecipacaoRecebiveisDados;
import ipp.aci.boleia.dominio.ConfiguracaoAntecipacaoRecebiveis;
import org.springframework.stereotype.Repository;

/**
 * Implementação do repositório da entidade {@link ConfiguracaoAntecipacaoRecebiveis}
 */
@Repository
public class OracleConfiguracaoAntecipacaoRecebiveisDados extends OracleRepositorioBoleiaDados<ConfiguracaoAntecipacaoRecebiveis> implements IConfiguracaoAntecipacaoRecebiveisDados {

    /**
     * Instancia o repositório
     */
    public OracleConfiguracaoAntecipacaoRecebiveisDados() {
        super(ConfiguracaoAntecipacaoRecebiveis.class);
    }

    @Override
    public ConfiguracaoAntecipacaoRecebiveis obterConfiguracaoVigente() {
        return pesquisarUnico();
    }
}
