package ipp.aci.boleia.dominio;

import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * Entidade para mapear a tabela de relacionamento entre Empresa Agregada e Unidade.
 */
@Audited
@Entity
@Table(name = "EMPRESA_AGREGADA_UNIDADE")
@IdClass(EmpresaAgregadaUnidadePk.class)
public class EmpresaAgregadaUnidade {

    @Id
    @Column(name = "CD_EMPR_AGREGADA")
    private Long idEmpresaAgregada;

    @Id
    @Column(name = "CD_UNIDADE")
    private Long idUnidade;

    public Long getIdEmpresaAgregada() {
        return idEmpresaAgregada;
    }

    public void setIdEmpresaAgregada(Long idEmpresaAgregada) {
        this.idEmpresaAgregada = idEmpresaAgregada;
    }

    public Long getIdUnidade() {
        return idUnidade;
    }

    public void setIdUnidade(Long idUnidade) {
        this.idUnidade = idUnidade;
    }
}
