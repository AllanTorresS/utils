
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Ens_Request")
@XmlSeeAlso({
    MarcarTituloASerBaixadoReq.class,
    GerarJDEReq.class,
    MarcarTituloASerBaixadoLoteReq.class,
    DemostrativoAbatimentoCreditoCombReq.class,
    InserirEmLoteJDEReq.class,
    IncluirJDEReq.class,
    VerificarTituloPagoReq.class,
    ConsultarJDEReq.class
})
public class EnsRequest
    extends EnsMessagebody
{


}
