
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "macroTms3", propOrder = {
    "idMacroTms3",
    "idVeiculo",
    "layout",
    "nome"
})
public class MacroTms3 {

    protected Integer idMacroTms3;
    protected Integer idVeiculo;
    protected String layout;
    protected String nome;

    
    public Integer getIdMacroTms3() {
        return idMacroTms3;
    }

    
    public void setIdMacroTms3(Integer value) {
        this.idMacroTms3 = value;
    }

    
    public Integer getIdVeiculo() {
        return idVeiculo;
    }

    
    public void setIdVeiculo(Integer value) {
        this.idVeiculo = value;
    }

    
    public String getLayout() {
        return layout;
    }

    
    public void setLayout(String value) {
        this.layout = value;
    }

    
    public String getNome() {
        return nome;
    }

    
    public void setNome(String value) {
        this.nome = value;
    }

}
