package ipp.aci.boleia.dados.servicos.allowme;

import ipp.aci.boleia.dados.IClienteHttpDados;
import ipp.aci.boleia.dados.IVerificacaoDispositivoDados;
import ipp.aci.boleia.dominio.DispositivoMotorista;
import ipp.aci.boleia.dominio.Motorista;
import ipp.aci.boleia.dominio.UsuarioMotorista;
import ipp.aci.boleia.dominio.enums.CodigoTelefonePais;
import ipp.aci.boleia.dominio.vo.DispositivoAllowMeVo;
import ipp.aci.boleia.dominio.vo.RespostaAllowMeVo;
import ipp.aci.boleia.dominio.vo.UsuarioAllowMeVo;
import ipp.aci.boleia.util.UtilitarioJson;
import ipp.aci.boleia.util.UtilitarioLambda;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

/**
 * Classe para integração com o serviço do AllowMe
 */
@Repository
public class AllowMeVerificacaoDispositivoDados implements IVerificacaoDispositivoDados {

    private static final String USERS_API_PATH = "/users/";
    private static final String DEVICES_API_PATH = "/devices";
    private static final String ACTIVATE_API_PATH = "/activate";

    @Value("${allowme.sentinelapp.username}")
    private String username;

    @Value("${allowme.sentinelapp.password}")
    private String password;

    @Value("${allowme.sentinelapp.url}")
    private String url;

    @Autowired
    private UtilitarioAmbiente ambiente;

    @Autowired
    private IClienteHttpDados clienteHttpDados;

    @Override
    public void inserirMotorista(Motorista motorista) {
        String motoristaUsername = montarMotoristaUsername(motorista);
        String endereco = url + USERS_API_PATH + motoristaUsername;
        UsuarioAllowMeVo body = new UsuarioAllowMeVo();
        body.setName(motoristaUsername);
        body.setUsername(motoristaUsername);
        body.setKeepOtpDevices(false);

        clienteHttpDados.doPostJson(endereco, username, password, body, null, null);
    }

    @Override
    public void inserirDispositivo(DispositivoMotorista dispositivo) {
        String endereco = url + USERS_API_PATH + montarMotoristaUsername(dispositivo.getMotorista()) + DEVICES_API_PATH;
        DispositivoAllowMeVo body = new DispositivoAllowMeVo();
        body.setNumber(montarDispositivoNumber(dispositivo.getMotorista()));
        body.getCapabilities().add("otp");
        body.getCapabilities().add("sms");

        DispositivoAllowMeVo vo = clienteHttpDados.doPostJson(endereco, username, password, body, null,
                resp -> UtilitarioJson.toObject(EntityUtils.toString(resp.getEntity()), DispositivoAllowMeVo.class));

        dispositivo.setTokenAllowMe(vo.getToken());
        dispositivo.setIdAllowMe(vo.getId());
    }

    @Override
    public void cadastrarUsernameMotorista(Motorista motorista) {
        String motoristaUsername = montarMotoristaUsername(motorista);
        cadastrarUsername(motoristaUsername);
    }

    @Override
    public void cadastrarTokenDispositivoMotorista(DispositivoMotorista dispositivo) {
        String endereco = url + USERS_API_PATH + montarMotoristaUsername(dispositivo.getMotorista()) + DEVICES_API_PATH;
        cadastrarToken(dispositivo, endereco);
    }

    @Override
    public void cadastrarUsernameUsuarioMotorista(UsuarioMotorista usuarioMotorista) {
        Long cpf = usuarioMotorista.getUsuario().getCpf();
        String usuarioUsername = montarUsuarioUsername(cpf.toString());
        cadastrarUsername(usuarioUsername);
    }

    @Override
    public void cadastrarTokenUsuarioMotorista(UsuarioMotorista usuarioMotorista, Motorista motorista) {
        Long cpf = usuarioMotorista.getUsuario().getCpf();
        String endereco = url + USERS_API_PATH + montarUsuarioUsername(cpf.toString()) + DEVICES_API_PATH;
        cadastrarToken(usuarioMotorista, motorista, endereco);
    }

