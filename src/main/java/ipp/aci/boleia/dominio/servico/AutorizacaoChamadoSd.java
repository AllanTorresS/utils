package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IAutorizacaoChamadoDados;
import ipp.aci.boleia.dominio.AutorizacaoChamado;
import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.enums.TipoOperacaoChamado;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Concentra a lógica de manipulação de AutorizacaoChamdo
 */
@Component
public class AutorizacaoChamadoSd {

    @Autowired
    protected UtilitarioAmbiente ambiente;

    @Autowired
    private IAutorizacaoChamadoDados repositorioChamado;

    /**
     * Associa uma autorização pagamento a um chamado
     *
     * @param numeroChamado o número do chamado
     * @param abastecimento o abastecimento
     * @param tipoOperacao o tipo da operação realizada
     *
     * @return chamado criado
     */
    public AutorizacaoChamado criaChamado(Long numeroChamado, AutorizacaoPagamento abastecimento, TipoOperacaoChamado tipoOperacao) {
        AutorizacaoChamado autorizacaoChamado = new AutorizacaoChamado(abastecimento, numeroChamado, tipoOperacao.getValue());
        autorizacaoChamado.setUsuario(ambiente.getUsuarioLogado());
        autorizacaoChamado.setDataCriacao(ambiente.buscarDataAmbiente());
        return repositorioChamado.armazenar(autorizacaoChamado);
    }
}