
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfpessoaJuridicapessoaJuridica", propOrder = {
    "pessoaJuridica"
})
public class ArrayOfpessoaJuridicapessoaJuridica {

    @XmlElement(nillable = true)
    protected List<PessoaJuridica> pessoaJuridica;

    
    public List<PessoaJuridica> getPessoaJuridica() {
        if (pessoaJuridica == null) {
            pessoaJuridica = new ArrayList<PessoaJuridica>();
        }
        return this.pessoaJuridica;
    }

}
