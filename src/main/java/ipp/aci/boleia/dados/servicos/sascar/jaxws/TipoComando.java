
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoComando", propOrder = {
    "descricao",
    "idTipoComando",
    "nome"
})
public class TipoComando {

    protected String descricao;
    protected Integer idTipoComando;
    protected String nome;

    
    public String getDescricao() {
        return descricao;
    }

    
    public void setDescricao(String value) {
        this.descricao = value;
    }

    
    public Integer getIdTipoComando() {
        return idTipoComando;
    }

    
    public void setIdTipoComando(Integer value) {
        this.idTipoComando = value;
    }

    
    public String getNome() {
        return nome;
    }

    
    public void setNome(String value) {
        this.nome = value;
    }

}
