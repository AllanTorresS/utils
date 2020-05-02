
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "macroTd50Tmcd", propOrder = {
    "idMacroTd50Tmcd",
    "idVeiculo",
    "layout",
    "layoutDetalhado",
    "nome"
})
public class MacroTd50Tmcd {

    protected Integer idMacroTd50Tmcd;
    protected Integer idVeiculo;
    protected String layout;
    protected String layoutDetalhado;
    protected String nome;

    
    public Integer getIdMacroTd50Tmcd() {
        return idMacroTd50Tmcd;
    }

    
    public void setIdMacroTd50Tmcd(Integer value) {
        this.idMacroTd50Tmcd = value;
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

    
    public String getLayoutDetalhado() {
        return layoutDetalhado;
    }

    
    public void setLayoutDetalhado(String value) {
        this.layoutDetalhado = value;
    }

    
    public String getNome() {
        return nome;
    }

    
    public void setNome(String value) {
        this.nome = value;
    }

}
