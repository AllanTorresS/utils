package ipp.aci.boleia.dados.servicos.kmvpdvcaminhoneiro;

import ipp.aci.boleia.dados.IClienteHttpDados;
import ipp.aci.boleia.dados.IKmvPdvCaminhoneiroDados;
import ipp.aci.boleia.dominio.enums.TypeModel;
import ipp.aci.boleia.dominio.vo.CouchDbFindVo;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * Servico que acessa o PDV para obter KMV dos Caminhoneiros
 */
@Repository
public class KmvPdvCaminhoneiroDados implements IKmvPdvCaminhoneiroDados {

    private static final Logger LOGGER = LoggerFactory.getLogger(KmvPdvCaminhoneiroDados.class);

    @Value("${kmvpdvcaminhoneiro.url}")
    private String url;

    @Value("${kmvpdvcaminhoneiro.parametros}")
    private String rotaParametros;

    @Value("${kmvpdvcaminhoneiro.abastecimentos}")
    private String rotaAbastecimento;

    @Autowired
    private IClienteHttpDados restDados;

    @Autowired
    private Mensagens mensagens;

    public String buscarBicos(Long codigoCorporativoPontoDeVenda) throws ExcecaoValidacao {

        CouchDbFindVo body = new CouchDbFindVo();
        body.getSelector().setPv(codigoCorporativoPontoDeVenda);
        body.getSelector().setTypeModel(TypeModel.BICO.getNomeModel());

        try {
            return restDados.doPostJson(url.concat(rotaParametros), body, null, (response)-> EntityUtils.toString(response.getEntity()));
        }catch (Exception ex){
            LOGGER.error(ex.getMessage(), ex);
            throw new ExcecaoValidacao(mensagens.obterMensagem("kmvpdvcaminhoneiro.erro.consulta.bico"));
        }
    }

    @Override
    public String buscarAbastecimentos(Long codigoCorporativoPontoDeVenda, String idBico) throws ExcecaoValidacao {

        CouchDbFindVo body = new CouchDbFindVo();
        body.getSelector().setPv(codigoCorporativoPontoDeVenda);
        body.getSelector().setTypeModel(TypeModel.ABASTECIMENTO.getNomeModel());
        body.getSelector().setBicoId(idBico);

        try {
            return restDados.doPostJson(url.concat(rotaAbastecimento), body, null, (response)-> EntityUtils.toString(response.getEntity()));
        }catch (Exception ex){
            LOGGER.error(ex.getMessage(), ex);
            throw new ExcecaoValidacao(mensagens.obterMensagem("kmvpdvcaminhoneiro.erro.consulta.abastecimento"));
        }
    }

    @Override
    public String buscarParametros(Long codigoCorporativoPontoDeVenda) throws ExcecaoValidacao {

        CouchDbFindVo body = new CouchDbFindVo();
        body.getSelector().setPv(codigoCorporativoPontoDeVenda);
        body.getSelector().setTypeModel(TypeModel.PARAMETRO.getNomeModel());

        try {
            return restDados.doPostJson(url.concat(rotaParametros), body, null, (response)-> EntityUtils.toString(response.getEntity()));
        }catch (Exception ex){
            LOGGER.error(ex.getMessage(), ex);
            throw new ExcecaoValidacao(mensagens.obterMensagem("kmvpdvcaminhoneiro.erro.consulta.parametros"));
        }
    }
}
