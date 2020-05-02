package ipp.aci.boleia.dominio.vo;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Vo para obtenção de abastecimentos enviados pelo CTA Plus
 */
public class CtaPlusAbastecimentoVo {

    private Long id;
    private String dataInicio;
    private String horaInicio;
    private String volume;
    private String custo;
    private String horimetro;
    private String odometro;
    private Integer produtoCategoriaId;


    private CtaPlusMotoristaVo motorista;
    private CtaPlusPostoVo posto;
    private CtaPlusEmpresaVo empresa;
    private CtaPlusCombustivelVo combustivel;
    private CtaPlusVeiculoVo veiculo;
    private CtaPlusFrentistaVo frentista;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getProdutoCategoriaId() {
        return produtoCategoriaId;
    }

    @JsonSetter("produto_categoria_id")
    public void setProdutoCategoriaId(Integer produtoCategoriaId) {
        this.produtoCategoriaId = produtoCategoriaId;
    }

    @JsonGetter("data_inicio")
    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getCusto() {
        return custo;
    }

    public void setCusto(String custo) {
        this.custo = custo;
    }

    public String getHorimetro() {
        return horimetro;
    }

    public void setHorimetro(String horimetro) {
        this.horimetro = horimetro;
    }

    public String getOdometro() {
        return odometro;
    }

    public void setOdometro(String odometro) {
        this.odometro = odometro;
    }

    public CtaPlusMotoristaVo getMotorista() {
        return motorista;
    }

    public void setMotorista(CtaPlusMotoristaVo motorista) {
        this.motorista = motorista;
    }

    public CtaPlusPostoVo getPosto() {
        return posto;
    }

    public void setPosto(CtaPlusPostoVo posto) {
        this.posto = posto;
    }

    public CtaPlusEmpresaVo getEmpresa() {
        return empresa;
    }

    public void setEmpresa(CtaPlusEmpresaVo empresa) {
        this.empresa = empresa;
    }

    public CtaPlusCombustivelVo getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(CtaPlusCombustivelVo combustivel) {
        this.combustivel = combustivel;
    }

    public CtaPlusVeiculoVo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(CtaPlusVeiculoVo veiculo) {
        this.veiculo = veiculo;
    }

    public CtaPlusFrentistaVo getFrentista() {
        return frentista;
    }

    public void setFrentista(CtaPlusFrentistaVo frentista) {
        this.frentista = frentista;
    }

    @JsonGetter("hora_inicio")
    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }
}
