
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "aprovador", propOrder = {
    "codigo",
    "numeroCadastro",
    "cnpj"
})
public class Aprovador {

    protected Long codigo;
    protected String numeroCadastro;
    protected String cnpj;

    
    public Long getCodigo() {
        return codigo;
    }

    
    public void setCodigo(Long value) {
        this.codigo = value;
    }

    
    public String getNumeroCadastro() {
        return numeroCadastro;
    }

    
    public void setNumeroCadastro(String value) {
        this.numeroCadastro = value;
    }

    
    public String getCnpj() {
        return cnpj;
    }

    
    public void setCnpj(String value) {
        this.cnpj = value;
    }

}
