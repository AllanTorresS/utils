package ipp.aci.boleia.dominio.vo;

import java.util.List;

/**
 * Vo para obtenção de valores para transferência de gestão
 */
public class TransferirGestaoVo {

    private Long idUsuarioDestino;
    private List<Long> permissoesUsuarioOrigem;

    public Long getIdUsuarioDestino() {
        return idUsuarioDestino;
    }

    public void setIdUsuarioDestino(Long idUsuarioDestino) {
        this.idUsuarioDestino = idUsuarioDestino;
    }

    public List<Long> getPermissoesUsuarioOrigem() {
        return permissoesUsuarioOrigem;
    }

    public void setPermissoesUsuarioOrigem(List<Long> permissoesUsuarioOrigem) {
        this.permissoesUsuarioOrigem = permissoesUsuarioOrigem;
    }
}
