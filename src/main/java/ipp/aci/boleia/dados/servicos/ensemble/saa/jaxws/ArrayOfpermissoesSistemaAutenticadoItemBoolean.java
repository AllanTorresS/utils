
package ipp.aci.boleia.dados.servicos.ensemble.saa.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfpermissoesSistemaAutenticadoItemBoolean", propOrder = {
    "permissoesSistemaAutenticadoItem"
})
public class ArrayOfpermissoesSistemaAutenticadoItemBoolean {

    @XmlElement(nillable = true)
    protected List<Boolean> permissoesSistemaAutenticadoItem;

    
    public List<Boolean> getPermissoesSistemaAutenticadoItem() {
        if (permissoesSistemaAutenticadoItem == null) {
            permissoesSistemaAutenticadoItem = new ArrayList<Boolean>();
        }
        return this.permissoesSistemaAutenticadoItem;
    }

}
