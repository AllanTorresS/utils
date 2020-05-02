package ipp.aci.boleia.dominio;


import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

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
import javax.persistence.Version;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Representa a tabela de Conta Bancaria
 */
@Entity
@Audited
@Table(name = "CONTA_BANCARIA")
public class ContaBancaria implements IPersistente {

    private static final long serialVersionUID = 2705082274116632751L;

    @Id
    @Column(name = "CD_CONTA_BANCARIA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONTA_BANCARIA")
    @SequenceGenerator(name = "SEQ_CONTA_BANCARIA", sequenceName = "SEQ_CONTA_BANCARIA", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_BANCO")
    private Banco banco;

    @NotEmpty
    @Size(max = 5)
    @Column(name = "NO_AGENCIA")
    private String numeroAgencia;

    @Size(max = 1)
    @Column(name = "DG_AGENCIA")
    private String digitoAgencia;

    @NotEmpty
    @Size(max = 20)
    @Column(name = "NO_CONTA")
    private String numeroConta;

    @Size(max = 2)
    @Column(name = "DG_CONTA")
    private String digitoConta;

    @NotNull
    @Column(name = "ID_PESSOA")
    private Integer tipoPessoa;

    @NotEmpty
    @Size(max = 250)
    @Column(name = "NM_TITULAR")
    private String nomeTitular;

    @NotNull
    @Digits(integer=22, fraction = 0)
    @Column(name = "CD_PESSOA")
    private Long codigoPessoa;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public String getNumeroAgencia() {
        return numeroAgencia;
    }

    public void setNumeroAgencia(String numeroAgencia) {
        this.numeroAgencia = numeroAgencia;
    }

    public String getDigitoAgencia() {
        return digitoAgencia;
    }

    public void setDigitoAgencia(String digitoAgencia) {
        this.digitoAgencia = digitoAgencia;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getDigitoConta() {
        return digitoConta;
    }

    public void setDigitoConta(String digitoConta) {
        this.digitoConta = digitoConta;
    }

    public Integer getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(Integer tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular;
    }

    public Long getCodigoPessoa() {
        return codigoPessoa;
    }

    public void setCodigoPessoa(Long codigoPessoa) {
        this.codigoPessoa = codigoPessoa;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    /**
     * Verifica se uma a conta informada contem os mesmos dados desta
     * @param dadosbancarios A conta a ser comparada
     * @return True caso sejam os mesmos dados bancarios
     */
    public boolean mesmosDadosBancarios(ContaBancaria dadosbancarios) {
        if (id != null ? !id.equals(dadosbancarios.id) : dadosbancarios.id != null){
            return false;
        }
        if (numeroAgencia != null ? !numeroAgencia.equals(dadosbancarios.numeroAgencia) : dadosbancarios.numeroAgencia != null) {
            return false;
        }
        if (digitoAgencia != null ? !digitoAgencia.equals(dadosbancarios.digitoAgencia) : dadosbancarios.digitoAgencia != null) {
            return false;
        }
        if (numeroConta != null ? !numeroConta.equals(dadosbancarios.numeroConta) : dadosbancarios.numeroConta != null) {
            return false;
        }
        if (digitoConta != null ? !digitoConta.equals(dadosbancarios.digitoConta) : dadosbancarios.digitoConta != null) {
            return false;
        }
        if (tipoPessoa != null ? !tipoPessoa.equals(dadosbancarios.tipoPessoa) : dadosbancarios.tipoPessoa != null) {
            return false;
        }
        if (nomeTitular != null ? !nomeTitular.equals(dadosbancarios.nomeTitular) : dadosbancarios.nomeTitular != null) {
            return false;
        }
        if (banco != null ? !banco.getId().equals(dadosbancarios.banco != null ? dadosbancarios.banco.getId() : null) : dadosbancarios.banco != null) {
            return false;
        }
        return codigoPessoa != null ? codigoPessoa.equals(dadosbancarios.codigoPessoa) : dadosbancarios.codigoPessoa == null;
    }
}