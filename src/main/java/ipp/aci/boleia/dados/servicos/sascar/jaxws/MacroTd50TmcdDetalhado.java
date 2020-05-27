
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "macroTd50TmcdDetalhado", propOrder = {
    "idMacroTd50Tmcd",
    "idTmcd",
    "listaLayout",
    "listaVeiculos",
    "nome"
})
public class MacroTd50TmcdDetalhado {

    protected Integer idMacroTd50Tmcd;
    protected Integer idTmcd;
    @XmlElement(nillable = true)
    protected List<LayoutDetalhado> listaLayout;
    @XmlElement(nillable = true)
    protected List<Integer> listaVeiculos;
    protected String nome;

    
    public Integer getIdMacroTd50Tmcd() {
        return idMacroTd50Tmcd;
    }

    
    public void setIdMacroTd50Tmcd(Integer value) {
        this.idMacroTd50Tmcd = value;
    }

    
    public Integer getIdTmcd() {
        return idTmcd;
    }

    
    public void setIdTmcd(Integer value) {
        this.idTmcd = value;
    }

    
    public List<LayoutDetalhado> getListaLayout() {
        if (listaLayout == null) {
            listaLayout = new ArrayList<LayoutDetalhado>();
        }
        return this.listaLayout;
    }

    
    public List<Integer> getListaVeiculos() {
        if (listaVeiculos == null) {
            listaVeiculos = new ArrayList<Integer>();
        }
        return this.listaVeiculos;
    }

    
    public String getNome() {
        return nome;
    }

    
    public void setNome(String value) {
        this.nome = value;
    }

}
