package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.AutorizacaoPagamentoEdicao;

/**
 * Classe de exibição que representa uma autorizacao pagamento anonimização
 */
public class AutorizacaoPagamentoEdicaoAnonimizacaoVo {

    private Long id;
    private Long cpfMotorista;
    private String nomeMotorista;

    /**
     * Construtor básico para serialização JSON
     */
    public AutorizacaoPagamentoEdicaoAnonimizacaoVo() {

    }

    /**
     * Constrói AutorizacaoPagamentoAnonimizacaoVo com os dados fornecidos
     *
     * @param autorizacaoPagamentoEdicao a entidade contendo informações
     */
    public AutorizacaoPagamentoEdicaoAnonimizacaoVo(AutorizacaoPagamentoEdicao autorizacaoPagamentoEdicao) {
        if(autorizacaoPagamentoEdicao == null){
            return;
        }
        this.id = autorizacaoPagamentoEdicao.getId();
        this.nomeMotorista = autorizacaoPagamentoEdicao.getNomeMotorista();
        this.cpfMotorista = autorizacaoPagamentoEdicao.getCpfMotorista();
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
