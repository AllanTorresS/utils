package ipp.aci.boleia.dados.servicos.salesforce;

import ipp.aci.boleia.dados.IChamadoDados;
import ipp.aci.boleia.dados.IClienteHttpDados;
import ipp.aci.boleia.dados.IMotivoChamadoDados;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Respositorio para abertura de chamados Salesforce
 */
@Repository
public class SalesForceChamadoDados implements IChamadoDados {

    private static final Logger LOGGER = LoggerFactory.getLogger(SalesForceChamadoDados.class);

    private static final String ALIAS_ORGID = "orgid";
    private static final String ALIAS_COMPANY = "company";
    private static final String ALIAS_NAME = "name";
    private static final String ALIAS_EMAIL = "email";
    private static final String ALIAS_PHONE = "phone";
    private static final String ALIAS_REASON = "00N0v0000018AKZ";
    private static final String ALIAS_SUBJECT = "subject";
    private static final String ALIAS_DESCRIPTION = "description";

    private static final int LIMITE_DEFAULT = 80;
    private static final int LIMITE_PHONE = 15;
    private static final int LIMITE_DESCRIPTION = 1024;

    @Value("${salesforce.chamado.orgid}")
    private String orgid;

    @Value("${salesforce.chamado.url}")
    private String endereco;

    @Autowired
    private IClienteHttpDados restDados;

    @Autowired
    private IMotivoChamadoDados motivoChamadoDados;

    @Override
    public boolean abrirChamado(String company ,String name, String email, String phone, Long idReason, String subject, String description) {

        Map<String, String> form = new LinkedHashMap<>();
        form.put(ALIAS_ORGID, orgid);
        form.put(ALIAS_COMPANY, limitar(company, LIMITE_DEFAULT));
        form.put(ALIAS_NAME, limitar(name, LIMITE_DEFAULT));
        form.put(ALIAS_EMAIL, limitar(email, LIMITE_DEFAULT));
        form.put(ALIAS_PHONE, limitar(phone, LIMITE_PHONE));
        form.put(ALIAS_REASON, motivoChamadoDados.obterPorId(idReason).getDescricao());
        form.put(ALIAS_SUBJECT, limitar(subject, LIMITE_DEFAULT));
        form.put(ALIAS_DESCRIPTION, limitar(description, LIMITE_DESCRIPTION));

        try {
            return restDados.doPostFormEncoded(endereco, form, resp -> resp.getStatusLine().getStatusCode() == 200);
        }catch (Exception ex){
            LOGGER.error(ex.getMessage(), ex);
            return false;
        }
    }

    /**
     * Limita um campo a ser enviado no chamado
     *
     * @param campo O valor do campo
     * @param limite O limite de caracteres desejado
     * @return O campo limitado, caso necessario
     */
    private String limitar(String campo, int limite) {
        if(campo != null && campo.length() > limite) {
            campo = campo.substring(0, limite);
        }
        return campo;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
