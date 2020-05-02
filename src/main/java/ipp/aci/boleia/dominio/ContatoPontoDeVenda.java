package ipp.aci.boleia.dominio;


import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceRevendedor;
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
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;

/**
 * Representa a tabela de Contato Ponto de Venda
 */
@Entity
@Audited
@Table(name = "CONTATO_PTOV")
public class ContatoPontoDeVenda implements IPersistente, IPertenceRevendedor {

    private static final long serialVersionUID = 6957437628265853857L;

    @Id
    @Column(name = "CD_CONTATO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONTATO_PTOV")
    @SequenceGenerator(name = "SEQ_CONTATO_PTOV", sequenceName = "SEQ_CONTATO_PTOV", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_PTOV")
    private PontoDeVenda pontoVenda;

    @Size(max=250)
    @Column(name = "NM_CONTATO")
    private String nome;

    @Max(99)
    @Column(name = "CD_DDD_TEL")
    private Integer dddTelefone;

    @Max(999999999L)
    @Column(name = "NO_TEL")
    private Long telefone;

    @Size(max=250)
    @Column(name = "DS_EMAIL")
    private String email;

    @Column(name = "ID_ADICIONAL")
    private Boolean adicional;

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

    public PontoDeVenda getPontoVenda() {
        return pontoVenda;
    }

    public void setPontoVenda(PontoDeVenda pontoVenda) {
        this.pontoVenda = pontoVenda;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getDddTelefone() {
        return dddTelefone;
    }

    public void setDddTelefone(Integer dddTelefone) {
        this.dddTelefone = dddTelefone;
    }

    public Long getTelefone() {
        return telefone;
    }

    public void setTelefone(Long telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAdicional() {
        return adicional;
    }

    public void setAdicional(Boolean adicional) {
        this.adicional = adicional;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    @Transient
    @Override
    public List<PontoDeVenda> getPontosDeVenda() {
        return Collections.singletonList(pontoVenda);
    }
}
