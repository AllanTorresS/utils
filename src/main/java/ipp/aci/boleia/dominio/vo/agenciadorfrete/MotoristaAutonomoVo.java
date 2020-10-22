package ipp.aci.boleia.dominio.vo.agenciadorfrete;

import io.swagger.annotations.ApiModelProperty;
import ipp.aci.boleia.dominio.agenciadorfrete.MotoristaAutonomo;
import ipp.aci.boleia.util.anotacoes.Numeric;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Classe de exibição que representa um motorista autonomo
 */
public class MotoristaAutonomoVo {

    @ApiModelProperty(value = "${docs.dominio.vo.MotoristaAutonomoVo.cpf}", required = true)
    @NotNull
    private String cpf;

    @ApiModelProperty(value = "${docs.dominio.vo.MotoristaAutonomoVo.nome}", required = true)
    @Size(max = 255)
    @NotNull
    private String nome;

    @ApiModelProperty(value = "${docs.dominio.vo.MotoristaAutonomoVo.email}")
    @Size(max = 255)
    @NotNull
    private String email;

    @ApiModelProperty(value = "${docs.dominio.vo.MotoristaAutonomoVo.dddCelular}")
    @Size(max = 2)
    @Numeric
    private String dddCelular;

    @ApiModelProperty(value = "${docs.dominio.vo.MotoristaAutonomoVo.celular}")
    @Size(max = 9)
    @Numeric
    private String celular;

    /**
     * Construtor default
     */
    public MotoristaAutonomoVo() {
        //construtor default
    }

    public MotoristaAutonomoVo(MotoristaAutonomo motoristaAutonomo) {
        this.cpf = motoristaAutonomo.getCpf().toString();
        this.nome = motoristaAutonomo.getNome();
        this.email = motoristaAutonomo.getEmail();
        this.dddCelular = motoristaAutonomo.getDddTelefoneCelular()!=null ? motoristaAutonomo.getDddTelefoneCelular().toString() : null;
        this.celular = motoristaAutonomo.getTelefoneCelular() != null ? motoristaAutonomo.getTelefoneCelular().toString() : null;
    }

    public MotoristaAutonomoVo(String cpf, MotoristaAutonomoEdicaoVo motoristaAutonomo) {
        this.cpf = cpf;
        this.nome = motoristaAutonomo.getNome();
        this.email = motoristaAutonomo.getEmail();
        this.dddCelular = motoristaAutonomo.getDddCelular();
        this.celular = motoristaAutonomo.getCelular();
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDddCelular() {
        return dddCelular;
    }

    public void setDddCelular(String dddCelular) {
        this.dddCelular = dddCelular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

}
