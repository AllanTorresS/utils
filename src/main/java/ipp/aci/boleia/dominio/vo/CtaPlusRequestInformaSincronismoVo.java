package ipp.aci.boleia.dominio.vo;

import java.util.List;

/**
 * Vo para envio da requisição de Informa Sincronismo
 */
public class CtaPlusRequestInformaSincronismoVo {

    private List<CtaPlusInformaSincronismoVo> abastecimentos;

    /**
     * Construtor para serializacao
     */
    public CtaPlusRequestInformaSincronismoVo() {
    }

    /**
     * Construtor especializado a partir de uma lista de abastecimentos
     * @param abastecimentos lista de abastecimentos
     */
    public CtaPlusRequestInformaSincronismoVo(List<CtaPlusInformaSincronismoVo> abastecimentos) {
        this.abastecimentos = abastecimentos;
    }

    public List<CtaPlusInformaSincronismoVo> getAbastecimentos() {
        return abastecimentos;
    }

    public void setAbastecimentos(List<CtaPlusInformaSincronismoVo> abastecimentos) {
        this.abastecimentos = abastecimentos;
    }
}
