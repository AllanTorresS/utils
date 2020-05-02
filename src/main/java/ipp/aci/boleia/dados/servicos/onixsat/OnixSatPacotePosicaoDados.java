package ipp.aci.boleia.dados.servicos.onixsat;

import ipp.aci.boleia.dados.IClienteHttpDados;
import ipp.aci.boleia.dados.IOnixSatPacotePosicaoDados;
import ipp.aci.boleia.dominio.vo.RequisicaoTelemetriaOnixSatVo;
import ipp.aci.boleia.dominio.vo.RequisicaoVeiculoOnixSatVo;
import ipp.aci.boleia.dominio.vo.RespostaTelemetriaOnixSatVo;
import ipp.aci.boleia.dominio.vo.RespostaVeiculoOnixSatVo;
import ipp.aci.boleia.util.UtilitarioStreams;
import ipp.aci.boleia.util.UtilitarioXml;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Classe que implementa {@link IOnixSatPacotePosicaoDados} para acesso a serviços da OnixSat
 *
 */
@Repository
public class OnixSatPacotePosicaoDados implements IOnixSatPacotePosicaoDados {

    @Autowired
    private IClienteHttpDados clienteRest;

    @Value("${onixsat.web.service.url}")
    private String onixSatServicoUrl;

    @Value("${onixsat.web.service.login}")
    private String login;

    @Value("${onixsat.web.service.senha}")
    private String senha;

    @Override
    public List<RespostaVeiculoOnixSatVo> obterDadosVeiculo(){
        try {
            RequisicaoVeiculoOnixSatVo request = new RequisicaoVeiculoOnixSatVo(login, senha);
            return clienteRest.doPostXml(onixSatServicoUrl, request, null, r -> tratarRespostaOnixSat(r,RespostaVeiculoOnixSatVo.class));
        } catch (Exception e){
            throw new ExcecaoBoleiaRuntime(e);
        }
    }

    @Override
    public List<RespostaTelemetriaOnixSatVo> obterDadosTelemetria(Long idUltimaMensagem) {
        try {
            RequisicaoTelemetriaOnixSatVo request = new RequisicaoTelemetriaOnixSatVo(login, senha, idUltimaMensagem);
            return clienteRest.doPostXml(onixSatServicoUrl, request, null, r -> tratarRespostaOnixSat(r,RespostaTelemetriaOnixSatVo.class));
        } catch (Exception e){
            throw new ExcecaoBoleiaRuntime(e);
        }
    }

    /**
     * Trata a resposta enviada pelo serviço do onixsat
     *
     * @param resp a resposta do serviço
     * @param classeRetorno a classe que será mapeada a partir da resposta
     * @return uma lista os dados retornados
     */
    private <T> List<T> tratarRespostaOnixSat(CloseableHttpResponse resp, Class<T> classeRetorno){
        try {
            List<T> respostaOnixSat = new ArrayList<>();
            byte[] respostaZipada = EntityUtils.toByteArray(resp.getEntity());
            Map<String, InputStream> mapaRespostas = UtilitarioStreams.descompactarStream(new ByteArrayInputStream(respostaZipada));
            mapaRespostas.forEach((arquivo, inputStream) -> {
                String conteudo = UtilitarioStreams.decodificarStream(inputStream,StandardCharsets.UTF_8);
                respostaOnixSat.add(UtilitarioXml.toObject(conteudo, classeRetorno));
            });
            return respostaOnixSat;
        } catch (Exception e){
            throw new ExcecaoBoleiaRuntime(e);
        }
    }

}
