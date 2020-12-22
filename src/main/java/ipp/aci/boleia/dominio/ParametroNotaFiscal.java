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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Representa a tabela de Parametrização de Nota Fiscal
 */
@Audited
@Entity
@Table(name = "PARAMETRO_NF")
public class ParametroNotaFiscal implements IPersistente {

    @Id
    @Column(name = "CD_PARAMETRO_NF")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PARAM_NF")
    @SequenceGenerator(name = "SEQ_PARAM_NF", sequenceName = "SEQ_PARAM_NF", allocationSize = 1)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA")
    private Frota frota;

    @NotNull
    @Column(name = "ID_LOCAL_DESTINO")
    private Integer localDestino;

    @NotNull
    @Column(name = "ID_AGRAPADA")
    private Boolean nfAgrupadas;

    @Size(max = 250)
    @Column(name = "NM_DADOS")
    private String dadosAdicionais;

    @Column(name = "NO_VERSAO")
    @Version
    private Long versao;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id=id;
    }

    public Frota getFrota() {
        return frota;
    }

    public void setFrota(Frota frota) {
        this.frota = frota;
    }

    public Integer getLocalDestino() {
        return localDestino;
    }

    public void setLocalDestino(Integer localDestino) {
        this.localDestino = localDestino;
    }

    public Boolean getNfAgrupadas() {
        return nfAgrupadas;
    }

    public void setNfAgrupadas(Boolean nfAgrupadas) {
        this.nfAgrupadas = nfAgrupadas;
    }

    public String getDadosAdicionais() {
        return dadosAdicionais;
    }

    public void setDadosAdicionais(String dadosAdicionais) {
        this.dadosAdicionais = dadosAdicionais;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }
}