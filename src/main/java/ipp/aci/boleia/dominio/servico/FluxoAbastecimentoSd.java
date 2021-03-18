package ipp.aci.boleia.dominio.servico;


import ipp.aci.boleia.dados.IFluxoAbastecimentoMotoristaDados;
import ipp.aci.boleia.dados.IHistoricoFluxoAbastecimentoMotoristaDados;
import ipp.aci.boleia.dominio.FluxoAbastecimentoFrotaConfig;
import ipp.aci.boleia.dominio.FluxoAbastecimentoMotoristaConfig;
import ipp.aci.boleia.dominio.HistoricoFluxoAbastecimentoMotoristaConfig;
import ipp.aci.boleia.dominio.Motorista;
import ipp.aci.boleia.dominio.Usuario;
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
        fluxoAbastecimentoFrota.setExigirLitragem(true);
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
