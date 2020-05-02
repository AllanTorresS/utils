
package ipp.aci.boleia.dados.servicos.ensemble.saa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "autorizacaoResp", propOrder = {
    "possuiPermissaoSistemaReturn",
    "permissoesSistemaAutenticado",
    "status",
    "msgErro"
})
public class AutorizacaoResp
    extends EnsResponse
{

    protected Boolean possuiPermissaoSistemaReturn;
    protected ArrayOfpermissoesSistemaAutenticadoItemBoolean permissoesSistemaAutenticado;
    protected byte[] status;
    protected String msgErro;

    
    public Boolean isPossuiPermissaoSistemaReturn() {
        return possuiPermissaoSistemaReturn;
    }

    
    public void setPossuiPermissaoSistemaReturn(Boolean value) {
        this.possuiPermissaoSistemaReturn = value;
    }

    
    public ArrayOfpermissoesSistemaAutenticadoItemBoolean getPermissoesSistemaAutenticado() {
        return permissoesSistemaAutenticado;
    }

    
    public void setPermissoesSistemaAutenticado(ArrayOfpermissoesSistemaAutenticadoItemBoolean value) {
        this.permissoesSistemaAutenticado = value;
    }

    
    public byte[] getStatus() {
        return status;
    }

    
    public void setStatus(byte[] value) {
        this.status = value;
    }

    
    public String getMsgErro() {
        return msgErro;
    }

    
    public void setMsgErro(String value) {
        this.msgErro = value;
    }

}
