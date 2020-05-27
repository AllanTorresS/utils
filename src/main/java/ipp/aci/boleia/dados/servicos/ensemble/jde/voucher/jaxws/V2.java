
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "v2", propOrder = {
    "statusIntegracao"
})
@XmlSeeAlso({
    BuscarResp.class,
    CriarResp.class
})
public class V2
    extends EnsResponse
{

    protected StatusIntegracao statusIntegracao;

    
    public StatusIntegracao getStatusIntegracao() {
        return statusIntegracao;
    }

    
    public void setStatusIntegracao(StatusIntegracao value) {
        this.statusIntegracao = value;
    }

}
