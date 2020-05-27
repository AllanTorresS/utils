package ipp.aci.boleia.dominio.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma edição de um conjunto de campos de um abastecimento da solução.
 */
public class EdicaoAbastecimentoVo {

    List<EdicaoCampoAbastecimentoVo> camposEditados;

    /**
     * Construtor default da classe.
     */
    public EdicaoAbastecimentoVo() {
        this.camposEditados = new ArrayList<>();
    }

    public List<EdicaoCampoAbastecimentoVo> getCamposEditados() {
        return camposEditados;
    }

    public void setCamposEditados(List<EdicaoCampoAbastecimentoVo> camposEditados) {
        this.camposEditados = camposEditados;
    }

    /**
     * Informa se a edição possui algum campo alterado.
     *
     * @return true, caso possua.
     */
    public boolean possuiCamposAlterados() {
        return camposEditados.size() > 0;
    }
}
