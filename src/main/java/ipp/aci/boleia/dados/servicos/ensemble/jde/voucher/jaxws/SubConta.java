
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "subConta", propOrder = {
    "subConta",
    "tipoSubConta"
})
public class SubConta {

    protected String subConta;
    protected String tipoSubConta;

    
    public String getSubConta() {
        return subConta;
    }

    
    public void setSubConta(String value) {
        this.subConta = value;
    }

    
    public String getTipoSubConta() {
        return tipoSubConta;
    }

    
    public void setTipoSubConta(String value) {
        this.tipoSubConta = value;
    }

}
