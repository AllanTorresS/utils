package ipp.aci.boleia.dominio.vo;

import java.util.List;

/**
 * Representa uma entidade de abastecimento para integração com o Integrador
 */
public class RespostaAbastecimentoIntegradorVo {

    private int statusRequisicao;

    private Integer quantidade;

    private List<RegistroAbastecimentoIntegradorVo> registros;

    private String novoTokenJwt;

    public int getStatusRequisicao() {
        return statusRequisicao;
    }

    public void setStatusRequisicao(int statusRequisicao) {
        this.statusRequisicao = statusRequisicao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public List<RegistroAbastecimentoIntegradorVo> getRegistros() {
        return registros;
    }

    public void setRegistros(List<RegistroAbastecimentoIntegradorVo> registros) {
        this.registros = registros;
    }

    public String getNovoTokenJwt() {
        return novoTokenJwt;
    }

    public void setNovoTokenJwt(String novoTokenJwt) {
        this.novoTokenJwt = novoTokenJwt;
    }
}
