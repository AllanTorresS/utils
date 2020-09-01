package ipp.aci.boleia.dominio.vo;


import java.math.RoundingMode;

import ipp.aci.boleia.dominio.TransacaoConectcarConsolidada;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.UtilitarioFormatacaoData;

/**
 * Classe com informações referentes à um detalhe de transacoes ConectCar para o aviso de débito
 */
public class DetalheTransacaoConectcarVo {

    private String data;
    private String hora;
    private String frota;
    private Integer codigoTipo;
    private String descricaoTipo;
    private String tag;
    private String placa;
    private String praca;
    private String valor;
    private String estorno;

    /**
     * Serializacao json
     */
    public DetalheTransacaoConectcarVo() {
        // serializacao json
    }

    /**
     * Constroi o VO de detalhe a partir de uma transação ConectCar
     * @param transacao A autorizacao de pagamento
     */
    public DetalheTransacaoConectcarVo(TransacaoConectcarConsolidada transacao) {
        this.setData(UtilitarioFormatacaoData.formatarDataCurta(transacao.getDataInicioPeriodo()));
        this.setHora(UtilitarioFormatacaoData.formatarHoraMinutosSegundos(transacao.getDataInicioPeriodo()));
        this.setFrota(transacao.getFrota().getNomeRazaoFrota());
        this.setCodigoTipo(transacao.getTipoTransacao());
        this.setPlaca(transacao.getPlaca());
        this.setTag(transacao.getTag());
        this.setPraca(transacao.getPraca());
        this.setValor(UtilitarioFormatacao.formatarDecimal(transacao.getValorTotal().setScale(2, RoundingMode.HALF_UP)));
        this.setEstorno(UtilitarioFormatacao.formatarDecimal(transacao.getEstorno().setScale(2, RoundingMode.HALF_UP)));
    }

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getFrota() {
		return frota;
	}

	public void setFrota(String frota) {
		this.frota = frota;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getPraca() {
		return praca;
	}

	public void setPraca(String praca) {
		this.praca = praca;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getEstorno() {
		return estorno;
	}

	public void setEstorno(String estorno) {
		this.estorno = estorno;
	}

	public Integer getCodigoTipo() {
		return codigoTipo;
	}

	public void setCodigoTipo(Integer codigoTipo) {
		this.codigoTipo = codigoTipo;
	}

	public String getDescricaoTipo() {
		return descricaoTipo;
	}

	public void setDescricaoTipo(String descricaoTipo) {
		this.descricaoTipo = descricaoTipo;
	}

}