package ipp.aci.boleia.dominio.servico;


import ipp.aci.boleia.dados.IVipDados;
import ipp.aci.boleia.dominio.Vip;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Encapsula as regras de negocio que envolvem a manipulacao de Vip
 */
@Component
public class VipSd {

    private static final int[] TEMPOS_BLOQUEIO_ERRO_AUTENTICACAO = {0, 0, 0, 30, 60, 90};
    private static final String AUTENTICAR_INVALIDO = "vip.servico.autenticar.invalido";
    private static final String AUTENTICAR_INVALIDO_PDV_WEB = "autorizador.servico.validacao.vip.autenticar.invalido";

    @Autowired
    private Mensagens mensagens;

    @Autowired
    private IVipDados repositorio;

    @Autowired
    private UtilitarioAmbiente ambiente;

    /**
     * Valida o código do VIP e o código corporativo do PV ao qual o VIP pertence
     * @param codigoCorporativoPV codigo corporativo do ponto de venda
     * @param codigoVip indentificador do VIP
     * @param ehAcessoPDV identifica se o acesso eh do PDV Web
     * @throws ExcecaoValidacao caso os dados do vip nao sejam validos
     */
    public void validarDadosVip(Long codigoCorporativoPV, String codigoVip, boolean ehAcessoPDV) throws ExcecaoValidacao {
        boolean valido = codigoVip != null && codigoVip.matches("\\d\\d+") && codigoCorporativoPV != null;
        if (!valido) {
            throw new ExcecaoValidacao(mensagens.obterMensagem(ehAcessoPDV ? AUTENTICAR_INVALIDO_PDV_WEB : AUTENTICAR_INVALIDO));
        }
    }

    /**
     * Verifica o preenchimento dos dados de entrada do VIP
     * @param codigoCorporativoPV codigo do ponto de venda
     * @param codigoVip identificador do vip
     * @param senhaVip senha do vip
     * @param ehAcessoPdvWeb identifica se o acesso eh do PDV Web
     * @throws ExcecaoValidacao em caso de falha na validação ou autenticação do VIP
     */
    public void autenticar(Long codigoCorporativoPV, String codigoVip, String senhaVip, boolean ehAcessoPdvWeb) throws ExcecaoValidacao{
        final String mensagemErroAutenticar = ehAcessoPdvWeb ? AUTENTICAR_INVALIDO_PDV_WEB : AUTENTICAR_INVALIDO;

        validarDadosVip(codigoCorporativoPV, codigoVip, ehAcessoPdvWeb);
        Vip vip = repositorio.obterVip(codigoVip, codigoCorporativoPV);
        if (vip != null) {
            verificarBloqueioErrosAutenticacaoConsecutivos(vip);
            if (!ehAcessoPdvWeb) {
                if (vip.getSenha().equals(senhaVip)) {
                    apagarDadosBloqueioTemporarioVip(vip);
                    return;
                } else {
                    bloquearVipTemporariamenteSeNecessario(vip);
                    throw new ExcecaoValidacao(mensagens.obterMensagem(mensagemErroAutenticar));
                }
            } else {
                return;
            }
        }
        throw new ExcecaoValidacao(mensagens.obterMensagem(mensagemErroAutenticar));
    }

    /**
     * Verifica se o vip esta bloqueado por erros consecutivos de autenticacao
     *
     * @param vip O VIP
     * @throws ExcecaoValidacao caso o vip esteja bloqueado por erros de autenticacao consecutivos
     */
    private void verificarBloqueioErrosAutenticacaoConsecutivos(Vip vip) throws ExcecaoValidacao {
        Date bloqueadoAte = vip.getBloqueioTemporario();
        long tempoBloqueio = bloqueadoAte != null ? bloqueadoAte.getTime() - ambiente.buscarDataAmbiente().getTime() : 0;
        if (tempoBloqueio > 0) {
            throw new ExcecaoValidacao(mensagens.obterMensagem("vip.servico.autenticar.bloqueio.temporario", tempoBloqueio / 1000));
        }
    }

    /**
     * Quando um VIP erra a senha mais de três vezes consecutivas, o sistema exige que ele aguarde um
     * periodo de tempo para uma nova tentativa. O objetivo desta implementacao eh evitar ataques
     * de forca bruta contra a senha do VIP.
     *
     * @param vip O VIP
     * @throws ExcecaoValidacao caso o vip seja bloqueado por erros de autenticacao consecutivos
     */
    private void bloquearVipTemporariamenteSeNecessario(Vip vip) throws ExcecaoValidacao {

        Integer numErros = vip.getNumeroErrosAutenticacao() != null ? vip.getNumeroErrosAutenticacao() + 1 : 1;
        vip.setNumeroErrosAutenticacao(numErros);
        vip = repositorio.armazenar(vip);

        int idx = Math.min(TEMPOS_BLOQUEIO_ERRO_AUTENTICACAO.length, numErros);
        int tempoBloqueio = TEMPOS_BLOQUEIO_ERRO_AUTENTICACAO[idx - 1];
        if (tempoBloqueio > 0) {
            Date bloqueadoAte = UtilitarioCalculoData.adicionarSegundosData(ambiente.buscarDataAmbiente(), tempoBloqueio);
            vip.setBloqueioTemporario(bloqueadoAte);
            repositorio.armazenar(vip);
            throw new ExcecaoValidacao(mensagens.obterMensagem("vip.servico.autenticar.bloqueio.temporario", tempoBloqueio));
        }
    }

    /**
     * Quano uma autenticacao bem sucedida ocorre, os dados de bloqueio temporario devem ser apagados
     * @param vip O vip
     */
    private void apagarDadosBloqueioTemporarioVip(Vip vip) {
        vip.setBloqueioTemporario(null);
        vip.setNumeroErrosAutenticacao(null);
        repositorio.armazenar(vip);
    }
}
