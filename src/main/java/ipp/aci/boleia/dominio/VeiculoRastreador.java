package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.enums.TipoRastreador;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.vo.DadosVeiculoOnixSatVo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Classe que representa um veículo na integração com a SASCAR
 *
 */

@Entity
@Table(name = "VEICULO_RASTREADOR")
public class VeiculoRastreador implements IPersistente {

    private static final long serialVersionUID = -2454233844663549147L;

    @Id
    @Column(name = "CD_VEICULO_RASTREADOR")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VEICULO_RASTREADOR")
    @SequenceGenerator(name = "SEQ_VEICULO_RASTREADOR", sequenceName = "SEQ_VEICULO_RASTREADOR", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "CD_VEICULO_SISTEMA")
    private Integer idVeiculoSistema;

    @NotNull
    @Size(max = 7)
    @Column(name = "DS_PLACA_VEICULO")
    private String placa;

    @NotNull
    @Column(name = "ID_TIPO_RASTREADOR")
    private Integer tipoRastreador;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    /**
     * Construtor default
     */
    public VeiculoRastreador(){
        //Construtor default
    }

    /**
     * Converte os dados do veículo retornados pelo serviço da OnixSat
     * para a entidade persistida
     * @param vo O VO com os dados do veículo
     */
    public VeiculoRastreador(DadosVeiculoOnixSatVo vo){
        this.setIdVeiculoSistema(vo.getVeiID());
        this.setPlaca(vo.getPlaca());
        this.setTipoRastreador(TipoRastreador.ONIXSAT.getValue());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdVeiculoSistema() {
        return idVeiculoSistema;
    }

    public void setIdVeiculoSistema(Integer idVeiculoSistema) {
        this.idVeiculoSistema = idVeiculoSistema;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Integer getTipoRastreador() {
        return tipoRastreador;
    }

    public void setTipoRastreador(Integer tipoRastreador) {
        this.tipoRastreador = tipoRastreador;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }
}
