package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.FluxoAbastecimentoMotoristaConfig;

/**
 * Classe de exibição que representa uma autorizacao pagamento anonimização
 */
public class FluxoAbastecimentoMotoristaConfigAnonimizacaoVo {

    private Long id;
    private Boolean exigirTelaHodometroHorimetro;
    private Boolean exigirLeituraAutoHodometroHorimetro;
    private Boolean exigirFotoHodometroHorimetro;
    private Boolean exigirPosto;
    private Boolean exigirCombustivel;
    private String veiculo;
    private String veiculoClimatizador;
    private Boolean excluido;

    /**
     * Construtor básico para serialização JSON
     */
    public FluxoAbastecimentoMotoristaConfigAnonimizacaoVo() {

    }

    /**
     * Constrói FluxoAbastecimentoMotoristaConfigAnonimizacaoVo com os dados fornecidos
     *
     * @param fluxoAbastecimentoMotoristaConfig a entidade contendo informações
     */
    public FluxoAbastecimentoMotoristaConfigAnonimizacaoVo(FluxoAbastecimentoMotoristaConfig fluxoAbastecimentoMotoristaConfig) {
        if(fluxoAbastecimentoMotoristaConfig == null){
            return;
        }
        this.id = fluxoAbastecimentoMotoristaConfig.getId();
        this.exigirTelaHodometroHorimetro = fluxoAbastecimentoMotoristaConfig.getExigirTelaHodometroHorimetro();
        this.exigirFotoHodometroHorimetro = fluxoAbastecimentoMotoristaConfig.getExigirFotoHodometroHorimetro();
        this.exigirLeituraAutoHodometroHorimetro = fluxoAbastecimentoMotoristaConfig.getExigirLeituraAutoHodometroHorimetro();
        this.exigirPosto = fluxoAbastecimentoMotoristaConfig.getExigirPosto();
        this.exigirCombustivel = fluxoAbastecimentoMotoristaConfig.getExigirCombustivel();
        this.veiculo = fluxoAbastecimentoMotoristaConfig.getVeiculo() != null ? fluxoAbastecimentoMotoristaConfig.getVeiculo().getPlaca() : null;
        this.veiculoClimatizador = fluxoAbastecimentoMotoristaConfig.getVeiculoClimatizador() != null ? fluxoAbastecimentoMotoristaConfig.getVeiculoClimatizador().getPlaca() : null;
        this.excluido = fluxoAbastecimentoMotoristaConfig.getExcluido();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getExigirTelaHodometroHorimetro() {
        return exigirTelaHodometroHorimetro;
    }

    public void setExigirTelaHodometroHorimetro(Boolean exigirTelaHodometroHorimetro) {
        this.exigirTelaHodometroHorimetro = exigirTelaHodometroHorimetro;
    }

    public Boolean getExigirLeituraAutoHodometroHorimetro() {
        return exigirLeituraAutoHodometroHorimetro;
    }

    public void setExigirLeituraAutoHodometroHorimetro(Boolean exigirLeituraAutoHodometroHorimetro) {
        this.exigirLeituraAutoHodometroHorimetro = exigirLeituraAutoHodometroHorimetro;
    }

    public Boolean getExigirFotoHodometroHorimetro() {
        return exigirFotoHodometroHorimetro;
    }

    public void setExigirFotoHodometroHorimetro(Boolean exigirFotoHodometroHorimetro) {
        this.exigirFotoHodometroHorimetro = exigirFotoHodometroHorimetro;
    }

    public Boolean getExigirPosto() {
        return exigirPosto;
    }

    public void setExigirPosto(Boolean exigirPosto) {
        this.exigirPosto = exigirPosto;
    }

    public Boolean getExigirCombustivel() {
        return exigirCombustivel;
    }

    public void setExigirCombustivel(Boolean exigirCombustivel) {
        this.exigirCombustivel = exigirCombustivel;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public String getVeiculoClimatizador() {
        return veiculoClimatizador;
    }

    public void setVeiculoClimatizador(String veiculoClimatizador) {
        this.veiculoClimatizador = veiculoClimatizador;
    }

    public Boolean getExcluido() {
        return excluido;
    }

    public void setExcluido(Boolean excluido) {
        this.excluido = excluido;
    }
}
