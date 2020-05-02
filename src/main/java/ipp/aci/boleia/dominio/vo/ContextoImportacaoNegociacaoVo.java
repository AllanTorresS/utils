package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.FrotaPontoVenda;
import ipp.aci.boleia.dominio.Negociacao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Armazena dados coletados antes do processamento das linhas da planilha, para
 * ganho de performance.
 */
public class ContextoImportacaoNegociacaoVo implements IDadosIniciaisImportacaoLoteVo {

    private Map<Long, Negociacao> negociacaoIndexadas;
    private Map<String, FrotaPontoVenda> frotaPtovIndexadas;

    /**
     * Construtor default
     */
    public ContextoImportacaoNegociacaoVo() {
        // construtor default
    }

    /**
     * Constroi o contexto para importacao dos negociacoes
     *
     * @param negociacoes A lista de negociacoes
     * @param frotaPtov A lista de frotas
     */
    public ContextoImportacaoNegociacaoVo(List<Negociacao> negociacoes, List<FrotaPontoVenda> frotaPtov) {
        this.negociacaoIndexadas = indexarNegociacoes(negociacoes);
        this.frotaPtovIndexadas = indexarFrotaPtov(frotaPtov);
    }

    /**
     * Dada uma lista de frotaPtov, cria um mapa indexando-os pela chave,
     * para performance na busca.
     *
     * @param frotaPtov a lista de frotaPtov
     * @return O indice
     */
    private Map<String, FrotaPontoVenda> indexarFrotaPtov(List<FrotaPontoVenda> frotaPtov) {
        Map<String, FrotaPontoVenda> indice = new HashMap<>();
        if(frotaPtov != null) {
            for(FrotaPontoVenda frotaPontoVenda : frotaPtov) {
                if (frotaPontoVenda != null && frotaPontoVenda.getPontoVenda() != null && frotaPontoVenda.getPontoVenda().getComponenteAreaAbastecimento() != null &&  frotaPontoVenda.getPontoVenda().getComponenteAreaAbastecimento().getCodigoPessoa() != null ){
                    indice.put(montarChaveFrotaPtov(frotaPontoVenda.getFrota().getCnpj(),
                            frotaPontoVenda.getPontoVenda().getComponenteAreaAbastecimento().getCodigoPessoa()),
                            frotaPontoVenda);
                }
            }
        }
        return indice;
    }

    /**
     * Monta a chave de index do mapa
     * @param cnpjFrota cnpj da frota
     * @param cnpjPtov cnpj do pv
     * @return chave do mapa de frotaPtov
     */
    private String montarChaveFrotaPtov(Long cnpjFrota, Long cnpjPtov) {
        return cnpjFrota + "_" + cnpjPtov;
    }

    /**
     * Dada uma lista de negociacoes, cria um mapa indexando-os pela chave,
     * para performance na busca.
     *
     * @param negociacoes a lista de negociacoes
     * @return O indice
     */
    private Map<Long,Negociacao> indexarNegociacoes(List<Negociacao> negociacoes) {
        Map<Long,Negociacao> indice = new HashMap<>();
        if(negociacoes != null) {
            for(Negociacao negociacao : negociacoes) {
                indice.put(negociacao.getFrotaPtov().getId(), negociacao);
            }
        }
        return indice;
    }

    /**
     * Obtem a frotaPtov por cnpj da frota e pv
     * @param cnpjFrota da negociacao
     * @param cnpjPv da negociacao
     * @return A frotaPtov da negociacao
     */
    public FrotaPontoVenda getFrotaPtovPorCnpjFrotaCnpjPv(Long cnpjFrota, Long cnpjPv) {
        return frotaPtovIndexadas.get(montarChaveFrotaPtov(cnpjFrota,cnpjPv));
    }

    /**
     * Obtem a negociacao por chave
     * @param idFrotaPtov da negociacao
     * @return A negociacao da idFrotaPtov
     */
    public Negociacao getNegociacaoPorFrotaPtov(Long idFrotaPtov) {
        return negociacaoIndexadas.get(idFrotaPtov);
    }

    public Map<Long, Negociacao> getNegociacaoIndexadas() {
        return negociacaoIndexadas;
    }

    public void setNegociacaoIndexadas(Map<Long, Negociacao> negociacaoIndexadas) {
        this.negociacaoIndexadas = negociacaoIndexadas;
    }

    public Map<String, FrotaPontoVenda> getFrotaPtovIndexadas() {
        return frotaPtovIndexadas;
    }

    public void setFrotaPtovIndexadas(Map<String, FrotaPontoVenda> frotaPtovIndexadas) {
        this.frotaPtovIndexadas = frotaPtovIndexadas;
    }
}