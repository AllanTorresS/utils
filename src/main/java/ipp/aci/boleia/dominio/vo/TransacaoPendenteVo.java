package ipp.aci.boleia.dominio.vo;

/**
 * Representa uma transacao do POS/Abastece Aí pendente de confirmacao
 */
public class TransacaoPendenteVo {

    private Long idAbastecimento;
    private String nsuZacc;

    public TransacaoPendenteVo() {
        //construtor default
    }

    /**
     * Construtor
     * @param idAbastecimento identificador do abastecimento
     * @param nsuZacc NSU Zacc referente a queima do abastecimento
     */
    public TransacaoPendenteVo(Long idAbastecimento, String nsuZacc) {
        this.idAbastecimento = idAbastecimento;
        this.nsuZacc = nsuZacc;
    }

    public Long getIdAbastecimento() {
        return idAbastecimento;
    }

    public void setIdAbastecimento(Long idAbastecimento) {
        this.idAbastecimento = idAbastecimento;
    }

    public String getNsuZacc() {
        return nsuZacc;
    }

    public void setNsuZacc(String nsuZacc) {
        this.nsuZacc = nsuZacc;
    }
}
