package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IHistoricoSaldoVeiculosDados;
import ipp.aci.boleia.dominio.HistoricoSaldoVeiculo;
import ipp.aci.boleia.dominio.SaldoVeiculo;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.TipoPerfilUsuario;
import ipp.aci.boleia.dominio.enums.TipoTokenJwt;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Serviços de domínio da entidade {@link ipp.aci.boleia.dominio.HistoricoSaldoVeiculo}.
 *
 * @author josue.conceicao
 */
@Component
public class HistoricoSaldoVeiculoSd {

    @Autowired
    private IHistoricoSaldoVeiculosDados repositorioHistoricoSaldoVeiculo;

    /**
     * Cria o historico para o saldo do veículo
     *
     * @param saldo             a entidade que deve ter seu historico gravado
     * @param ambiente          contem as informações do usuario logado que realizou a alteração e data atual da alteração
     */
    public void criarHistoricoSaldo(SaldoVeiculo saldo, UtilitarioAmbiente ambiente) {
        HistoricoSaldoVeiculo historicoSaldoVeiculo = new HistoricoSaldoVeiculo();

        historicoSaldoVeiculo.setSaldoVeiculo(saldo);
        historicoSaldoVeiculo.setCotaValor(saldo.getCotaValor());
        historicoSaldoVeiculo.setCotaLitros(saldo.getCotaLitros());
        historicoSaldoVeiculo.setCotaValorAbastecimento(saldo.getCotaValorAbastecimento());
        historicoSaldoVeiculo.setCotaLitrosAbastecimento(saldo.getCotaLitrosAbastecimento());
        historicoSaldoVeiculo.setCotaMensalValorSugerido(saldo.getCotaMensalValorSugerido());
        historicoSaldoVeiculo.setCotaMensalLitrosSugerido(saldo.getCotaMensalLitrosSugerido());
        historicoSaldoVeiculo.setCotaMensalValorSugeridoMaximo(saldo.getCotaMensalValorSugeridoMaximo());
        historicoSaldoVeiculo.setCotaMensalLitrosSugeridoMaximo(saldo.getCotaMensalLitrosSugeridoMaximo());
        historicoSaldoVeiculo.setCotaAbastecimentoValorSugerido(saldo.getCotaAbastecimentoValorSugerido());
        historicoSaldoVeiculo.setCotaAbastecimentoLitrosSugerido(saldo.getCotaAbastecimentoLitrosSugerido());
        historicoSaldoVeiculo.setCotaAbastecimentoValorSugeridoMaximo(saldo.getCotaAbastecimentoValorSugeridoMaximo());
        historicoSaldoVeiculo.setCotaAbastecimentoLitrosSugeridoMaximo(saldo.getCotaAbastecimentoLitrosSugeridoMaximo());
        historicoSaldoVeiculo.setValorConsumido(saldo.getValorConsumido());
        historicoSaldoVeiculo.setLitrosConsumidos(saldo.getLitrosConsumidos());
        historicoSaldoVeiculo.setVeiculo(saldo.getVeiculo());
        historicoSaldoVeiculo.setRenovarCotaVeiculoAgregadoAutomaticamente(saldo.getRenovarCotaVeiculoAgregadoAutomaticamente());

        Usuario usuarioLogado = ambiente.getUsuarioLogado();
        if (usuarioLogado != null && usuarioLogado.getTipoTokenJwt() != null && (usuarioLogado.getTipoTokenJwt().equals(TipoTokenJwt.USUARIO_BOLEIA) || usuarioLogado.getTipoTokenJwt().getTipoPerfil() != null && !usuarioLogado.getTipoTokenJwt().getTipoPerfil().equals(TipoPerfilUsuario.SISTEMA_EXTERNO))) {
            historicoSaldoVeiculo.setUsuario(usuarioLogado);
        }
        historicoSaldoVeiculo.setDataAlteracao(ambiente.buscarDataAmbiente());

        repositorioHistoricoSaldoVeiculo.armazenar(historicoSaldoVeiculo);
    }
}