package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IComandaDigitalDados;
import ipp.aci.boleia.dados.IEmpresaAgregadaDados;
import ipp.aci.boleia.dados.IFrotaDados;
import ipp.aci.boleia.dados.IGrupoOperacionalDados;
import ipp.aci.boleia.dados.IHistoricoUnidadeDados;
import ipp.aci.boleia.dados.IMotoristaDados;
import ipp.aci.boleia.dados.IUnidadeDados;
import ipp.aci.boleia.dados.IUsuarioDados;
import ipp.aci.boleia.dados.IVeiculoDados;
import ipp.aci.boleia.dominio.Unidade;
import ipp.aci.boleia.dominio.historico.HistoricoUnidade;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Serviços de domínio da entidade HistoricoUnidade.
 */
@Component
public class HistoricoUnidadeSd {


    @Autowired
    private UtilitarioAmbiente ambiente;

    @Autowired
    private IUnidadeDados repositorio;

    @Autowired
    private IFrotaDados repositorioFrota;

    @Autowired
    private IComandaDigitalDados repositorioComandaDigital;

    @Autowired
    private IGrupoOperacionalDados grupoOperacionalDados;

    @Autowired
    private IMotoristaDados motoristaDados;

    @Autowired
    private IVeiculoDados veiculoDados;

    @Autowired
    private IUsuarioDados usuarioDados;

    @Autowired
    private IEmpresaAgregadaDados empresaAgregadaDados;

    @Autowired
    private IHistoricoUnidadeDados historicoUnidadeDados;

    /**
     * Armazena o histórico de uma unidade
     * @param unidade A unidade a ter seu historico armazenado
     * @return O historico armazenado
     */
    public HistoricoUnidade armazenar(Unidade unidade) {
        if(unidade.getId() != null) {
            Unidade dadosAntigos = repositorio.obterPorId(unidade.getId());
            if(dadosAntigos != null) {
                HistoricoUnidade historicoUnidade = new HistoricoUnidade();
                historicoUnidade.setUnidade(dadosAntigos);
                historicoUnidade.setDataHistorico(ambiente.buscarDataAmbiente());
                historicoUnidade.setExigeNotaFiscal(dadosAntigos.getExigeNotaFiscal());
                historicoUnidade.setLocalDestinoPadraoNfeUf(dadosAntigos.getLocalDestinoPadraoNfeUf());
                return historicoUnidadeDados.armazenar(historicoUnidade);
            }
        }
        return null;
    }
}
