package ipp.aci.boleia.dados.servicos.ensemble.jde;

import ipp.aci.boleia.dados.servicos.ensemble.ServicoSoapDados;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Base para implementacao de repositorios de acesso ao JDE
 *
 * @param <S> O tipo do servico SOAP a ser invocado
 */
@Component
public abstract class JdeBaseDados<S> extends ServicoSoapDados<S> {

    @Value("${jde.web.service.login}")
    private String jdeLogin;

    @Value("${jde.web.service.senha}")
    private String jdeSenha;

    @Autowired
    private UtilitarioAmbiente utilitarioAmbiente;

    @Autowired
    private Mensagens mensagens;

    public String getJdeLogin() {
        return jdeLogin;
    }

    public String getJdeSenha() {
        return jdeSenha;
    }

    public UtilitarioAmbiente getUtilitarioAmbiente() {
        return utilitarioAmbiente;
    }

    public Mensagens getMensagens() {
        return mensagens;
    }
}
