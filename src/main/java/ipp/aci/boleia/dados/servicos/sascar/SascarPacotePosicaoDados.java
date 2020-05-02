package ipp.aci.boleia.dados.servicos.sascar;

import ipp.aci.boleia.dados.ISascarPacotePosicaoDados;
import ipp.aci.boleia.dados.servicos.ensemble.ServicoSoapDados;
import ipp.aci.boleia.dados.servicos.ensemble.WsSecurityUsernameTokenHandler;
import ipp.aci.boleia.dados.servicos.sascar.jaxws.PacotePosicao;
import ipp.aci.boleia.dados.servicos.sascar.jaxws.SasIntegraNotification;
import ipp.aci.boleia.dados.servicos.sascar.jaxws.SasIntegraWS;
import ipp.aci.boleia.dados.servicos.sascar.jaxws.SasIntegraWSService;
import ipp.aci.boleia.dados.servicos.sascar.jaxws.Veiculo;
import ipp.aci.boleia.dominio.TelemetriaVeiculo;
import ipp.aci.boleia.dominio.VeiculoRastreador;
import ipp.aci.boleia.dominio.vo.RespostaSascarVo;
import ipp.aci.boleia.util.UtilitarioFormatacaoData;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Classe que implementa {@link ISascarPacotePosicaoDados} para acesso a serviços da SASCAR
 *
 */
@Repository
public class SascarPacotePosicaoDados extends ServicoSoapDados<SasIntegraWS> implements ISascarPacotePosicaoDados {

    private static final String ARQUIVO_WSDL = "/wsdl/sascar.wsdl";
    private static final Integer TAMANHO_PAGINA_VEICULOS = 1000;
    private static final Integer TAMANHO_PAGINA_PACOTES = 3000;

    @Value("${sascar.web.service.login}")
    private String usuario;

    @Value("${sascar.web.service.senha}")
    private String senha;


    @Override
    public RespostaSascarVo<VeiculoRastreador> obterPlacasVeiculos(Integer ponteiroParaRequisicao) {
        List<Veiculo> veiculos = obterVeiculos(TAMANHO_PAGINA_VEICULOS, ponteiroParaRequisicao);
        List<VeiculoRastreador> veiculosSascar = obterVeiculosSascar(veiculos);
        Integer ponteiroParaProximaRequisicao =  veiculos.size() == TAMANHO_PAGINA_VEICULOS ?
                    veiculos.get(veiculos.size() - 1).getIdVeiculo() : null;
        return new RespostaSascarVo<>(veiculosSascar, ponteiroParaProximaRequisicao);
    }

    @Override
    public RespostaSascarVo<TelemetriaVeiculo> obterPacotes(Function<Integer, String> obterPlaca) {
        List<PacotePosicao> pacotesPosicao = obterPacotesPosicao(TAMANHO_PAGINA_PACOTES);
        List<TelemetriaVeiculo> telemetriaVeiculos = obterTelemetriaVeiculos(obterPlaca, pacotesPosicao);
        return new RespostaSascarVo<>(telemetriaVeiculos, pacotesPosicao.size() == TAMANHO_PAGINA_PACOTES);
    }

    /**
     * Método que obtém veículos na telemetria da SASCAR
     *
     * @param quantidade Quantidade máxima de registros
     * @param idVeiculo Id do primeiro veículo a ser obtido.
     * @return Lista com veículos obtidos.
     */
    private List<Veiculo> obterVeiculos(Integer quantidade, Integer idVeiculo) {
        try {
            return getServico().obterVeiculos(usuario, senha, quantidade, idVeiculo);
        } catch (SasIntegraNotification sasIntegraNotification) {
            throw new ExcecaoBoleiaRuntime(sasIntegraNotification);
        }
    }


    /**
     * Método que obtém pacotes de posição na telemetria da Sascar
     * @param quantidade Quantidade máxima de registros
     * @return Lista com pacotes obtidos
     */
    private  List<PacotePosicao> obterPacotesPosicao(Integer quantidade) {
        try {
            return getServico().obterPacotePosicoes(usuario, senha, quantidade);
        } catch (SasIntegraNotification sasIntegraNotification) {
            throw new ExcecaoBoleiaRuntime(sasIntegraNotification);
        }
    }