    @Override
    public void ativarOtpUsuarioMotorista(UsuarioMotorista usuarioMotorista) {
        Long cpf = usuarioMotorista.getUsuario().getCpf();
        String endereco = url + USERS_API_PATH + montarUsuarioUsername(cpf.toString()) + DEVICES_API_PATH + "/" + usuarioMotorista.getTokenAllowMe() + ACTIVATE_API_PATH;
        DispositivoAllowMeVo vo = clienteHttpDados.doPostJson(endereco, username, password, null,
                resp -> UtilitarioJson.toObject(EntityUtils.toString(resp.getEntity()), DispositivoAllowMeVo.class));

        usuarioMotorista.setSeedAllowMe(vo.getSeed());
    }

    @Override
    public Boolean validarTokenDispositivo(Motorista motorista, String codigoAbastecimento) {
        String motoristaUsername = montarMotoristaUsername(motorista);
        return validarToken(codigoAbastecimento, motoristaUsername);
    }

    @Override
    public Boolean validarTokenUsuarioMotorista(String cpf, String codigoAbastecimento) {
        String usuarioUsername = montarUsuarioUsername(cpf);
        return validarToken(codigoAbastecimento, usuarioUsername);
    }

    @Override
    public void excluirDispositivo(DispositivoMotorista dispositivo) {
        String endereco = url + USERS_API_PATH + montarMotoristaUsername(dispositivo.getMotorista()) + "/devices/" + dispositivo.getIdAllowMe();
        clienteHttpDados.doDelete(endereco, username, password, null, null);
    }

    @Override
    public String montarMotoristaUsername(Motorista motorista) {
        return ambiente.getIdentificadorAmbiente() + "-motorista-" + motorista.getFrota().getId() + "-" + motorista.getCpf();
    }

    @Override
    public String montarUsuarioUsername(String cpf) {
        return ambiente.getIdentificadorAmbiente() + "-motorista-" + cpf;
    }

    @Override
    public void excluirDispositivoAllowMe(UsuarioMotorista usuarioMotorista, Motorista motorista) {
        if(usuarioMotorista.getIdAllowMe() != null){
            String endereco = url + USERS_API_PATH + montarUsuarioUsername(motorista.getCpf().toString()) + "/devices/" + usuarioMotorista.getIdAllowMe();
            clienteHttpDados.doDelete(endereco, username, password, null, null);
        }
    }

