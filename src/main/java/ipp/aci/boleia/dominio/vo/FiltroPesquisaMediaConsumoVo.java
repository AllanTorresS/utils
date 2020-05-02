package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Filtro de Pesquisa para Relatório de Média de Consumo
 *
 */
public class FiltroPesquisaMediaConsumoVo extends BaseFiltroPaginado {

    private static final Long ID_MATRIZ = -1L;

    @NotNull
    private Date de;

    @NotNull
    private Date ate;

    private EnumVo tipoConsumo;

    private Long idFrota;

    @Size(max = 250)
    private String motorista;

    private EnumVo classificacaoMotorista;

    private EntidadeVo unidadeMotorista;

    private EntidadeVo grupoMotorista;

    private EntidadeVo empresaMotorista;

    @Size(max = 8)
    private String placaVeiculo;

    private EntidadeVo tipoVeiculo;

    private EntidadeVo subtipoVeiculo;

    private EnumVo classificacaoVeiculo;

    private EntidadeVo unidadeVeiculo;

    private EntidadeVo grupoVeiculo;

    private EntidadeVo empresaVeiculo;

    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    public void setPlacaVeiculo(String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }

    public EntidadeVo getTipoVeiculo() {
        return tipoVeiculo;
    }

    public void setTipoVeiculo(EntidadeVo tipoVeiculo) {
        this.tipoVeiculo = tipoVeiculo;
    }

    public EntidadeVo getSubtipoVeiculo() {
        return subtipoVeiculo;
    }

    public void setSubtipoVeiculo(EntidadeVo subtipoVeiculo) {
        this.subtipoVeiculo = subtipoVeiculo;
    }

    public EnumVo getClassificacaoVeiculo() {
        return classificacaoVeiculo;
    }

    public void setClassificacaoVeiculo(EnumVo classificacaoVeiculo) {
        this.classificacaoVeiculo = classificacaoVeiculo;
    }

    public EntidadeVo getUnidadeVeiculo() {
        return unidadeVeiculo;
    }

    public void setUnidadeVeiculo(EntidadeVo unidadeVeiculo) {
        this.unidadeVeiculo = unidadeVeiculo;
    }

    public EntidadeVo getGrupoVeiculo() {
        return grupoVeiculo;
    }

    public void setGrupoVeiculo(EntidadeVo grupoVeiculo) {
        this.grupoVeiculo = grupoVeiculo;
    }

    public EntidadeVo getEmpresaVeiculo() {
        return empresaVeiculo;
    }

    public void setEmpresaVeiculo(EntidadeVo empresaVeiculo) {
        this.empresaVeiculo = empresaVeiculo;
    }


    public Long getIdFrota() {
        return idFrota;
    }

    public void setIdFrota(Long idFrota) {
        this.idFrota = idFrota;
    }

    public Date getDe() {
        return de;
    }

    public void setDe(Date de) {
        this.de = de;
    }

    public Date getAte() {
        return ate;
    }

    public void setAte(Date ate) {
        this.ate = ate;
    }

    public String getMotorista() {
        return motorista;
    }

    public void setMotorista(String motorista) {
        this.motorista = motorista;
    }

    public EnumVo getClassificacaoMotorista() {
        return classificacaoMotorista;
    }

    public void setClassificacaoMotorista(EnumVo classificacaoMotorista) {
        this.classificacaoMotorista = classificacaoMotorista;
    }

    public EntidadeVo getUnidadeMotorista() {
        return unidadeMotorista;
    }

    public void setUnidadeMotorista(EntidadeVo unidadeMotorista) {
        this.unidadeMotorista = unidadeMotorista;
    }

    public EntidadeVo getGrupoMotorista() {
        return grupoMotorista;
    }

    public void setGrupoMotorista(EntidadeVo grupoMotorista) {
        this.grupoMotorista = grupoMotorista;
    }

    public EntidadeVo getEmpresaMotorista() {
        return empresaMotorista;
    }

    public void setEmpresaMotorista(EntidadeVo empresaMotorista) {
        this.empresaMotorista = empresaMotorista;
    }

    public EnumVo getTipoConsumo() {
        return tipoConsumo;
    }

    public void setTipoConsumo(EnumVo tipoConsumo) {
        this.tipoConsumo = tipoConsumo;
    }

    /**
     * Indica que a unidade do Motorista está sendo fitlrada pela Matriz
     * @return true caso verdadeiro
     */
    public boolean isUnidadeMotoristaMatriz() {
        return unidadeMotorista != null && ID_MATRIZ.equals(unidadeMotorista.getId());
    }

    /**
     * Indica que a unidade do Veículo está sendo fitlrada pela Matriz
     * @return true caso verdadeiro
     */
    public boolean isUnidadeVeiculoMatriz() {
        return unidadeVeiculo != null &&  ID_MATRIZ.equals(unidadeVeiculo.getId());
    }
}
