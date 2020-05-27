
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "chaveConta", propOrder = {
    "identificacaoDaConta",
    "identificacaoCompletaDaConta",
    "contaAlternativa"
})
public class ChaveConta {

    protected String identificacaoDaConta;
    protected String identificacaoCompletaDaConta;
    protected String contaAlternativa;

    
    public String getIdentificacaoDaConta() {
        return identificacaoDaConta;
    }

    
    public void setIdentificacaoDaConta(String value) {
        this.identificacaoDaConta = value;
    }

    
    public String getIdentificacaoCompletaDaConta() {
        return identificacaoCompletaDaConta;
    }

    
    public void setIdentificacaoCompletaDaConta(String value) {
        this.identificacaoCompletaDaConta = value;
    }

    
    public String getContaAlternativa() {
        return contaAlternativa;
    }

    
    public void setContaAlternativa(String value) {
        this.contaAlternativa = value;
    }

}
