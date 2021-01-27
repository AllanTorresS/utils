package ipp.aci.boleia.dominio.servico;



import ipp.aci.boleia.dados.IHistoricoParametroUsoDados;
import ipp.aci.boleia.dados.IHistoricoStatusParametroUsoDados;
import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.HistoricoStatusParametroDeUso;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Serviços de domínio da entidade {@link HistoricoStatusParametroUsoSd}.
 *
 * @author tiago.marques
 */
@Component
public class HistoricoStatusParametroUsoSd {

    @Autowired
    private UtilitarioAmbiente utilitarioAmbiente;

    @Autowired
    private IHistoricoStatusParametroUsoDados repoStatusParametroUsoDados;



    /**
     * Registra um histórico de parametros de uso e o usuario responsavel
     *
     * @param parametroUso Parametro que está sendo salvo.
     */
    public void registrarHistoricoAtivacaoParametro(FrotaParametroSistema parametroUso) {
        HistoricoStatusParametroDeUso historico = new HistoricoStatusParametroDeUso();
        historico.setFrota(parametroUso.getFrota());
        historico.setUsuario(utilitarioAmbiente.getUsuarioLogado());
        historico.setParametroSistema(parametroUso.getParametroSistema());
        historico.setAtivo(parametroUso.getAtivo());
        historico.setRestritivo(parametroUso.getRestritivo());
        historico.setDataAlteracao(utilitarioAmbiente.buscarDataAmbiente());

        repoStatusParametroUsoDados.armazenar(historico);
    }
}
