package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IComandaDigitalDados;
import ipp.aci.boleia.dados.ITokenDados;
import ipp.aci.boleia.dominio.ComandaDigital;
import ipp.aci.boleia.dominio.enums.StatusBloqueio;
import ipp.aci.boleia.dominio.enums.StatusHabilitacao;
import ipp.aci.boleia.dominio.enums.TipoToken;
import ipp.aci.boleia.dominio.vo.TokenVo;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implementa as regras de negocio relacionadas a entidade Unidade
 */
@Component
public class ComandaDigitalSd {

    @Autowired
    private IComandaDigitalDados repositorio;

    @Autowired
    private UtilitarioAmbiente ambiente;

    @Autowired
    private Mensagens mensagens;

    @Autowired
    private ITokenDados tokenDados;

    /**
     * Habilita uma dada comanda no cadastro do revendedor
     *
     * @param token O token de habilitacao da comanda
     * @param udidDispositivo  O UDID do aparelho
     * @param numeroSerie O numero de serie do aparelho
     * @param modelo O modelo do aparelho
     * @param marca A marca do aparelho
     * @param sistemaoperacional o sistema operacional do aparelho
     * @return A comanda encontrada e habilitada
     * @throws ExcecaoValidacao Em caso de erro de validacao do token ou token não encontrado
     */
    public ComandaDigital habilitar(String token, String udidDispositivo, String numeroSerie, String modelo, String marca, String sistemaoperacional) throws ExcecaoValidacao {
        ComandaDigital comanda = repositorio.obterPorToken(token);

        if(comanda==null) {
            throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO, mensagens.obterMensagem("comanda.servico.validacao.token.invalido"));
        } else if(comanda.getDataExpiracaoToken()!=null && comanda.getDataExpiracaoToken().getTime() < ambiente.buscarDataAmbiente().getTime()) {
            throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO, mensagens.obterMensagem("comanda.servico.validacao.token.expirado"));
        } else if(comanda.getHabilitado().equals(StatusHabilitacao.HABILITADO.getValue())) {
            throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO, mensagens.obterMensagem("comanda.servico.validacao.token.habilitado"));
        } else if(comanda.getPontoVenda() != null && !comanda.getPontoVenda().getStatusHabilitacao().equals(StatusHabilitacao.HABILITADO.getValue())) {
            throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO, mensagens.obterMensagem("comanda.servico.validacao.pv.habilitado"));
        }


        comanda.setHabilitado(StatusHabilitacao.HABILITADO.getValue());
        comanda.setUdidDispositivo(udidDispositivo);
        comanda.setNumeroSerie(numeroSerie);
        comanda.setModeloDispositivo(modelo);
        comanda.setMarcaDispositivo(marca);
        comanda.setSoDispositivo(sistemaoperacional);
        comanda.setDataExpiracaoToken(null);

        return repositorio.armazenar(comanda);
    }

    /**
     * Bloqueia/Desbloqueia uma dada comanda no cadastro do revendedor
     *
     * @param id O id da comanda
     * @param statusBloqueio O novo status de bloqueio
     * @return A comanda alterada
     * @throws ExcecaoValidacao Em caso de erro para comanda não encontrada
     */
    public ComandaDigital editarBloqueio(Long id, StatusBloqueio statusBloqueio) throws ExcecaoValidacao {
        ComandaDigital comanda = repositorio.obterPorId(id);
        if(comanda!=null) {
            comanda.setBloqueado(statusBloqueio.getValue());
            return repositorio.armazenar(comanda);
        } else {
            throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO,mensagens.obterMensagem("comanda.servico.validacao.id.inexistente"));
        }
    }

    /**
     * Gera um novo token para comanda digital
     * @param comandaDigital comanda a atualizar com novo token
     * @return A comanda alterada
     */
    public ComandaDigital gerarToken(ComandaDigital comandaDigital) {
        TokenVo token = tokenDados.novoToken(TipoToken.APP_COMANDA_DIGITAL);
        comandaDigital.setToken(token.getToken());
        comandaDigital.setDataExpiracaoToken(token.getDataExpiracaoToken());
        comandaDigital.setHabilitado(StatusHabilitacao.NAO_HABILITADO.getValue());
        return repositorio.armazenar(comandaDigital);
    }
}
