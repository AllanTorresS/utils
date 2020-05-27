
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consultarCadastroGeralJDEReq", propOrder = {
    "nome",
    "filial",
    "codigoClassificacaoIndustrial",
    "codigoIdioma",
    "indTipoCadastro",
    "codigoCadastro",
    "codigoLongoCadastro",
    "cpfCnpj",
    "cep",
    "municipio",
    "siglaEstado",
    "siglaPais",
    "gerenciaVenda",
    "coordVenda",
    "zonaVenda",
    "gerenciaComercial",
    "tipoNegocio",
    "distribuicaoLubes",
    "localPagamento",
    "garantia",
    "segmentoMercadoria",
    "micromercado",
    "cargoFuncionario",
    "lotacaoFuncionario",
    "previsaoLubes",
    "tipoComponente",
    "limiteTolerancia",
    "motivoInativacao",
    "perfilPosto",
    "propriedadeTerreno",
    "localizacao",
    "tipoImagem",
    "tipoFornecedor",
    "statusFornecedor",
    "empresa",
    "beneficioFiscal",
    "emprContabBenef",
    "repasseFrete",
    "coligada",
    "agenteEconomicoANP",
    "grupoSuspensao",
    "situacaoTributaria"
})
public class ConsultarCadastroGeralJDEReq
    extends EnsRequest
{

    protected String nome;
    protected String filial;
    protected String codigoClassificacaoIndustrial;
    protected String codigoIdioma;
    protected String indTipoCadastro;
    protected Long codigoCadastro;
    protected String codigoLongoCadastro;
    protected String cpfCnpj;
    protected String cep;
    protected String municipio;
    protected String siglaEstado;
    protected String siglaPais;
    protected String gerenciaVenda;
    protected String coordVenda;
    protected String zonaVenda;
    protected String gerenciaComercial;
    protected String tipoNegocio;
    protected String distribuicaoLubes;
    protected String localPagamento;
    protected String garantia;
    protected String segmentoMercadoria;
    protected String micromercado;
    protected String cargoFuncionario;
    protected String lotacaoFuncionario;
    protected String previsaoLubes;
    protected String tipoComponente;
    protected String limiteTolerancia;
    protected String motivoInativacao;
    protected String perfilPosto;
    protected String propriedadeTerreno;
    protected String localizacao;
    protected String tipoImagem;
    protected String tipoFornecedor;
    protected String statusFornecedor;
    protected String empresa;
    protected String beneficioFiscal;
    protected String emprContabBenef;
    protected String repasseFrete;
    protected String coligada;
    protected String agenteEconomicoANP;
    protected String grupoSuspensao;
    protected String situacaoTributaria;

    
    public String getNome() {
        return nome;
    }

    
    public void setNome(String value) {
        this.nome = value;
    }

    
    public String getFilial() {
        return filial;
    }

    
    public void setFilial(String value) {
        this.filial = value;
    }

    
    public String getCodigoClassificacaoIndustrial() {
        return codigoClassificacaoIndustrial;
    }

    
    public void setCodigoClassificacaoIndustrial(String value) {
        this.codigoClassificacaoIndustrial = value;
    }

    
    public String getCodigoIdioma() {
        return codigoIdioma;
    }

    
    public void setCodigoIdioma(String value) {
        this.codigoIdioma = value;
    }

    
    public String getIndTipoCadastro() {
        return indTipoCadastro;
    }

    
    public void setIndTipoCadastro(String value) {
        this.indTipoCadastro = value;
    }

    
    public Long getCodigoCadastro() {
        return codigoCadastro;
    }

    
    public void setCodigoCadastro(Long value) {
        this.codigoCadastro = value;
    }

    
    public String getCodigoLongoCadastro() {
        return codigoLongoCadastro;
    }

    
    public void setCodigoLongoCadastro(String value) {
        this.codigoLongoCadastro = value;
    }

    
    public String getCpfCnpj() {
        return cpfCnpj;
    }

    
    public void setCpfCnpj(String value) {
        this.cpfCnpj = value;
    }

    
    public String getCep() {
        return cep;
    }

    
    public void setCep(String value) {
        this.cep = value;
    }

    
    public String getMunicipio() {
        return municipio;
    }

    
    public void setMunicipio(String value) {
        this.municipio = value;
    }

    
    public String getSiglaEstado() {
        return siglaEstado;
    }

    
    public void setSiglaEstado(String value) {
        this.siglaEstado = value;
    }

    
    public String getSiglaPais() {
        return siglaPais;
    }

    
    public void setSiglaPais(String value) {
        this.siglaPais = value;
    }

    
    public String getGerenciaVenda() {
        return gerenciaVenda;
    }

    
    public void setGerenciaVenda(String value) {
        this.gerenciaVenda = value;
    }

    
    public String getCoordVenda() {
        return coordVenda;
    }

    
    public void setCoordVenda(String value) {
        this.coordVenda = value;
    }

    
    public String getZonaVenda() {
        return zonaVenda;
    }

    
    public void setZonaVenda(String value) {
        this.zonaVenda = value;
    }

    
    public String getGerenciaComercial() {
        return gerenciaComercial;
    }

    
    public void setGerenciaComercial(String value) {
        this.gerenciaComercial = value;
    }

    
    public String getTipoNegocio() {
        return tipoNegocio;
    }

    
    public void setTipoNegocio(String value) {
        this.tipoNegocio = value;
    }

    
    public String getDistribuicaoLubes() {
        return distribuicaoLubes;
    }

    
    public void setDistribuicaoLubes(String value) {
        this.distribuicaoLubes = value;
    }

    
    public String getLocalPagamento() {
        return localPagamento;
    }

    
    public void setLocalPagamento(String value) {
        this.localPagamento = value;
    }

    
    public String getGarantia() {
        return garantia;
    }

    
    public void setGarantia(String value) {
        this.garantia = value;
    }

    
    public String getSegmentoMercadoria() {
        return segmentoMercadoria;
    }

    
    public void setSegmentoMercadoria(String value) {
        this.segmentoMercadoria = value;
    }

    
    public String getMicromercado() {
        return micromercado;
    }

    
    public void setMicromercado(String value) {
        this.micromercado = value;
    }

    
    public String getCargoFuncionario() {
        return cargoFuncionario;
    }

    
    public void setCargoFuncionario(String value) {
        this.cargoFuncionario = value;
    }

    
    public String getLotacaoFuncionario() {
        return lotacaoFuncionario;
    }

    
    public void setLotacaoFuncionario(String value) {
        this.lotacaoFuncionario = value;
    }

    
    public String getPrevisaoLubes() {
        return previsaoLubes;
    }

    
    public void setPrevisaoLubes(String value) {
        this.previsaoLubes = value;
    }

    
    public String getTipoComponente() {
        return tipoComponente;
    }

    
    public void setTipoComponente(String value) {
        this.tipoComponente = value;
    }

    
    public String getLimiteTolerancia() {
        return limiteTolerancia;
    }

    
    public void setLimiteTolerancia(String value) {
        this.limiteTolerancia = value;
    }

    
    public String getMotivoInativacao() {
        return motivoInativacao;
    }

    
    public void setMotivoInativacao(String value) {
        this.motivoInativacao = value;
    }

    
    public String getPerfilPosto() {
        return perfilPosto;
    }

    
    public void setPerfilPosto(String value) {
        this.perfilPosto = value;
    }

    
    public String getPropriedadeTerreno() {
        return propriedadeTerreno;
    }

    
    public void setPropriedadeTerreno(String value) {
        this.propriedadeTerreno = value;
    }

    
    public String getLocalizacao() {
        return localizacao;
    }

    
    public void setLocalizacao(String value) {
        this.localizacao = value;
    }

    
    public String getTipoImagem() {
        return tipoImagem;
    }

    
    public void setTipoImagem(String value) {
        this.tipoImagem = value;
    }

    
    public String getTipoFornecedor() {
        return tipoFornecedor;
    }

    
    public void setTipoFornecedor(String value) {
        this.tipoFornecedor = value;
    }

    
    public String getStatusFornecedor() {
        return statusFornecedor;
    }

    
    public void setStatusFornecedor(String value) {
        this.statusFornecedor = value;
    }

    
    public String getEmpresa() {
        return empresa;
    }

    
    public void setEmpresa(String value) {
        this.empresa = value;
    }

    
    public String getBeneficioFiscal() {
        return beneficioFiscal;
    }

    
    public void setBeneficioFiscal(String value) {
        this.beneficioFiscal = value;
    }

    
    public String getEmprContabBenef() {
        return emprContabBenef;
    }

    
    public void setEmprContabBenef(String value) {
        this.emprContabBenef = value;
    }

    
    public String getRepasseFrete() {
        return repasseFrete;
    }

    
    public void setRepasseFrete(String value) {
        this.repasseFrete = value;
    }

    
    public String getColigada() {
        return coligada;
    }

    
    public void setColigada(String value) {
        this.coligada = value;
    }

    
    public String getAgenteEconomicoANP() {
        return agenteEconomicoANP;
    }

    
    public void setAgenteEconomicoANP(String value) {
        this.agenteEconomicoANP = value;
    }

    
    public String getGrupoSuspensao() {
        return grupoSuspensao;
    }

    
    public void setGrupoSuspensao(String value) {
        this.grupoSuspensao = value;
    }

    
    public String getSituacaoTributaria() {
        return situacaoTributaria;
    }

    
    public void setSituacaoTributaria(String value) {
        this.situacaoTributaria = value;
    }

}
