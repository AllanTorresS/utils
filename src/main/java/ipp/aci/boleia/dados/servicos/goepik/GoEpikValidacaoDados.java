package ipp.aci.boleia.dados.servicos.goepik;

import ipp.aci.boleia.dados.IClienteHttpDados;
import ipp.aci.boleia.dados.IVerificacaoImagemGoEpikDados;
import ipp.aci.boleia.dominio.vo.ImagemGoEpikVo;
import ipp.aci.boleia.dominio.vo.RespostaValidacaoGoEpik;
import ipp.aci.boleia.util.UtilitarioJson;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GoEpikValidacaoDados implements IVerificacaoImagemGoEpikDados {

    @Value("${goepik.service.apikey}")
    private String apiKey;

    @Value("${goepik.service.url}")
    private String endereco;

    @Autowired
    private IClienteHttpDados clienteHttpDados;

    @Override
    public String validarImagemContadorGoEpik(String imageData) {

        ImagemGoEpikVo body = new ImagemGoEpikVo();
        body.setImage(imageData);

        RespostaValidacaoGoEpik vo = clienteHttpDados.doPostJson(endereco, null, null, body, montarHeader(apiKey),
                resp -> UtilitarioJson.toObject(EntityUtils.toString(resp.getEntity()), RespostaValidacaoGoEpik.class));

        return vo.getResult();
    }


    /**
     * Monta o header do request com o token associado
     * @param apikey O token de autenticação da GoEpik
     * @return O header completo
     */
    private Header[] montarHeader(String apikey) {
        String type = "x-api-key";
        return new Header[]{new BasicHeader(type, apikey)};
    }
}
