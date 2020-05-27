
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "criarResp", propOrder = {
    "numeroDocumento",
    "tipoCodigoDocumento",
    "companhiaDocumento",
    "listaItemVoucher",
    "listaLinhaVoucher"
})
public class CriarResp
    extends V2
{

    protected String numeroDocumento;
    protected String tipoCodigoDocumento;
    protected String companhiaDocumento;
    protected ArrayOfitemVoucheritemVoucher listaItemVoucher;
    protected ArrayOflinhaVoucherlinhaVoucher listaLinhaVoucher;

    
    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    
    public void setNumeroDocumento(String value) {
        this.numeroDocumento = value;
    }

    
    public String getTipoCodigoDocumento() {
        return tipoCodigoDocumento;
    }

    
    public void setTipoCodigoDocumento(String value) {
        this.tipoCodigoDocumento = value;
    }

    
    public String getCompanhiaDocumento() {
        return companhiaDocumento;
    }

    
    public void setCompanhiaDocumento(String value) {
        this.companhiaDocumento = value;
    }

    
    public ArrayOfitemVoucheritemVoucher getListaItemVoucher() {
        return listaItemVoucher;
    }

    
    public void setListaItemVoucher(ArrayOfitemVoucheritemVoucher value) {
        this.listaItemVoucher = value;
    }

    
    public ArrayOflinhaVoucherlinhaVoucher getListaLinhaVoucher() {
        return listaLinhaVoucher;
    }

    
    public void setListaLinhaVoucher(ArrayOflinhaVoucherlinhaVoucher value) {
        this.listaLinhaVoucher = value;
    }

}
