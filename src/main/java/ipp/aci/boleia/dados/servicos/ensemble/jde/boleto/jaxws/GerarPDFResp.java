
package ipp.aci.boleia.dados.servicos.ensemble.jde.boleto.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GerarPDFResp", propOrder = {
    "pdf"
})
public class GerarPDFResp
    extends V2
{

    protected byte[] pdf;

    
    public byte[] getPdf() {
        return pdf;
    }

    
    public void setPdf(byte[] value) {
        this.pdf = value;
    }

}
