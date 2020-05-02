
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contaContabilGeral", propOrder = {
    "contaObjeto",
    "centroCusto",
    "detalhe",
    "subConta"
})
public class ContaContabilGeral {

    protected String contaObjeto;
    protected String centroCusto;
    protected String detalhe;
    protected String subConta;

    
    public String getContaObjeto() {
        return contaObjeto;
    }

    
    public void setContaObjeto(String value) {
        this.contaObjeto = value;
    }

    
    public String getCentroCusto() {
        return centroCusto;
    }

    
    public void setCentroCusto(String value) {
        this.centroCusto = value;
    }

    
    public String getDetalhe() {
        return detalhe;
    }

    
    public void setDetalhe(String value) {
        this.detalhe = value;
    }

    
    public String getSubConta() {
        return subConta;
    }

    
    public void setSubConta(String value) {
        this.subConta = value;
    }

}