    /**
     * Método que transforma informações de veículo em VeiculoSscar
     *
     * @param veiculos Lista com veículos obtidos na SASCAR
     * @return Lista de VeiculoRastreador
     */
    private List<VeiculoRastreador> obterVeiculosSascar(List<Veiculo> veiculos) {
        return veiculos
                .stream()
                .map(this::obterVeiculoRastreador)
                .collect(Collectors.toList());
    }

    /**
     * Método que transforma Veiculo em VeiculoRastreador
     * @param veiculo Veiculo obtido no serviço da sascar
     * @return Veiculo Sascar
     */
    private VeiculoRastreador obterVeiculoRastreador(Veiculo veiculo) {
        VeiculoRastreador VeiculoRastreador = new VeiculoRastreador();
        VeiculoRastreador.setIdVeiculoSistema(veiculo.getIdVeiculo());
        VeiculoRastreador.setPlaca(veiculo.getPlaca());
        return VeiculoRastreador;
    }

    /**
     * Método que obtém Telemetria Veículo
     * @param obterPlacaVeiculo Método que obtém placa do veículo a partir do id na integração
     * @param pacotes Pacotes de posição na SASCAR
     * @return Lista com dados de telemetria
     */
    private List<TelemetriaVeiculo> obterTelemetriaVeiculos(Function<Integer, String> obterPlacaVeiculo, List<PacotePosicao> pacotes) {
        return pacotes.stream()
                .map(pacotePosicao -> obterTelemetriaVeiculo(obterPlacaVeiculo, pacotePosicao))
                .collect(Collectors.toList());
    }

    /**
     * Método que obtém converte telemetria um pacote posição em uma telemetria veículo
     * @param obterPlacaVeiculo Método que botém placa do veículo a partir do id
     * @param pacote Pacote posição
     * @return Telemetria Veículo
     */
    private TelemetriaVeiculo obterTelemetriaVeiculo(Function<Integer, String> obterPlacaVeiculo, PacotePosicao pacote) {
        TelemetriaVeiculo telemetriaVeiculo = new TelemetriaVeiculo();
        telemetriaVeiculo.setDataPacote(UtilitarioFormatacaoData.lerDataGregorian(pacote.getDataPacote()));
        telemetriaVeiculo.setDataPosicao(UtilitarioFormatacaoData.lerDataGregorian(pacote.getDataPosicao()));
        telemetriaVeiculo.setHodometro(pacote.getOdometro().longValue());
        telemetriaVeiculo.setHorimetro(pacote.getHorimetro().longValue());
        telemetriaVeiculo.setLatitude(new BigDecimal(pacote.getLatitude()));
        telemetriaVeiculo.setLongitude(new BigDecimal(pacote.getLongitude()));
        telemetriaVeiculo.setPlaca(obterPlacaVeiculo.apply(pacote.getIdVeiculo()));
        telemetriaVeiculo.setStatusGps(pacote.getGps());
        telemetriaVeiculo.setStatusIgnicao(pacote.getIgnicao());
        telemetriaVeiculo.setStatusSatelite(pacote.getSatelite());
        telemetriaVeiculo.setVelocidade(pacote.getVelocidade());
        return telemetriaVeiculo;
    }


    @Override
    protected WsSecurityUsernameTokenHandler instanciarHeadersHandler() {
        return null;
    }

    @Override
    protected SasIntegraWS instanciarServico() throws MalformedURLException {
        URL url = new URL(obterUrlWsdlCompleta());
        SasIntegraWSService service = new SasIntegraWSService(url);
        return service.getSasIntegraWSPort();
    }

    /**
     * Dado o endereco do wsdl, converte-o em um caminho absoluto
     *
     * @return url wsdl completa
     */
    private String obterUrlWsdlCompleta() {
        return this.getClass().getResource(ARQUIVO_WSDL).toString();
    }

}
