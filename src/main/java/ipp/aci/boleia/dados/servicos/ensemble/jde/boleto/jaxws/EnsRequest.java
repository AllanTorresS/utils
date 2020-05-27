
package ipp.aci.boleia.dados.servicos.ensemble.jde.boleto.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Ens_Request")
@XmlSeeAlso({
    RecuperarBoletoReq.class,
    BuscarReq.class,
    GerarPDFReq.class
})
public class EnsRequest
    extends EnsMessagebody
{


}
