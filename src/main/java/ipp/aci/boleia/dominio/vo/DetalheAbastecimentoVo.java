package ipp.aci.boleia.dominio.vo;


import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.UtilitarioFormatacaoData;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Classe com informações referentes à um detalhe de abastecimento para o aviso de débito
 */
public class DetalheAbastecimentoVo {

    private String idTransacao;
    private String dataRequisicao;
    private String nomePv;
    private String placaVeiculo;
    private String nomeMotorista;
    private String cpfMotorista;
    private String totalLitrosAbastecimento;
    private String valorUnitarioAbastecimento;
    private String nomeItemAbastecimento;
    private String valorTotal;
    private String valorDescontoTotal;

    /**
     * Serializacao json
     */
    public DetalheAbastecimentoVo() {
        // serializacao json
    }

    /**
     * Constroi o VO autorizacao partir de um abastecimento
     * @param autorizacao A autorizacao de pagamento
     */
    public DetalheAbastecimentoVo(AutorizacaoPagamento autorizacao) {
        this.setNomePv(autorizacao.getNomePv());
        this.setValorTotal(UtilitarioFormatacao.formatarDecimal(autorizacao.getValorTotal().setScale(2, RoundingMode.HALF_UP)));
        this.setPlacaVeiculo(autorizacao.getPlacaVeiculo());
        this.setNomeMotorista(autorizacao.getNomeMotorista());
        this.setCpfMotorista(UtilitarioFormatacao.formatarCpfApresentacao(autorizacao.getCpfMotorista()));
        this.setDataRequisicao(UtilitarioFormatacaoData.formatarDataCurta(autorizacao.getDataRequisicao()));
        this.setNomeItemAbastecimento(autorizacao.getNomeItemAbastecimento());
        this.setTotalLitrosAbastecimento(UtilitarioFormatacao.formatarDecimal(autorizacao.getTotalLitrosAbastecimento().setScale(3, RoundingMode.HALF_UP)));
        this.setValorUnitarioAbastecimento(UtilitarioFormatacao.formatarDecimal(autorizacao.getValorUnitarioAbastecimento().setScale(3, RoundingMode.HALF_UP)));
        this.setIdTransacao(UtilitarioFormatacao.obterDigitosMascara(autorizacao.getId()));
        if(autorizacao.getValorDescontoTotal() != null){
            if(BigDecimal.ZERO.equals(autorizacao.getValorDescontoTotal()) || BigDecimal.ZERO.compareTo(autorizacao.getValorDescontoTotal().setScale(2,RoundingMode.HALF_UP)) == 0){
              this.setValorDescontoTotal("");
            }
            else{
                this.setValorDescontoTotal(UtilitarioFormatacao.formatarDecimal(autorizacao.getValorDescontoTotal().setScale(2,RoundingMode.HALF_UP)));
            }
        }
    }

    public String getIdTransacao() {
        return idTransacao;
    }

    public void setIdTransacao(String idTransacao) {
        this.idTransacao = idTransacao;
    }

    public String getDataRequisicao() {
        return dataRequisicao;
    }

    public void setDataRequisicao(String dataRequisicao) {
        this.dataRequisicao = dataRequisicao;
    }

    public String getNomePv() {
        return nomePv;
    }

    public void setNomePv(String nomePv) {
        this.nomePv = nomePv;
    }

    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    public void setPlacaVeiculo(String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }

    public String getNomeMotorista() {
        return nomeMotorista;
    }

    public void setNomeMotorista(String nomeMotorista) {
        this.nomeMotorista = nomeMotorista;
    }

    public String getTotalLitrosAbastecimento() {
        return totalLitrosAbastecimento;
    }

    public void setTotalLitrosAbastecimento(String totalLitrosAbastecimento) {
        this.totalLitrosAbastecimento = totalLitrosAbastecimento;
    }

    public String getValorUnitarioAbastecimento() {
        return valorUnitarioAbastecimento;
    }

    public void setValorUnitarioAbastecimento(String valorUnitarioAbastecimento) {
        this.valorUnitarioAbastecimento = valorUnitarioAbastecimento;
    }

    public String getNomeItemAbastecimento() {
        return nomeItemAbastecimento;
    }

    public void setNomeItemAbastecimento(String nomeItemAbastecimento) {
        this.nomeItemAbastecimento = nomeItemAbastecimento;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getValorDescontoTotal() {
        return valorDescontoTotal;
    }

    public void setValorDescontoTotal(String valorDescontoTotal) {
        this.valorDescontoTotal = valorDescontoTotal;
    }

    public String getCpfMotorista() {
        return cpfMotorista;
    }

    public void setCpfMotorista(String cpfMotorista) {
        this.cpfMotorista = cpfMotorista;
    }
}