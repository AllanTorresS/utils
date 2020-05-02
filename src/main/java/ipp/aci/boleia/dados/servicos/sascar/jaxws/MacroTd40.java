
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "macroTd40", propOrder = {
    "idMacroTd40",
    "idVeiculo",
    "mensagem",
    "tipoMensagem"
})
public class MacroTd40 {

    protected Integer idMacroTd40;
    protected Integer idVeiculo;
    protected String mensagem;
    protected Integer tipoMensagem;

    
    public Integer getIdMacroTd40() {
        return idMacroTd40;
    }

    
    public void setIdMacroTd40(Integer value) {
        this.idMacroTd40 = value;
    }

    
    public Integer getIdVeiculo() {
        return idVeiculo;
    }

    
    public void setIdVeiculo(Integer value) {
        this.idVeiculo = value;
    }

    
    public String getMensagem() {
        return mensagem;
    }

    
    public void setMensagem(String value) {
        this.mensagem = value;
    }

    
    public Integer getTipoMensagem() {
        return tipoMensagem;
    }

    
    public void setTipoMensagem(Integer value) {
        this.tipoMensagem = value;
    }

}
