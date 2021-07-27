package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.AutorizacaoPagamento;

/**
 * Classe de exibição que representa uma autorizacao pagamento anonimização
 */
public class AutorizacaoPagamentoAnonimizacaoVo {

    private Long id;
    private Long cpfMotorista;
    private String nomeMotorista;

    /**
     * Construtor básico para serialização JSON
     */
    public AutorizacaoPagamentoAnonimizacaoVo() {

    }

    /**
     * Constrói AutorizacaoPagamentoAnonimizacaoVo com os dados fornecidos
     *
     * @param autorizacaoPagamento a entidade contendo informações
     */
    public AutorizacaoPagamentoAnonimizacaoVo(AutorizacaoPagamento autorizacaoPagamento) {
        if(autorizacaoPagamento == null){
            return;
        }
        this.id = autorizacaoPagamento.getId();
        this.nomeMotorista = autorizacaoPagamento.getNomeMotorista();
        this.cpfMotorista = autorizacaoPagamento.getCpfMotorista();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCpfMotorista() {
        return cpfMotorista;
    }

    public void setCpfMotorista(Long cpfMotorista) {
        this.cpfMotorista = cpfMotorista;
    }

    public String getNomeMotorista() {
        return nomeMotorista;
    }

    public void setNomeMotorista(String nomeMotorista) {
        this.nomeMotorista = nomeMotorista;
    }
}
