
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Ens_Request")
@XmlSeeAlso({
    AtualizarJDEReq.class,
    ListarEstadoCivilReq.class,
    ListarTipoDocumentoPFReq.class,
    ConsultarCadastroGeralJDEReq.class,
    InativarJDEReq.class,
    CadastrarJDEReq.class,
    ListarOrgEmisPorDocPFReq.class,
    ConsultarPessoaJuridicaReq.class,
    ListarOrgEmisDocumentosPFReq.class
})
public class EnsRequest
    extends EnsMessagebody
{


}
