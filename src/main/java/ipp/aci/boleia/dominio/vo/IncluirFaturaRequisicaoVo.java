package ipp.aci.boleia.dominio.vo;

import com.fasterxml.jackson.databind.ObjectMapper;
import ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws.IncluirJDEReq;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;

import java.util.Date;

/**
 * Contém dados da requisição enviada ao serviço de inclusão de cobrança do JDE
 */
public class IncluirFaturaRequisicaoVo {
    private Date dataRequisicao;
    private Long idCobranca;
    private String requisicao;

    /**
     * Construtor default
     */
    public IncluirFaturaRequisicaoVo() {
        // Construtor default
    }

    /**
     * Construtor
     *
     * @param idCobranca o id da cobrança sendo integrada
     * @param requisicao a requisição sendo enviada ao JDE
     * @param dataRequisicao a data de envio da requisição
     */
    public IncluirFaturaRequisicaoVo(Long idCobranca, IncluirJDEReq requisicao, Date dataRequisicao) {
        this.idCobranca = idCobranca;
        try {
            this.requisicao = new ObjectMapper().writeValueAsString(requisicao);
        } catch (Exception e) {
            throw new ExcecaoBoleiaRuntime(Erro.OBJETO_NAO_SERIALIZAVEL, e);
        }
        this.dataRequisicao = dataRequisicao;
    }

    public Date getDataRequisicao() {
        return dataRequisicao;
    }

    public void setDataRequisicao(Date dataRequisicao) {
        this.dataRequisicao = dataRequisicao;
    }

    public Long getIdCobranca() {
        return idCobranca;
    }

    public void setIdCobranca(Long idCobranca) {
        this.idCobranca = idCobranca;
    }

    public String getRequisicao() {
        return requisicao;
    }

    public void setRequisicao(String requisicao) {
        this.requisicao = requisicao;
    }
}
