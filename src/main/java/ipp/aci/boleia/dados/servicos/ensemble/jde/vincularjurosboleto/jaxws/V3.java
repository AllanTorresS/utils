
package ipp.aci.boleia.dados.servicos.ensemble.jde.vincularjurosboleto.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "v3", namespace = "http://ipiranga.com.br/statusIntegracao", propOrder = {
    "statusIntegracao"
})
@XmlSeeAlso({
    Response.class
})
public class V3
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
