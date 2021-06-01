package ipp.aci.boleia.dominio.servico;


import ipp.aci.boleia.dados.IFluxoAbastecimentoMotoristaDados;
import ipp.aci.boleia.dados.IHistoricoFluxoAbastecimentoFrotaDados;
import ipp.aci.boleia.dados.IHistoricoFluxoAbastecimentoMotoristaDados;
import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.FluxoAbastecimentoFrotaConfig;
import ipp.aci.boleia.dominio.FluxoAbastecimentoMotoristaConfig;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.HistoricoFluxoAbastecimentoFrotaConfig;
import ipp.aci.boleia.dominio.HistoricoFluxoAbastecimentoMotoristaConfig;
import ipp.aci.boleia.dominio.Motorista;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.interfaces.IFluxoAbastecimentoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Encapsula as regras de negocio que envolvem a manipulacao das Configurações de FLuxo de abastecimento
 */
@Component
public class FluxoAbastecimentoSd {

    @Autowired
    private IFluxoAbastecimentoMotoristaDados repositorioFluxoMotorista;

    @Autowired
    private IHistoricoFluxoAbastecimentoMotoristaDados repositorioHistoricoFluxoMotorista;

    @Autowired
    private IHistoricoFluxoAbastecimentoFrotaDados repositorioHistoricoFluxoFrota;

    /**
     * Remove Configuração de fluxo de abastecimento de motorista e registra alteração em histórico
     *
     * @param motoristaEntidade motorista com  configuração de fluxo de abastecimento
     * @param usuarioLogado usuario logado que realizou a alteração
     * @param dataAmbiente data atual da alteração
     */
    public void excluirFluxoAbastecimentoMotorista(Motorista motoristaEntidade, Usuario usuarioLogado, Date dataAmbiente) {
        FluxoAbastecimentoMotoristaConfig fluxoAbastecimentoMotoristaconfigEntidade
                = repositorioFluxoMotorista.obterFluxoPorMotorista(motoristaEntidade);

        if (fluxoAbastecimentoMotoristaconfigEntidade != null){
            fluxoAbastecimentoMotoristaconfigEntidade.setExcluido(true);
            repositorioFluxoMotorista.excluir(fluxoAbastecimentoMotoristaconfigEntidade.getId());
            repositorioHistoricoFluxoMotorista.armazenar(
                    new HistoricoFluxoAbastecimentoMotoristaConfig(fluxoAbastecimentoMotoristaconfigEntidade, usuarioLogado, dataAmbiente ));
        }
    }

    /**
     * Obtém configuração de fluxo de abastecimento conforme abastecimento.
     *
     * @param autorizacaoPagamento do abastecimento
     * @return configuração de fluxo utilizada no abastecimento
     */
    public IFluxoAbastecimentoConfig obterParaAbastecimento(AutorizacaoPagamento autorizacaoPagamento) {
        return this.obterParaAbastecimento(autorizacaoPagamento.getFrota(), autorizacaoPagamento.getMotorista(), autorizacaoPagamento.getDataRequisicao());
    }

    /**
     * Obtém configuração de fluxo de abastecimento para frota, motorista e data parametrizados.
     *
     * @param frota da configuração de fluxo
     * @param motorista da configuração de fluxo, pode ser opcional (nulo)
     * @param data no historico da configuração de fluxo
     * @return configuração de fluxo para contexto parametrizado
     */
    public IFluxoAbastecimentoConfig obterParaAbastecimento(Frota frota, Motorista motorista, Date data) {
        if (motorista != null) {
            HistoricoFluxoAbastecimentoMotoristaConfig fluxoMotorista = repositorioHistoricoFluxoMotorista.obterFluxoPorData(motorista,data);
            if (fluxoMotorista != null && !fluxoMotorista.getExcluido()) {
                return fluxoMotorista;
            }
        }
        HistoricoFluxoAbastecimentoFrotaConfig fluxoFrotaConfig = repositorioHistoricoFluxoFrota.obterFluxoPorData(frota, data);
        return fluxoFrotaConfig != null ? fluxoFrotaConfig : this.obterFluxoAbastecimentoPadrao();
    }


    /**
     * Cria ou altera (caso possua ID) a configuração de fluxo motorista mantendo o historico de alteração
     *
     * @param fluxoAbastecimentoMotoristaConfig configuração de fluxo de motorista alterada
     * @param usuarioLogado usuario logado que realizou a alteração
     * @param dataAmbiente data atual da alteração
     * @return configuração de fluxo de motorista alterada
     */
    public FluxoAbastecimentoMotoristaConfig armazenarFluxoMotorista(FluxoAbastecimentoMotoristaConfig fluxoAbastecimentoMotoristaConfig, Usuario usuarioLogado, Date dataAmbiente) {
        fluxoAbastecimentoMotoristaConfig  = repositorioFluxoMotorista.armazenar(fluxoAbastecimentoMotoristaConfig);
        repositorioHistoricoFluxoMotorista.armazenar(
                new HistoricoFluxoAbastecimentoMotoristaConfig(fluxoAbastecimentoMotoristaConfig, usuarioLogado, dataAmbiente ));
        return fluxoAbastecimentoMotoristaConfig;
    }

    /**
     * Cria Configuração de Fluxo de Abastecimento de Frota com valores padrões
     *
     * @return FluxoAbastecimentoFrotaConfig Configuração de Fluxo de Abastecimento padrão
     */
    public FluxoAbastecimentoFrotaConfig obterFluxoAbastecimentoPadrao() {
        FluxoAbastecimentoFrotaConfig fluxoAbastecimentoFrota = new FluxoAbastecimentoFrotaConfig();
        fluxoAbastecimentoFrota.setExigirTelaHodometroHorimetro(true);
        fluxoAbastecimentoFrota.setExigirFotoHodometroHorimetro(true);
        fluxoAbastecimentoFrota.setExigirLeituraAutoHodometroHorimetro(true);
        fluxoAbastecimentoFrota.setExigirPosto(false);
        fluxoAbastecimentoFrota.setExigirCombustivel(true);
        return fluxoAbastecimentoFrota;
    }

    /**
     * Remove o veiculo parametrizado por id das configurações de fluxo abastecimento de motorista onde esse veículo está associado
     *
     * @param idVeiculo identificador do veiculo
     * @param usuarioLogado usuario logado que realizou a alteração
     * @param dataAmbiente data atual da alteração
     */
    public void removerVeiculoDeConfiguracoesFluxoMotorista(Long idVeiculo, Usuario usuarioLogado, Date dataAmbiente) {
        List<FluxoAbastecimentoMotoristaConfig> fluxosMotoristaComEsseVeiculo = repositorioFluxoMotorista.obterFluxosMotoristaPorVeiculo(idVeiculo);
        for (FluxoAbastecimentoMotoristaConfig fluxoAbastecimentoMotoristaConfig : fluxosMotoristaComEsseVeiculo) {
            fluxoAbastecimentoMotoristaConfig.setVeiculo(null);
            armazenarFluxoMotorista(fluxoAbastecimentoMotoristaConfig, usuarioLogado, dataAmbiente);
        }
    }
}
