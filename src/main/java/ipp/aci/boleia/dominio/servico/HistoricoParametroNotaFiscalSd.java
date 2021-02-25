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
        if(parametroNotaFiscal != null && parametroNotaFiscal.getId() != null) {
            HistoricoParametroNotaFiscal historicoParametroNotaFiscal = new HistoricoParametroNotaFiscal();
            historicoParametroNotaFiscal.setParametroNotaFiscal(parametroNotaFiscal);
            historicoParametroNotaFiscal.setDataHistorico(ambiente.buscarDataAmbiente());
            historicoParametroNotaFiscal.setNfTipoAgrupamento(parametroNotaFiscal.getNfTipoAgrupamento());
            historicoParametroNotaFiscal.setLocalDestino(parametroNotaFiscal.getLocalDestino());
            historicoParametroNotaFiscal.setSepararPorCombustivelProdutoServico(parametroNotaFiscal.getSepararPorCombustivelProdutoServico());
            historicoParametroNotaFiscal.setDadosAdicionais(parametroNotaFiscal.getDadosAdicionais());
            historicoParametroNotaFiscal.setUnidadeLocalDestinoPadrao(parametroNotaFiscal.getUnidadeLocalDestinoPadrao());
            return historicoParametroNotaFiscalDados.armazenar(historicoParametroNotaFiscal);
        }
        return null;
    }
}
