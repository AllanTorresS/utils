
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Ens_Response")
@XmlSeeAlso({
    ListarEstadoCivilResp.class,
    ConsultarCadastroGeralJDEResp.class,
    Response.class,
    ListarTipoDocumentoPFResp.class,
    CadastrarJDEResp.class,
    ListarOrgEmisDocumentosPFResp.class,
    ListarOrgEmisPorDocPFResp.class,
    StatusIntegracao.class,
    V2.class
})
public class EnsResponse
    extends EnsMessagebody
{


}
