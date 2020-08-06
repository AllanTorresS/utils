package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.PrecoBase;
import ipp.aci.boleia.dominio.PrecoMicromercado;
import ipp.aci.boleia.dominio.TipoCombustivel;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Armazena dados coletados antes do processamento da planilha de importacao de preco base em lote, para ganho de performance
 */
public class ContextoImportacaoPrecosBaseVo  implements IDadosIniciaisImportacaoLoteVo {

    private Map<Long, PontoDeVenda> pontosDeVendaIndexados;
    private Map<String, TipoCombustivel> tiposCombustivelIndexados;
    private Map<String, PrecoMicromercado> precosMicroMercadosIndexados;
    private Map<String, PrecoBase> precosBaseAtuaisIndexados;
    private Date dataAtual;
    private Date dataEnvio;
    private Boolean requerAceiteRevenda;


    /**
     * Construtor default
     */
    public ContextoImportacaoPrecosBaseVo() {
        // construtor default
    }

    /**
     * Construtor do contexto de importacao de preco base em lote
     * @param pontosDeVenda A lista dos pontos de venda cadastrados
     * @param combustiveis A lista dos tipos de combustivel cadastrados
     * @param precosMicromercado A lista dos precos de micromercado cadastrados
     * @param precosBase A lista dos precos base atuais
     * @param dataAtual A data atual
     * @param dataEnvio A data de agendamento da importacao
     * @param requerAceite O flag indicativo sobre necessidade de aceite da revenda
     */
    public ContextoImportacaoPrecosBaseVo(List<PontoDeVenda> pontosDeVenda, List<TipoCombustivel> combustiveis, List<PrecoMicromercado> precosMicromercado, List<PrecoBase> precosBase, Date dataAtual, Date dataEnvio, Boolean requerAceite){
        this.pontosDeVendaIndexados = indexarPontosDeVenda(pontosDeVenda);
        this.tiposCombustivelIndexados = indexarTiposCombustivel(combustiveis);
        this.precosMicroMercadosIndexados = indexarPrecosMicromercado(precosMicromercado);
        this.precosBaseAtuaisIndexados = indexarPrecosBase(precosBase);
        this.dataAtual = dataAtual;
        this.dataEnvio = dataEnvio;
        this.requerAceiteRevenda = requerAceite;
    }

    /**
     * Obtem o ponto de venda pelo seu codigo profrotas
     *
     * @param chave O codigo pro-frotas do PV
     * @return O ponto de venda
     */
    public PontoDeVenda getPontoDeVendaPorChave(Long chave){
        return pontosDeVendaIndexados.get(chave);
    }

    /**
     * Obtem o Tipo Combustivel pela sua descricao
     *
     * @param descricao A descricao do Tipo Combustivel
     * @return O Tipo Combustivel
     */
    public TipoCombustivel getTipoCombustivelPorDescricao(String descricao){
        return tiposCombustivelIndexados.get(descricao);
    }

    /**
     * Obtem o preco micromercado a partir dos identificadores do micromercado e do tipo de combustivel
     * @param idMicromercado O identificador do micromercado
     * @param idTipoCombustivel O identificador do tipo de combustivel
     * @return O preco de micromercado
     */
    public PrecoMicromercado getPrecoMicromercado(Long idMicromercado, Long idTipoCombustivel){
        return precosMicroMercadosIndexados.get(criarChaveMicromercadoCombustivel(idMicromercado, idTipoCombustivel));
    }

    /**
     * Obtem o preco base atual a partir do novo preco base
     *
     * @param precoBase O novo preco base
     * @return O preco base atual
     */
    public PrecoBase getPrecoBaseAtualPorNovoPrecoBase(PrecoBase precoBase){
        return precosBaseAtuaisIndexados.get(criarChavePontoVendaPrecoMicromercado(precoBase));
    }

    /**
     * Dada uma lista de preco base, gera um indice a partir do codigo do PV e da chave do preco de micromercado associado
     * @param precosBase A lista de precos base a ser indexada
     * @return O Indice
     */
    private Map<String,PrecoBase> indexarPrecosBase(List<PrecoBase> precosBase) {
        Map<String, PrecoBase> indice = new HashMap<>();
        if(precosBase != null){
            for(PrecoBase p : precosBase){
                indice.put(criarChavePontoVendaPrecoMicromercado(p), p);
            }
        }
        return indice;
    }

    /**
     * Gera um string representando a chave de um preco base
     * @param p O preco Base para geracao da chave
     * @return A string resultante da concatenacao do id do PV com o id do preco de micromercado associado
     */
    private String criarChavePontoVendaPrecoMicromercado(PrecoBase p) {
        Long idPV;
        if(p.getIdPontoVenda() != null){
            idPV = p.getIdPontoVenda();
        } else{
            idPV = p.getPontoVenda().getId();
        }
        return String.format("%d_%d", idPV, p.getPrecoMicromercado().getId());
    }

