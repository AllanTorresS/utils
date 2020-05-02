package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Representa a tabela de Connect Car
 */
@Entity
@Table(name="CONECT_CAR")
public class ConectCar implements IPersistente {

    private static final long serialVersionUID = 2000131050251557566L;

    @Id
    @Column(name = "CD_CONECT_CAR")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONECT_CAR")
    @SequenceGenerator(name = "SEQ_CONECT_CAR", sequenceName = "SEQ_CONECT_CAR", allocationSize = 1)
    private Long id;

    @Column(name = "DT_REQUISICAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataRequisicao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_USUARIO")
    private Usuario usuario;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataRequisicao() {
        return dataRequisicao;
    }

    public void setDataRequisicao(Date dataRequisicao) {
        this.dataRequisicao = dataRequisicao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
