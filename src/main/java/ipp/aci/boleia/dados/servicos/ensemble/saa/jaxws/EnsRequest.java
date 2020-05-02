
package ipp.aci.boleia.dados.servicos.ensemble.saa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Ens_Request")
@XmlSeeAlso({
    AutorizacaoReq.class,
    AutenticacaoUsuarioReq.class
})
public class EnsRequest
    extends EnsMessagebody
{


}