    /**
     * Dada uma lista de precos de micromercado, indexa-os por micromercado e tipo combustiovel
     *
     * @param precosMicromercado A lista de precos de micromercado vigentes
     * @return O indice
     */
    private Map<String,PrecoMicromercado> indexarPrecosMicromercado(List<PrecoMicromercado> precosMicromercado) {
        Map<String, PrecoMicromercado> indice = new HashMap<>();
        if(precosMicromercado != null){
            for(PrecoMicromercado p : precosMicromercado){
                indice.put(criarChaveMicromercadoCombustivel(p), p);
            }
        }
        return indice;
    }

    /**
     * Dado um preco de micromercado, gera uma string com a concatenacao do id do MMR com o id do tipo combustivel
     *
     * @param p O preco Micromercado para o qual sera gerada chave
     * @return A chave gerada em formato string
     */
    private String criarChaveMicromercadoCombustivel(PrecoMicromercado p) {
        if(p.getMicromercado()!=null && p.getTipoCombustivel()!=null) {
            return criarChaveMicromercadoCombustivel(p.getMicromercado().getId(), p.getTipoCombustivel().getId());
        }
        return null;
    }

    /**
     * Gera um string com a concatencao do id de micromercado com o id do combustivel
     * @param idMicromercado O identificador de micromercado
     * @param idCombustivel O identificador do tipo de combustivel
     * @return A chave criada
     */
    private String criarChaveMicromercadoCombustivel(Long idMicromercado, Long idCombustivel){
        return String.format("%d_%d", idMicromercado, idCombustivel);
    }

    /**
     * Dada uma lista de combustiveis, indexa-a de acordo com sua descricao
     *
     * @param combustiveis A lista de combustiveis a ser indexada
     * @return O indice
     */
    private Map<String,TipoCombustivel> indexarTiposCombustivel(List<TipoCombustivel> combustiveis) {
        Map<String,TipoCombustivel> indice = new HashMap<>();
        if(combustiveis != null){
            for(TipoCombustivel c : combustiveis){
                indice.put(c.getDescricao(), c);
            }
        }
        return indice;
    }

    /**
     * Dada uma lista de PVs, indexa-a de acordo com o codigo pro-frotas
     *
     * @param pontosDeVenda a lista de PVs
     * @return O indice
     */
    private Map<Long,PontoDeVenda> indexarPontosDeVenda(List<PontoDeVenda> pontosDeVenda) {
        Map<Long, PontoDeVenda> indice = new HashMap<>();
        if(pontosDeVenda != null){
            for(PontoDeVenda p : pontosDeVenda){
                indice.put(p.getId(), p);
            }
        }
        return indice;
    }

    public Map<Long, PontoDeVenda> getPontosDeVendaIndexados() {
        return pontosDeVendaIndexados;
    }

    public void setPontosDeVendaIndexados(Map<Long, PontoDeVenda> pontosDeVendaIndexados) {
        this.pontosDeVendaIndexados = pontosDeVendaIndexados;
    }

    public Map<String, TipoCombustivel> getTiposCombustivelIndexados() {
        return tiposCombustivelIndexados;
    }

    public void setTiposCombustivelIndexados(Map<String, TipoCombustivel> tiposCombustivelIndexados) {
        this.tiposCombustivelIndexados = tiposCombustivelIndexados;
    }

    public Map<String, PrecoMicromercado> getPrecosMicroMercadosIndexados() {
        return precosMicroMercadosIndexados;
    }

    public void setPrecosMicroMercadosIndexados(Map<String, PrecoMicromercado> precosMicroMercadosIndexados) {
        this.precosMicroMercadosIndexados = precosMicroMercadosIndexados;
    }

    public Map<String, PrecoBase> getPrecosBaseAtuaisIndexados() {
        return precosBaseAtuaisIndexados;
    }

    public void setPrecosBaseAtuaisIndexados(Map<String, PrecoBase> precosBaseAtuaisIndexados) {
        this.precosBaseAtuaisIndexados = precosBaseAtuaisIndexados;
    }

    public Date getDataAtual() {
        return dataAtual;
    }

    public void setDataAtual(Date dataAtual) {
        this.dataAtual = dataAtual;
    }

    public Date getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(Date dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public Boolean getRequerAceiteRevenda() {
        return requerAceiteRevenda;
    }

    public void setRequerAceiteRevenda(Boolean requerAceiteRevenda) {
        this.requerAceiteRevenda = requerAceiteRevenda;
    }
}
