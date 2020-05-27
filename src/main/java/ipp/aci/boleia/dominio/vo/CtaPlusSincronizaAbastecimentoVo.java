package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.util.ConstantesCta;
import java.util.List;

/**
 * Vo para obtenção da resposta das requisições de CTA Plus
 */
public class CtaPlusSincronizaAbastecimentoVo {

    private CtaPlusStatusVo status;
    private List<CtaPlusAbastecimentoVo> abastecimentos;

    /**
     * Construtor default da classe.
     */
    public CtaPlusSincronizaAbastecimentoVo() { }

    /**
     * Constrói a classe com um erro de sincronização.
     *
     * @param erro Erro ocorrido.
     */
    public CtaPlusSincronizaAbastecimentoVo(String erro) {
        this.status = new CtaPlusStatusVo();
        this.status.setMensagem(erro);
        this.status.setCodigo(ConstantesCta.ERRO);
    }

    public CtaPlusStatusVo getStatus() {
        return status;
    }

    public void setStatus(CtaPlusStatusVo status) {
        this.status = status;
    }

    public List<CtaPlusAbastecimentoVo> getAbastecimentos() {
        return abastecimentos;
    }

    public void setAbastecimentos(List<CtaPlusAbastecimentoVo> abastecimentos) {
        this.abastecimentos = abastecimentos;
    }
}
