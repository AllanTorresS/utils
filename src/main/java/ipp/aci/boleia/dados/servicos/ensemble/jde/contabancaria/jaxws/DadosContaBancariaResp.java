
package ipp.aci.boleia.dados.servicos.ensemble.jde.contabancaria.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dadosContaBancariaResp", propOrder = {
    "mensagemRetorno",
    "numeroContaBancaria"
})
public class DadosContaBancariaResp
    extends V2
{

    protected String mensagemRetorno;
    protected BigDecimal numeroContaBancaria;

    
    public String getMensagemRetorno() {
        return mensagemRetorno;
    }

    
    public void setMensagemRetorno(String value) {
        this.mensagemRetorno = value;
    }

    
    public BigDecimal getNumeroContaBancaria() {
        return numeroContaBancaria;
    }

    
    public void setNumeroContaBancaria(BigDecimal value) {
        this.numeroContaBancaria = value;
    }

}
