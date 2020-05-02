
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "listarOrgEmisPorDocPFReq", propOrder = {
    "codigoTipoDocumento"
})
public class ListarOrgEmisPorDocPFReq
    extends EnsRequest
{

    protected long codigoTipoDocumento;

    
    public long getCodigoTipoDocumento() {
        return codigoTipoDocumento;
    }

    
    public void setCodigoTipoDocumento(long value) {
        this.codigoTipoDocumento = value;
    }

}
