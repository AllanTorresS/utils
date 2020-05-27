
package ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Ens_Response")
@XmlSeeAlso({
    ConsultarResp.class,
    CriarEmLoteResp.class,
    LiberarResp.class
})
public class EnsResponse
    extends EnsMessagebody
{


}
