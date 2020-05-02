package ipp.aci.boleia.dados.servicos.ensemble.saa;

import ipp.aci.boleia.dados.IAutenticacaoUsuarioDados;
import ipp.aci.boleia.dados.servicos.ensemble.ServicoSoapDados;
import ipp.aci.boleia.dados.servicos.ensemble.WsSecurityUsernameTokenHandler;
import ipp.aci.boleia.dados.servicos.ensemble.saa.jaxws.ArrayOfpermissoesSistemaAutenticadoItemBoolean;
import ipp.aci.boleia.dados.servicos.ensemble.saa.jaxws.AutenticacaoUsuarioReq;
import ipp.aci.boleia.dados.servicos.ensemble.saa.jaxws.AutenticacaoUsuarioResp;
import ipp.aci.boleia.dados.servicos.ensemble.saa.jaxws.AutorizacaoReq;
import ipp.aci.boleia.dados.servicos.ensemble.saa.jaxws.AutorizacaoResp;
import ipp.aci.boleia.dados.servicos.ensemble.saa.jaxws.SaaAcessoExterno;
import ipp.aci.boleia.dados.servicos.ensemble.saa.jaxws.SaaAcessoExternoSoap;
import ipp.aci.boleia.dominio.enums.TipoPerfilUsuario;
import ipp.aci.boleia.util.excecao.ExcecaoAutenticacaoRemota;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servico de autenticacao dos dados do usuario no sistema
 */
@Repository
@ConditionalOnProperty(name="saa.web.service.enabled", havingValue = "true")
public class SaaAutenticacaoUsuarioDados extends ServicoSoapDados<SaaAcessoExternoSoap> implements IAutenticacaoUsuarioDados {

    private static final String ARQUIVO_SAA_WSDL = "/wsdl/saa.wsdl";
    private static final Map<TipoPerfilUsuario, Integer> CODS_PERFIL_SAA = new HashMap<>();
    static {
        CODS_PERFIL_SAA.put(TipoPerfilUsuario.INTERNO, 1);
        CODS_PERFIL_SAA.put(TipoPerfilUsuario.PRECOS, 2);
    }

    @Value("${saa.web.service.login}")
    private String saaLogin;

    @Value("${saa.web.service.senha}")
    private String saaSenha;

    @Value("${saa.web.service.codigo.boleia}")
    private String codigoBoleia;

    @Override
    protected WsSecurityUsernameTokenHandler instanciarHeadersHandler() {
        return new WsSecurityUsernameTokenHandler(saaLogin, saaSenha, "SAA");
    }

    @Override
    protected SaaAcessoExternoSoap instanciarServico() throws MalformedURLException {
        String urlSaa = obterUrlSaaCompleta();
        return new SaaAcessoExterno(new URL(urlSaa)).getSaaAcessoExternoSoap();
    }

    @Override
    public boolean autenticar(String login, String senha) throws ExcecaoAutenticacaoRemota {
        try {
            AutenticacaoUsuarioReq req = new AutenticacaoUsuarioReq();
            req.setUsuario(login);
            req.setSenha(senha);

            AutenticacaoUsuarioResp resp = getServico().autenticacaoUsuario(req);
            Long autenticou = resp.getUsuarioAutenticou();
            return Long.valueOf(1L).equals(autenticou);
        } catch (Exception e) {
            throw new ExcecaoAutenticacaoRemota(e, "SAA");
        }
    }

    @Override
    public boolean possuiPerfil(String login, TipoPerfilUsuario perfil) throws ExcecaoAutenticacaoRemota {
        try {
            AutorizacaoReq req = new AutorizacaoReq();
            req.setUsuario(login);
            req.setSistema(codigoBoleia);
            AutorizacaoResp resp = getServico().autorizacaoUsuario(req);
            Boolean possuiPermissaoSistema = resp.isPossuiPermissaoSistemaReturn();
            return possuiPermissaoSistema && verificarPerfil(resp, perfil);
        } catch (Exception e) {
            throw new ExcecaoAutenticacaoRemota(e, "SAA");
        }
    }

    /**
     * Verifica se o perfil desejado esta na lista de permissoes do usuario
     * @param resp A resposta do SAA
     * @param perfil O perfil desejado
     * @return true caso o perfil esteja listado
     */
    private boolean verificarPerfil(AutorizacaoResp resp, TipoPerfilUsuario perfil) {
        Integer idxPerfil = CODS_PERFIL_SAA.get(perfil);
        if(idxPerfil != null) {
            ArrayOfpermissoesSistemaAutenticadoItemBoolean perms = resp.getPermissoesSistemaAutenticado();
            List<Boolean> list = perms != null ? perms.getPermissoesSistemaAutenticadoItem() : null;
            if(list != null && list.size() > idxPerfil) {
                return list.get(idxPerfil);
            }
        }
        return false;
    }

    /**
     * Dado o endereco do wsdl, converte-o em um caminho absoluto
     *
     * @return url saa completa
     */
    private String obterUrlSaaCompleta() {
        return this.getClass().getResource(ARQUIVO_SAA_WSDL).toString();
    }
}
