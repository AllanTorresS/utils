
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consultarPessoaJuridicaeResp", propOrder = {
    "listaPessoaJuridica"
})
public class ConsultarPessoaJuridicaeResp
    extends V2
{

    protected ArrayOfpessoaJuridicapessoaJuridica listaPessoaJuridica;

    
    public ArrayOfpessoaJuridicapessoaJuridica getListaPessoaJuridica() {
        return listaPessoaJuridica;
    }

    
    public void setListaPessoaJuridica(ArrayOfpessoaJuridicapessoaJuridica value) {
        this.listaPessoaJuridica = value;
    }

}
