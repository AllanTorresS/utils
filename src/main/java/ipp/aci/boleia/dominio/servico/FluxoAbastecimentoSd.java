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
import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.dominio.interfaces.IFluxoAbastecimentoConfig;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
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

    public void validarVeiculoConformeFluxoMotorista(IFluxoAbastecimentoConfig fluxoAbastecimentoConfig, Veiculo veiculo) throws ExcecaoValidacao {

        boolean subtipoClimatizador = veiculo.getSubtipoVeiculo().utilizaHorimetro();
        //Tem restrição de placa principal?
        if(!subtipoClimatizador && fluxoAbastecimentoConfig.getVeiculo() != null && !fluxoAbastecimentoConfig.possuiMesmaPlacaDoFluxo(veiculo)){
            throw new ExcecaoValidacao(Erro.VEICULO_NAO_AUTORIZADO_PELO_FLUXO_APP, veiculo.getPlaca());
        }

        //Tem restrição de placa climatizador?
        if(subtipoClimatizador && fluxoAbastecimentoConfig.getVeiculoClimatizador() != null && !fluxoAbastecimentoConfig.possuiMesmoClimatizadorDoFluxo(veiculo)){
            throw new ExcecaoValidacao(Erro.VEICULO_NAO_AUTORIZADO_PELO_FLUXO_APP, veiculo.getPlaca());
        }
    }

    /**
     * Realiza a validação de veículos de acordo com o fluxo de abastecimento.
     *
     * @param fluxoAbastecimentoConfig Configuração do fluxo de abastecimento.
     * @param veiculo Primeiro veículo.
     * @param segundoVeiculo Segundo veículo.
     * @throws ExcecaoValidacao Exceção lançada em caso de erro de validação.
     */
    public void validarVeiculosConformeFluxoMotorista(IFluxoAbastecimentoConfig fluxoAbastecimentoConfig, Veiculo veiculo, Veiculo segundoVeiculo) throws ExcecaoValidacao {
        validarVeiculoConformeFluxoMotorista(fluxoAbastecimentoConfig, veiculo);
        validarVeiculoConformeFluxoMotorista(fluxoAbastecimentoConfig, segundoVeiculo);
        validarClimatizadorParaDuasPlacas(veiculo, segundoVeiculo);
    }

    /**
     * Realiza as validações de climatizador para o fluxo com duas placas.
     *
     * @param veiculo Primeiro veículo.
     * @param segundoVeiculo Segundo veículo.
     * @throws ExcecaoValidacao Exceção lançada em caso de erro de validação.
     */
    public void validarClimatizadorParaDuasPlacas(Veiculo veiculo, Veiculo segundoVeiculo) throws ExcecaoValidacao {
        //Os dois são climatizadores?
        if(veiculo.utilizaHorimetro() && segundoVeiculo.utilizaHorimetro()){
            throw new ExcecaoValidacao(Erro.ABAST_DOIS_CLIMATIZADORES_APP, segundoVeiculo.getPlaca());
        }

        //Nenhum deles é climatizador?
        if(!veiculo.utilizaHorimetro() && !segundoVeiculo.utilizaHorimetro()){
            throw new ExcecaoValidacao(Erro.ABAST_DUAS_PLACAS_SEM_CLIMATIZADOR_APP, segundoVeiculo.getPlaca());
        }

        //O veículo não climatizador está habilitado para abastecer com duas placas?
        Veiculo principal = definirVeiculoPrincipal(veiculo, segundoVeiculo);
        if(!principal.getHabilitadoAbastecerDuasPlacas()){
            throw new ExcecaoValidacao(Erro.VEICULO_N_HABILITADO_DUAS_PLACAS_APP, principal.getPlaca());
        }
    }

    /**
     * Define qual o veículo principal para um fluxo de abastecimento com 2 placas.
     *
     * @param primeiroVeiculo Primeiro veículo.
     * @param segundoVeiculo Segundo veículo.
     * @return O veículo principal;
     */
    public Veiculo definirVeiculoPrincipal(Veiculo primeiroVeiculo, Veiculo segundoVeiculo) {
        return !primeiroVeiculo.utilizaHorimetro() ? primeiroVeiculo : segundoVeiculo;
    }
}
