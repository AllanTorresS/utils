package ipp.aci.boleia.dados.servicos.ensemble.saa;

import ipp.aci.boleia.dados.IAutenticacaoUsuarioDados;
import ipp.aci.boleia.dominio.enums.TipoPerfilUsuario;
import ipp.aci.boleia.util.excecao.ExcecaoAutenticacaoRemota;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

/**
 * Bean para simulacao da comunicacao com o SAA. Utilizado apenas em ambiente de desenvolvimento,
 * quando a propriedade <code>saa.web.service.enabled</code> for configurada com o valor <code>false</code>.
 *
 * Util para para evitar que indisponibilidades no servico ou na rede afetem a produtividade nas
 * tarefas de construcao e teste.
 */
@Repository
@ConditionalOnProperty(name="saa.web.service.enabled", havingValue = "false")
public class SimuladorSaaAutenticacaoUsuarioDados implements IAutenticacaoUsuarioDados {

    @Override
    public boolean autenticar(String login, String senha) throws ExcecaoAutenticacaoRemota {
        return true;
    }

    @Override
    public boolean possuiPerfil(String login, TipoPerfilUsuario perfil) throws ExcecaoAutenticacaoRemota {
        return true;
    }
}
