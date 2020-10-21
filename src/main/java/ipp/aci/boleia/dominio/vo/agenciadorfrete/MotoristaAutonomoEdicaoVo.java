package ipp.aci.boleia.dominio.vo.agenciadorfrete;

import io.swagger.annotations.ApiModelProperty;
import ipp.aci.boleia.util.anotacoes.Numeric;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Classe de exibição que representa a edição de um motorista autonomo
 */
public class MotoristaAutonomoEdicaoVo {

    @ApiModelProperty(value = "${docs.visao.dto.MotoristaAutonomoEdicaoDTO.nome}", required = true)
    @NotNull
    @Size(max = 255)
    private String nome;
    @ApiModelProperty(value = "${docs.visao.dto.MotoristaAutonomoEdicaoDTO.email}")
    @Size(max = 255)
    private String email;
    @ApiModelProperty(value = "${docs.visao.dto.MotoristaAutonomoEdicaoDTO.dddCelular}")
    @Size(max = 2)
    @Numeric
    private String dddCelular;
    @ApiModelProperty(value = "${docs.visao.dto.MotoristaAutonomoEdicaoDTO.celular}")
    @Size(max = 9)
    @Numeric
    private String celular;

    /**
     * Construtor default
     */
    public MotoristaAutonomoEdicaoVo() {
        //construtor default
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDddCelular() {
        return dddCelular;
    }

    public void setDddCelular(String dddCelular) {
        this.dddCelular = dddCelular;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }
}
