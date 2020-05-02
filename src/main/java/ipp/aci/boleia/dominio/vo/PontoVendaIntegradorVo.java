package ipp.aci.boleia.dominio.vo;

import java.util.List;

/**
 * Representa uma entidade de ponto de venda para integração com o Integrador
 */
public class PontoVendaIntegradorVo {

    private int statusRequisicao;
    private List<ControladoraIntegradorVo> controladoras;
    private String novoTokenJWT;

    public int getStatusRequisicao() {
        return statusRequisicao;
    }

    public void setStatusRequisicao(int statusRequisicao) {
        this.statusRequisicao = statusRequisicao;
    }

    public List<ControladoraIntegradorVo> getControladoras() {
        return controladoras;
    }

    public void setControladoras(List<ControladoraIntegradorVo> controladoras) {
        this.controladoras = controladoras;
    }

    public String getNovoTokenJWT() {
        return novoTokenJWT;
    }

    public void setNovoTokenJWT(String novoTokenJWT) {
        this.novoTokenJWT = novoTokenJWT;
    }
}
