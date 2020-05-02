
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dadosFornecedor", propOrder = {
    "codFornecedor",
    "codLongoFornecedor",
    "cpfCnpjFornecedor"
})
public class DadosFornecedor {

    protected Long codFornecedor;
    protected String codLongoFornecedor;
    protected String cpfCnpjFornecedor;

    
    public Long getCodFornecedor() {
        return codFornecedor;
    }

    
    public void setCodFornecedor(Long value) {
        this.codFornecedor = value;
    }

    
    public String getCodLongoFornecedor() {
        return codLongoFornecedor;
    }

    
    public void setCodLongoFornecedor(String value) {
        this.codLongoFornecedor = value;
    }

    
    public String getCpfCnpjFornecedor() {
        return cpfCnpjFornecedor;
    }

    
    public void setCpfCnpjFornecedor(String value) {
        this.cpfCnpjFornecedor = value;
    }

}
