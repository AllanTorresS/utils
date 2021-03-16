package ipp.aci.boleia.dominio;

import java.io.Serializable;

/**
 * Classe para manter as chaves primárias da entidade {@link RotaParametroFrota}.
 */
public class RotaParametroFrotaPk implements Serializable {
    private static final long serialVersionUID = 1890396646571997146L;

    private Long idRota;
    private Integer idParametro;

    /**
     * Construtor default da classe.
     */
    public RotaParametroFrotaPk() {
    }

    /**
     * Construtor da classe.
     *
     * @param idRota Identificador da Rota.
     * @param idParametro         Identificador dO Parametro.
     */
    public RotaParametroFrotaPk(Long idRota, Integer idParametro) {
        this.idRota = idRota;
        this.idParametro = idParametro;
    }

    public Long getIdRota() {
        return idRota;
    }

    public void setIdRota(Long idRota) {
        this.idRota = idRota;
    }

    public Integer getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(Integer idParametro) {
        this.idParametro = idParametro;
    }
}
