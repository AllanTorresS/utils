package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.Frota;
import org.apache.commons.lang.StringUtils;

/**
 * Classe com informacoes relacionadas ao cliente com pedido na Mundipagg
 */
public class MundipaggClientePedidoVo {

    private static final String COMPANY_CUSTOMER_TYPE = "company";
    private static final int LIMITE_NOME_CLIENTE = 64;

    private String name;
    private String email;
    private String document;
    private String code;
    private String type;

    public MundipaggClientePedidoVo() {
    }

    /**
     * Construtor do cliente com pedido na Mundipagg
     * @param frota A frota com pedido
     */
    public MundipaggClientePedidoVo(Frota frota) {
        name = frota.getRazaoSocial().length() > LIMITE_NOME_CLIENTE
                ? frota.getRazaoSocial().substring(0,LIMITE_NOME_CLIENTE)
                : frota.getRazaoSocial();
        email = frota.getEmail();
        document = StringUtils.leftPad(frota.getCnpj().toString(), 14, "0");
        code = frota.getId().toString();
        type = COMPANY_CUSTOMER_TYPE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
