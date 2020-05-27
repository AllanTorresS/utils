
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Ens_Response")
@XmlSeeAlso({
    GerarJDEResp.class,
    MarcarTituloASerBaixadoResp.class,
    VerificarTituloPagoResp.class,
    DemostrativoAbatimentoCreditoCombResp.class,
    IncluirJDEResp.class,
    ProrrogarVencimentoResp.class,
    InserirEmLoteJDEResp.class,
    ConsultarJDEResp.class
})
public class EnsResponse
    extends EnsMessagebody
{


}
