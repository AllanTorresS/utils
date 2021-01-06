package ipp.aci.boleia.dominio.vo;

import java.util.Date;

/**
 * Filtro utilizado para buscar as Empresas/Unidades para a tela do financeiro.
 *
 * @author pedro.silva
 */
public class FiltroPesquisaEmpresaUnidadeFinanceiroVo {
    private Date de;
    private Date ate;
    private Long idFrota;
    private Long idPontoVenda;
    private EnumVo statusCiclo;

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

    public Long getIdFrota() {
        return idFrota;
    }

    public void setIdFrota(Long idFrota) {
        this.idFrota = idFrota;
    }

    public Long getIdPontoVenda() {
        return idPontoVenda;
    }

    public void setIdPontoVenda(Long idPontoVenda) {
        this.idPontoVenda = idPontoVenda;
    }

    public EnumVo getStatusCiclo() {
        return statusCiclo;
    }

    public void setStatusCiclo(EnumVo statusCiclo) {
        this.statusCiclo = statusCiclo;
    }
}
