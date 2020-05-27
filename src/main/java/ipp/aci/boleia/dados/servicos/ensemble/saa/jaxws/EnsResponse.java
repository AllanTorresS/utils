
package ipp.aci.boleia.dados.servicos.ensemble.saa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Ens_Response")
@XmlSeeAlso({
    AutenticacaoUsuarioResp.class,
    AutorizacaoResp.class
})
public class EnsResponse
    extends EnsMessagebody
{


}
