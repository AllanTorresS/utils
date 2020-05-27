package ipp.aci.boleia.dominio;


import ipp.aci.boleia.dominio.enums.StatusAtivacao;
import ipp.aci.boleia.dominio.enums.StatusHabilitacaoPontoVenda;
import ipp.aci.boleia.dominio.enums.TiposBandeiras;
import ipp.aci.boleia.dominio.interfaces.IExclusaoLogica;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceRevendedor;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Formula;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

/**
 * Representa a tabela de Ponto de Venda
 */
@Entity
@Audited
@Table(name = "PONTO_VENDA")
public class PontoDeVenda implements IPersistente, IExclusaoLogica, IPertenceRevendedor {

    /**
     * Foi necessário a utilização de uma subquery em um @Formula para que fosse possível realizar a ordenação paginada
     * do ponto de venda por cnpj sem grandes mudanças na estrutura de pesquisa via Criteria.
     */
    private static final String CNPJ_FORMULA = "(SELECT c.CD_PESSOA FROM BOLEIA_SCHEMA.COMPONENTE c JOIN BOLEIA_SCHEMA.ATIVIDADE_COMPONENTE ac ON c.CD_ATIV_COMP = ac.CD_ATIV_COMP WHERE c.CD_PTOV = CD_PTOV AND (ac.CD_ATIV_COMP_CORP = 1 OR ac.CD_ATIV_COMP_CORP = 99))";

    private static final long serialVersionUID = -6358128598442202483L;

    @Id
    @Column(name = "CD_PTOV")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PONTO_VENDA")
    @SequenceGenerator(name = "SEQ_PONTO_VENDA", sequenceName = "SEQ_PONTO_VENDA", allocationSize = 1)
    private Long id;

    @NotNull
    @Max(99999999L)
    @Column(name = "CD_PTOV_CORP")
    private Long codigoCorporativo;

    @NotAudited
    @Formula(CNPJ_FORMULA)
    @Basic(fetch = FetchType.LAZY)
    private Long cnpj;

    @NotNull
    @Size(max=40)
    @Column(name = "NM_PTOV")
    private String nome;

    @NotNull
    @Max(9L)
    @Column(name = "ID_STATUS")
    private Integer status;

    @NotAudited
    @Formula(StatusAtivacao.DECODE_FORMULA)
    private String statusConvertido;

    @NotNull
    @Max(9L)
    @Column(name = "ID_STATUS_CORP")
    private Integer statusCorporativo;

    @Max(99999999)
    @Column(name = "CD_PTOV_JDE")
    private Integer numeroJde;

    @Max(999999)
    @Column(name = "CD_PTOV_ABADI")
    private Integer numeroAbadi;

    @Max(99999999)
    @Column(name = "NO_CEP")
    private Integer cep;

    @Size(max=150)
    @Column(name = "DS_END")
    private String logradouroEndereco;

    @Max(999999)
    @Column(name = "NO_END")
    private Integer numeroEndereco;

    @Size(max=50)
    @Column(name = "DS_COMPL")
    private String complementoEndereco;

    @Size(max=40)
    @Column(name = "NM_BAIRRO")
    private String bairro;

    @Size(max=50)
    @Column(name = "NM_MUNIC")
    private String municipio;

    @Size(max=2)
    @Column(name = "SG_UF")
    private String uf;

    @DecimalMax("90.0")
    @DecimalMin("-90.0")
    @Digits(integer = 4, fraction = 12)
    @Column(name = "QT_GRAU_LATIT")
    private BigDecimal latitude;

    @DecimalMax("180.0")
    @DecimalMin("-180.0")
    @Digits(integer = 4, fraction = 12)
    @Column(name = "QT_GRAU_LONGIT")
    private BigDecimal longitude;

    @Size(max=600)
    @Column(name = "NM_ASSESSORES_VAREJO")
    private String assessoresVarejo;

    @Size(max=30)
    @Column(name = "DS_TIPO_LOC")
    private String tipoLocalizacao;