    /**
     * Monta o número completo do motorista.
     *
     * @param motorista Entidade com as informações do motorista.
     * @return Número de telefone completo do motorista.
     */
    private String montarDispositivoNumber(Motorista motorista) {
        return CodigoTelefonePais.BRASIL.getCodigo().toString() + motorista.getDddTelefoneCelular() + motorista.getTelefoneCelular();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Cadastra o username do allow me do motorista
     *
     * @param motoristaUsername nome do usuario motorista
     */
    private void cadastrarUsername(String motoristaUsername) {
        String endereco = url + USERS_API_PATH + motoristaUsername;
        UsuarioAllowMeVo body = new UsuarioAllowMeVo();
        body.setName(motoristaUsername);
        body.setUsername(motoristaUsername);
        body.setKeepOtpDevices(false);

        clienteHttpDados.doPostJson(endereco, username, password, body, null, null);
    }

    /**
     * Cadastra o token allow me do motorista
     *
     * @param dispositivo dispositivo motorista
     * @param endereco endereço do token
     */
    private void cadastrarToken(DispositivoMotorista dispositivo, String endereco) {
        DispositivoAllowMeVo body = new DispositivoAllowMeVo();
        body.setNumber(montarDispositivoNumber(dispositivo.getMotorista()));
        body.getCapabilities().add("otp");
        body.getCapabilities().add("sms");

        DispositivoAllowMeVo vo = clienteHttpDados.doPostJson(endereco, username, password, body, null,
                resp -> UtilitarioJson.toObject(EntityUtils.toString(resp.getEntity()), DispositivoAllowMeVo.class));

        dispositivo.setTokenAllowMe(vo.getToken());
        dispositivo.setIdAllowMe(vo.getId());
    }

    /**
     * Cadastra o token allow me para um usuário motorista
     *
     * @param usuarioMotorista usuario Motorista
     * @param motorista motorista
     * @param endereco endereço do serviço
     */
    private void cadastrarToken(UsuarioMotorista usuarioMotorista, Motorista motorista, String endereco) {
        DispositivoAllowMeVo body = new DispositivoAllowMeVo();
        body.setNumber(montarDispositivoNumber(motorista));
        body.getCapabilities().add("otp");
        body.getCapabilities().add("sms");

        DispositivoAllowMeVo vo = clienteHttpDados.doPostJson(endereco, username, password, body, null,
                resp -> UtilitarioJson.toObject(EntityUtils.toString(resp.getEntity()), DispositivoAllowMeVo.class));

        usuarioMotorista.setTokenAllowMe(vo.getToken());
        usuarioMotorista.setIdAllowMe(vo.getId());
    }

    /**
     * Valida um token no allowme para dado username
     *
     * @param codigoAbastecimento token informado pelo motorista
     * @param username username do allowme utilizado para validar o token
     * @return True para token valido
     */
    private Boolean validarToken(String codigoAbastecimento, String username) {
        String endereco = url + "/process";
        UsuarioAllowMeVo body = new UsuarioAllowMeVo();
        body.setUsername(username);
        RespostaAllowMeVo resposta = clienteHttpDados.doPostJson(endereco, this.username, password, body, null,
                resp -> UtilitarioJson.toObject(EntityUtils.toString(resp.getEntity()), RespostaAllowMeVo.class));

        endereco = url + "/process/" + resposta.getToken() + "/auth";
        body.setCode(codigoAbastecimento);
        resposta = clienteHttpDados.doPostJson(endereco, this.username, password, body, null,
                resp -> UtilitarioJson.toObject(EntityUtils.toString(resp.getEntity()), RespostaAllowMeVo.class));

        return resposta.getAuthorized() != null ? resposta.getAuthorized() : false;
    }

    @Override
    public void cadastrarUsernameUsuarioETokenDispositivoMotorista(UsuarioMotorista usuarioMotorista, Motorista motorista) {
        Long cpf = usuarioMotorista.getUsuario().getCpf();
        String motoristaUsername = montarUsuarioUsername(cpf.toString());
        String endereco = url + USERS_API_PATH + motoristaUsername;

        DispositivoAllowMeVo dispositivo = new DispositivoAllowMeVo();
        dispositivo.setNumber(montarDispositivoNumber(motorista));
        dispositivo.getCapabilities().add("otp");
        dispositivo.getCapabilities().add("sms");

        UsuarioAllowMeVo body = new UsuarioAllowMeVo();
        body.setName(motoristaUsername);
        body.setUsername(motoristaUsername);
        body.setKeepOtpDevices(false);
        body.setDevices(Arrays.asList(dispositivo));

        UsuarioAllowMeVo vo = clienteHttpDados.doPostJson(endereco, username, password, body, null,
                resp -> UtilitarioJson.toObject(EntityUtils.toString(resp.getEntity()), UsuarioAllowMeVo.class));

        DispositivoAllowMeVo dispositivoCadastrado = UtilitarioLambda.obterPrimeiroObjetoDaLista(vo.getDevices());
        usuarioMotorista.setTokenAllowMe(dispositivoCadastrado.getToken());
        usuarioMotorista.setIdAllowMe(dispositivoCadastrado.getId());
    }

    @Override
    public boolean buscarCadastroUsuarioAllowMe(UsuarioMotorista usuarioMotorista) {
        Long cpf = usuarioMotorista.getUsuario().getCpf();
        String motoristaUsername = montarUsuarioUsername(cpf.toString());
        String endereco = url + USERS_API_PATH + motoristaUsername;

        UsuarioAllowMeVo vo = clienteHttpDados.doGet (endereco, username, password, null, this::tratarResposta);

        return vo.getUsername() != null;
    }

    /**
     * trata a resposta enviada pela api do allowme
     * quando o usuario já tiver um cadastro na base da tempest o método retorna o perfil cadastrado,
     * caso contrario o mesmo não retorna nada e deixamos o objeto como nulo para ser tratado posteriormente
     * @param resp response da requisição da api do allowme
     * @return objeto que representa o perfil do usuario cadastrado no allowme
     */
    private UsuarioAllowMeVo tratarResposta(CloseableHttpResponse resp)  {
        UsuarioAllowMeVo response = new UsuarioAllowMeVo();
        try {
            response = UtilitarioJson.toObjectWithConfigureFailOnUnknowProperties(resp, UsuarioAllowMeVo.class, false);
        } catch (Exception e){
            response = null;
        }
        return response;
    }

}
