package ipp.aci.boleia.dominio;


import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

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

/**
 * Entidade que representa uma aba de um relatório gerado pelo motor de relatórios
 */
@Audited
@Entity
@Table(name = "ABA_RELATORIO")
public class AbaRelatorio implements IPersistente {

    @Id
    @Column(name = "CD_ABA_RELATORIO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ABA_RELATORIO")
    @SequenceGenerator(name = "SEQ_ABA_RELATORIO", sequenceName = "SEQ_ABA_RELATORIO", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_MOTOR_GER_RELATORIOS")
    private MotorGeracaoRelatorios motorGeracaoRelatorio;

    @Column(name="VA_INDICE_ABA")
    private Integer indiceAba;

    @Column(name="VA_TOTAL_REGISTROS")
    private Long totalRegistros;

    @Column(name="VA_REGISTROS_PROCESSADOS")
    private Long registrosProcessados;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public MotorGeracaoRelatorios getMotorGeracaoRelatorio() {
        return motorGeracaoRelatorio;
    }

    public void setMotorGeracaoRelatorio(MotorGeracaoRelatorios motorGeracaoRelatorio) {
        this.motorGeracaoRelatorio = motorGeracaoRelatorio;
    }

    public Integer getIndiceAba() {
        return indiceAba;
    }

    public void setIndiceAba(Integer indiceAba) {
        this.indiceAba = indiceAba;
    }

    public Long getTotalRegistros() {
        return totalRegistros;
    }

    public void setTotalRegistros(Long totalRegistros) {
        this.totalRegistros = totalRegistros;
    }

    public Long getRegistrosProcessados() {
        return registrosProcessados != null ? registrosProcessados : Long.valueOf(0);
    }

    public void setRegistrosProcessados(Long registrosProcessados) {
        this.registrosProcessados = registrosProcessados;
    }
}