    @Size(max=30)
    @Column(name = "DS_TIPO_PERFIL_VND")
    private String perfilVenda;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name="FOTO_PTOV", joinColumns= {@JoinColumn(name="CD_PTOV")}, inverseJoinColumns= {@JoinColumn(name="CD_ARQUIVO")})
    private List<Arquivo> fotos;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="USUARIO_PONTO_VENDA", joinColumns={@JoinColumn(name="CD_PTOV")}, inverseJoinColumns={@JoinColumn(name="CD_USUARIO")})
    private List<Usuario> usuarios;

    @OneToMany(mappedBy = "pontoVenda", fetch = FetchType.LAZY)
    @OrderBy("ID_ADICIONAL,NM_CONTATO")
    private List<ContatoPontoDeVenda> contatos;

    @OneToMany(mappedBy = "pontoDeVenda", fetch = FetchType.LAZY)
    private List<Componente> componentes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CD_MICROMERCADO")
    private Micromercado micromercado;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pontoVenda")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<FrotaPontoVenda> negociacoes;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="QUEST_RESP_PTOV", joinColumns= {@JoinColumn(name="CD_PTOV")}, inverseJoinColumns= {@JoinColumn(name="CD_QUEST_PERG_OPCAO")})
    private List<QuestionarioPerguntaOpcao> respostaQuestionario;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "pontoDeVenda")
    private PontoDeVendaAvaliacao avaliacao;

    @Column(name = "ID_EXCLUIDO")
    private Boolean excluido;

    @Column(name = "ID_HABIL")
    private Integer statusHabilitacao;

    @Column(name = "ID_RODO_REDE")
    private Boolean rodoRede;

    @Column(name = "ID_FUNC_24H")
    private Boolean funcionamento24h;

    @NotAudited
    @Formula(StatusHabilitacaoPontoVenda.DECODE_FORMULA)
    private String situacaoConvertido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_REDE")
    private Rede rede;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    @Column(name = "CD_PTOV_JDE_INT")
    private Integer numeroJdeInterno;

    @Column(name = "ID_ATUALIZAR_JDE")
    private Boolean atualizarJde;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="CONTA_BANCARIA_PTOV", joinColumns= {@JoinColumn(name="CD_PTOV")}, inverseJoinColumns= {@JoinColumn(name="CD_CONTA_BANCARIA")})
    private List<ContaBancaria> contasBancarias;

    @Column(name = "DT_PENDENCIA_ACEITE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPendenciaAceite;

    @Column(name = "DT_ACEITE_TERMOS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAceiteTermos;

    @Column(name = "MDR")
    @DecimalMin("00.00")
    @DecimalMax("99.99")
    private BigDecimal mdr;

    @Column(name = "NM_CONTATO_SOLUCAO")
    private String nomeContatoSolucao;

    @Column(name = "DS_EMAIL_SOLUCAO")
    private String emailSolucao;

    @Column(name = "NO_TELEFONE")
    private String telefoneSolucao;

    @Column(name = "VA_TAXA_ADESAO")
    private BigDecimal taxaAdesao;

    @Column(name = "VA_MENSALIDADE")
    private BigDecimal mensalidade;

    @Column(name = "NO_PRAZO_PAGAMENTO")
    private Integer prazoPagamento;

    @Column(name = "DS_PRIMEIRO_PAGAMENTO")
    @Size(max=50)
    private String dataPrimeiroPagamento;

    @Column(name = "DS_PRAZO_CONTRATO")
    @Size(max=50)
    private String dataPrazoContrato;

    @Column(name = "ID_DISP_RECEBIMENTO")
    private Boolean statusDispositivoRecebimento;

    @Column(name= "VA_DISPOSITIVO")
    private BigDecimal valorDispositivo;

    @Column(name = "CD_MUNIC_IBGE")
    private String codigoIBGE;

    @Column(name = "NO_SEQ_JDE")
    private Long numeroSequencialJde;

    @Column(name = "ID_PERMISSAO_PRECO_POSTO")
    private Boolean permissaoPrecoPosto;

    @NotAudited
    @Formula("CD_MICROMERCADO")
    private Long idMicromercado;

    @Column(name = "ID_MODIF_LAT_LONG")
    private Boolean idAlterouLocalizacao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pontoVenda")
    private List<PrecoBase> precosBase;
    
    @Column(name = "DS_TOKEN_CREDENCIAMENTO")
    private String tokenCredenciamento;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_TC")
    private TermosContrato termosContrato;
    
    @Column(name = "ID_CONTRATO_BAIXADO")
    private Boolean contratoBaixado;
    
    @Column(name = "CD_LEAD_CREDENCIAMENTO")
    private String idLeadCredenciamento;
    
    @Column(name = "CD_CPF_RESP_LEGAL")
    private Long cpfResponsavelLegal;
    
    @Column(name = "NM_RESP_LEGAL")
    private String nomeResponsavelLegal;

    @Column(name = "DS_EMAIL_RESP_LEGAL")
    private String emailResponsavelLegal;

    @Column(name = "NO_TELEFONE_RESP_LEGAL")
    private String telefoneResponsavelLegal;
    
    public PontoDeVenda() {
        // construtor default
    }

    /**
     * Construtor a partir do id do Ponto de Venda
     * @param idPontoVenda id do Ponto de Venda
     */
    public PontoDeVenda(Long idPontoVenda) {
        this.id = idPontoVenda;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Boolean getExcluido() {
        return excluido;
    }

    @Override
    public void setExcluido(Boolean excluido) {
        this.excluido = excluido;
    }

    public Long getCodigoCorporativo() {
        return codigoCorporativo;
    }

    public void setCodigoCorporativo(Long codigoCorporativo) {
        this.codigoCorporativo = codigoCorporativo;
    }

    public Long getCnpj() {
        return cnpj;
    }

    public void setCnpj(Long cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusConvertido() {
        return statusConvertido;
    }

    public void setStatusConvertido(String statusConvertido) {
        this.statusConvertido = statusConvertido;
    }

    public Integer getStatusCorporativo() {
        return statusCorporativo;
    }

    public void setStatusCorporativo(Integer statusCorporativo) {
        this.statusCorporativo = statusCorporativo;
    }

    public Integer getNumeroJde() {
        return numeroJde;
    }

    public void setNumeroJde(Integer numeroJde) {
        this.numeroJde = numeroJde;
    }

    public Integer getNumeroAbadi() {
        return numeroAbadi;
    }

    public void setNumeroAbadi(Integer numeroAbadi) {
        this.numeroAbadi = numeroAbadi;
    }

    public Integer getCep() {
        return cep;
    }

    public void setCep(Integer cep) {
        this.cep = cep;
    }

    public String getLogradouroEndereco() {
        return logradouroEndereco;
    }

    public void setLogradouroEndereco(String logradouroEndereco) {
        this.logradouroEndereco = logradouroEndereco;
    }

    public Integer getNumeroEndereco() {
        return numeroEndereco;
    }

    public void setNumeroEndereco(Integer numeroEndereco) {
        this.numeroEndereco = numeroEndereco;
    }

    public String getComplementoEndereco() {
        return complementoEndereco;
    }

    public void setComplementoEndereco(String complementoEndereco) {
        this.complementoEndereco = complementoEndereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public List<Arquivo> getFotos() {
        return fotos;
    }

    public void setFotos(List<Arquivo> fotos) {
        this.fotos = fotos;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<ContatoPontoDeVenda> getContatos() {
        return contatos;
    }

    public void setContatos(List<ContatoPontoDeVenda> contatos) {
        this.contatos = contatos;
    }

    public List<Componente> getComponentes() {
        return componentes;
    }

    public void setComponentes(List<Componente> componentes) {
        this.componentes = componentes;
    }

    public List<FrotaPontoVenda> getNegociacoes() {
        return negociacoes;
    }

    public void setNegociacoes(List<FrotaPontoVenda> negociacoes) {
        this.negociacoes = negociacoes;
    }

    public List<QuestionarioPerguntaOpcao> getRespostaQuestionario() {
        return respostaQuestionario;
    }

    public void setRespostaQuestionario(List<QuestionarioPerguntaOpcao> respostaQuestionario) {
        this.respostaQuestionario = respostaQuestionario;
    }

    public String getAssessoresVarejo() {
        return assessoresVarejo;
    }

    public void setAssessoresVarejo(String assessoresVarejo) {
        this.assessoresVarejo = assessoresVarejo;
    }

    public String getTipoLocalizacao() {
        return tipoLocalizacao;
    }

    public void setTipoLocalizacao(String tipoLocalizacao) {
        this.tipoLocalizacao = tipoLocalizacao;
    }

    public String getPerfilVenda() {
        return perfilVenda;
    }

    public void setPerfilVenda(String perfilVenda) {
        this.perfilVenda = perfilVenda;
    }

    public Micromercado getMicromercado() {
        return micromercado;
    }

    public void setMicromercado(Micromercado micromercado) {
        this.micromercado = micromercado;
    }

    public Rede getRede() {
        return rede;
    }

    public void setRede(Rede rede) {
        this.rede = rede;
    }

    public Integer getNumeroJdeInterno() {
        return numeroJdeInterno;
    }

    public void setNumeroJdeInterno(Integer numeroJdeInterno) {
        this.numeroJdeInterno = numeroJdeInterno;
    }

    public Boolean getAtualizarJde() {
        return atualizarJde;
    }

    public void setAtualizarJde(Boolean atualizarJde) {
        this.atualizarJde = atualizarJde;
    }

    public Boolean getRodoRede() {
        return rodoRede;
    }

    public void setRodoRede(Boolean rodoRede) {
        this.rodoRede = rodoRede;
    }

    public Boolean getFuncionamento24h() {
        return funcionamento24h;
    }

    public void setFuncionamento24h(Boolean funcionamento24h) {
        this.funcionamento24h = funcionamento24h;
    }

    public Integer getStatusHabilitacao() {
        return statusHabilitacao;
    }

    public void setStatusHabilitacao(Integer statusHabilitacao) {
        this.statusHabilitacao = statusHabilitacao;
    }

    public List<ContaBancaria> getContasBancarias() {
        return contasBancarias;
    }

    public void setContasBancarias(List<ContaBancaria> contasBancarias) {
        this.contasBancarias = contasBancarias;
    }

    public Date getDataAceiteTermos() {
        return dataAceiteTermos;
    }

    public void setDataAceiteTermos(Date dataAceiteTermos) {
        this.dataAceiteTermos = dataAceiteTermos;
    }

    public Date getDataPendenciaAceite() {
        return dataPendenciaAceite;
    }

    public void setDataPendenciaAceite(Date dataPendenciaAceite) {
        this.dataPendenciaAceite = dataPendenciaAceite;
    }

    public BigDecimal getMdr() {
        return mdr;
    }

    public void setMdr(BigDecimal mdr) {
        this.mdr = mdr;
    }

    public String getNomeContatoSolucao() {
        return nomeContatoSolucao;
    }

    public void setNomeContatoSolucao(String nomeContatoSolucao) {
        this.nomeContatoSolucao = nomeContatoSolucao;
    }

    public String getEmailSolucao() {
        return emailSolucao;
    }

    public void setEmailSolucao(String emailSolucao) {
        this.emailSolucao = emailSolucao;
    }

    public String getTelefoneSolucao() {
        return telefoneSolucao;
    }

    public void setTelefoneSolucao(String telefoneSolucao) {
        this.telefoneSolucao = telefoneSolucao;
    }

    public BigDecimal getTaxaAdesao() {
        return taxaAdesao;
    }

    public void setTaxaAdesao(BigDecimal taxaAdesao) {
        this.taxaAdesao = taxaAdesao;
    }

    public BigDecimal getMensalidade() {
        return mensalidade;
    }

    public void setMensalidade(BigDecimal mensalidade) {
        this.mensalidade = mensalidade;
    }

    public Integer getPrazoPagamento() {
        return prazoPagamento;
    }

    public void setPrazoPagamento(Integer prazoPagamento) {
        this.prazoPagamento = prazoPagamento;
    }

    public String getDataPrimeiroPagamento() {
        return dataPrimeiroPagamento;
    }

    public void setDataPrimeiroPagamento(String dataPrimeiroPagamento) {
        this.dataPrimeiroPagamento = dataPrimeiroPagamento;
    }

    public String getDataPrazoContrato() {
        return dataPrazoContrato;
    }

    public void setDataPrazoContrato(String dataPrazoContrato) {
        this.dataPrazoContrato = dataPrazoContrato;
    }

    public Boolean getStatusDispositivoRecebimento() {
        return statusDispositivoRecebimento;
    }

    public void setStatusDispositivoRecebimento(Boolean statusDispositivoRecebimento) {
        this.statusDispositivoRecebimento = statusDispositivoRecebimento;
    }

    public String getCodigoIBGE() {
        return codigoIBGE;
    }

    public void setCodigoIBGE(String codigoIBGE) {
        this.codigoIBGE = codigoIBGE;
    }

    public Long getNumeroSequencialJde() {
        return numeroSequencialJde;
    }

    public void setNumeroSequencialJde(Long numeroSequencialJde) {
        this.numeroSequencialJde = numeroSequencialJde;
    }

    public PontoDeVendaAvaliacao getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(PontoDeVendaAvaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }

    public List<PrecoBase> getPrecosBase() { return precosBase; }

    public void setPrecosBase(List<PrecoBase> precosBase) { this.precosBase = precosBase; }

    public Boolean getIdAlterouLocalizacao() {
        return idAlterouLocalizacao;
    }

    public void setIdAlterouLocalizacao(Boolean idAlterouLocalizacao) {
        this.idAlterouLocalizacao = idAlterouLocalizacao;
    }
    
	public String getTokenCredenciamento() {
		return tokenCredenciamento;
	}

	public void setTokenCredenciamento(String tokenCredenciamento) {
		this.tokenCredenciamento = tokenCredenciamento;
	}

	public TermosContrato getTermosContrato() {
		return termosContrato;
	}

	public void setTermosContrato(TermosContrato termosContrato) {
		this.termosContrato = termosContrato;
	}
	
	public Boolean getContratoBaixado() {
		return contratoBaixado;
	}

	public void setContratoBaixado(Boolean contratoBaixado) {
		this.contratoBaixado = contratoBaixado;
	}
	
	public String getIdLeadCredenciamento() {
		return idLeadCredenciamento;
	}

	public void setIdLeadCredenciamento(String idLeadCredenciamento) {
		this.idLeadCredenciamento = idLeadCredenciamento;
	}

	public Long getCpfResponsavelLegal() {
		return cpfResponsavelLegal;
	}

	public void setCpfResponsavelLegal(Long cpfResponsavelLegal) {
		this.cpfResponsavelLegal = cpfResponsavelLegal;
	}

	public String getNomeResponsavelLegal() {
		return nomeResponsavelLegal;
	}

	public void setNomeResponsavelLegal(String nomeResponsavelLegal) {
		this.nomeResponsavelLegal = nomeResponsavelLegal;
	}

	public String getEmailResponsavelLegal() {
		return emailResponsavelLegal;
	}

	public void setEmailResponsavelLegal(String emailResponsavelLegal) {
		this.emailResponsavelLegal = emailResponsavelLegal;
	}

	public String getTelefoneResponsavelLegal() {
		return telefoneResponsavelLegal;
	}

	public void setTelefoneResponsavelLegal(String telefoneResponsavelLegal) {
		this.telefoneResponsavelLegal = telefoneResponsavelLegal;
	}

	/**
     * @return componente que representa a area de abastecimento
     */
    @Transient
    public Componente getComponenteAreaAbastecimento() {
        return this.getComponentes().stream()
                .filter(c ->  AtividadeComponente.obterCodigosAreaAbastecimento().contains(c.getAtividadeComponente().getCodigoCorporativo()))
                .findFirst()
                .orElse(null);
    }

    @Transient
    @Override
    public List<PontoDeVenda> getPontosDeVenda() {
        return Collections.singletonList(this);
    }

    public String getSituacaoConvertido() {
        return situacaoConvertido;
    }

    public void setSituacaoConvertido(String situacaoConvertido) {
        this.situacaoConvertido = situacaoConvertido;
    }

    @Transient
    public String getLogradouroENumero() {
        return this.logradouroEndereco + ", " + this.numeroEndereco;
    }

    @Transient
    public boolean isHabilitado() {
        return StatusHabilitacaoPontoVenda.HABILITADO.getValue().equals(this.statusHabilitacao) && (this.excluido == null || !this.excluido);
    }

    /**
     * Monta o nome de apresentacao do ponto de venda, formato pelo
     * CPF ou CNPJ da área de abastecimento, caso exista, concatenado ao
     * nome do ponto de venda.
     * @return O nome de apresentacao do ponto de venda
     */
    @Transient
    public String getNomeApresentacao() {
        Componente abast = getComponenteAreaAbastecimento();
        String codigoPessoa = "";
        if(abast !=null && abast.getCodigoPessoa() != null) {
            if(abast.getTipoPessoa().equals(Componente.TIPO_PESSOA_PJ)) {
                codigoPessoa = UtilitarioFormatacao.formatarCnpjApresentacao(abast.getCodigoPessoa());
            } else {
                codigoPessoa = UtilitarioFormatacao.formatarCpfApresentacao(abast.getCodigoPessoa());
            }
            codigoPessoa += " - ";
        }
        return codigoPessoa + nome;
    }

	/**
	 * @return the valorDispositivo
	 */
	public BigDecimal getValorDispositivo() {
		return valorDispositivo;
	}

	/**
	 * @param valorDispositivo the valorDispositivo to set
	 */
	public void setValorDispositivo(BigDecimal valorDispositivo) {
		this.valorDispositivo = valorDispositivo;
	}

    public Long getIdMicromercado() { return idMicromercado; }

    public void setIdMicromercado(Long idMicromercado) { this.idMicromercado = idMicromercado; }

    public Boolean getPermissaoPrecoPosto() {
        return permissaoPrecoPosto;
    }

    public void setPermissaoPrecoPosto(Boolean permissaoPrecoPosto) {
        this.permissaoPrecoPosto = permissaoPrecoPosto;
    }

    @Transient
    public String getLatitudeString() {
        return UtilitarioFormatacao.formatarDecimal(latitude);
    }

    @Transient
    public String getLongitudeString() {
        return UtilitarioFormatacao.formatarDecimal(longitude);
    }

    /**
     * Obtém o nome do muncípio no formato "nome - uf"
     *
     * @return O nome do munícipio no formato desejado
     */
    @Transient
    public String getNomeMunicipioApresentacao() {
        StringJoiner joiner = new StringJoiner(" - ");
        joiner.add(this.municipio);
        joiner.add(this.uf);

        return joiner.toString();
    }

    /**
    * Informa se o ponto de venda é ou não bandeira branca
    *
    * @return se o ponto de venda é ou não bandeira branca
    */
    @Transient
    public Boolean isBandeiraBranca() {
        Componente areaDeAbastecimento = getComponenteAreaAbastecimento();
        if (areaDeAbastecimento == null) {
            return false;
        }

        Bandeira bandeira = areaDeAbastecimento.getBandeira();
        if (bandeira == null) {
            return false;
        }
        return bandeira.getCodigoCorporativo().equals(TiposBandeiras.BANDEIRA_BRANCA.getCodigoCorporativo());
    }

    /**
     * Informa se o ponto de venda é ou não bandeira ipiranga
     *
     * @return se o ponto de venda é ou não bandeira ipiranga
     */
    public Boolean isBandeiraIpiranga() {
        Componente areaDeAbastecimento = getComponenteAreaAbastecimento();
        if (areaDeAbastecimento == null) {
            return false;
        }

        Bandeira bandeira = areaDeAbastecimento.getBandeira();
        if (bandeira == null) {
            return false;
        }
        return bandeira.getCodigoCorporativo().equals(TiposBandeiras.IPIRANGA.getCodigoCorporativo());
    }
}
