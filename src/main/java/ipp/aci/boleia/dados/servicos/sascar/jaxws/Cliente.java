
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cliente", propOrder = {
    "cnpj",
    "cpf",
    "idCliente",
    "nome"
})
public class Cliente {

    protected Long cnpj;
    protected Long cpf;
    protected Integer idCliente;
    protected String nome;

    
    public Long getCnpj() {
        return cnpj;
    }

    
    public void setCnpj(Long value) {
        this.cnpj = value;
    }

    
    public Long getCpf() {
        return cpf;
    }

    
    public void setCpf(Long value) {
        this.cpf = value;
    }

    
    public Integer getIdCliente() {
        return idCliente;
    }

    
    public void setIdCliente(Integer value) {
        this.idCliente = value;
    }

    
    public String getNome() {
        return nome;
    }

    
    public void setNome(String value) {
        this.nome = value;
    }

}
