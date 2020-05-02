
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fornecedor", propOrder = {
    "codigo",
    "numeroCadastrato",
    "cnpj"
})
public class Fornecedor {

    protected Long codigo;
    protected String numeroCadastrato;
    protected String cnpj;

    
    public Long getCodigo() {
        return codigo;
    }

    
    public void setCodigo(Long value) {
        this.codigo = value;
    }

    
    public String getNumeroCadastrato() {
        return numeroCadastrato;
    }

    
    public void setNumeroCadastrato(String value) {
        this.numeroCadastrato = value;
    }

    
    public String getCnpj() {
        return cnpj;
    }

    
    public void setCnpj(String value) {
        this.cnpj = value;
    }

}
