package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IHistoricoParametroNotaFiscalDados;
import ipp.aci.boleia.dados.IParametroNotaFiscalDados;
import ipp.aci.boleia.dominio.ParametroNotaFiscal;
import ipp.aci.boleia.dominio.historico.HistoricoParametroNotaFiscal;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Serviços de domínio da entidade HistoricoUnidade.
 */
@Component
public class HistoricoParametroNotaFiscalSd {


    @Autowired
    private UtilitarioAmbiente ambiente;

    @Autowired
    private IParametroNotaFiscalDados repositorio;

    @Autowired
    private IHistoricoParametroNotaFiscalDados historicoParametroNotaFiscalDados;

    /**
     * Armazena o histórico de uma parâmetro nota fiscal
     * @param parametroNotaFiscal O parâmetro a ter seu historico armazenado
     * @return O historico armazenado
     */
    public HistoricoParametroNotaFiscal armazenar(ParametroNotaFiscal parametroNotaFiscal) {
        if(parametroNotaFiscal.getId() != null) {
            ParametroNotaFiscal dadosAntigos = repositorio.obterPorId(parametroNotaFiscal.getId());
            if(dadosAntigos != null) {
                HistoricoParametroNotaFiscal historicoParametroNotaFiscal = new HistoricoParametroNotaFiscal();
                historicoParametroNotaFiscal.setParametroNotaFiscal(dadosAntigos);
                historicoParametroNotaFiscal.setDataHistorico(ambiente.buscarDataAmbiente());
                historicoParametroNotaFiscal.setNfTipoAgrupamento(dadosAntigos.getNfTipoAgrupamento());
                historicoParametroNotaFiscal.setLocalDestino(dadosAntigos.getLocalDestino());
                historicoParametroNotaFiscal.setSepararPorCombustivelProdutoServico(dadosAntigos.getSepararPorCombustivelProdutoServico());
                historicoParametroNotaFiscal.setDadosAdicionais(dadosAntigos.getDadosAdicionais());
                historicoParametroNotaFiscal.setLocalDestinoPadrao(parametroNotaFiscal.getUnidadeLocalDestinoPadrao());
                return historicoParametroNotaFiscalDados.armazenar(historicoParametroNotaFiscal);
            }
        }
        return null;
    }
}
