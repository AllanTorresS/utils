package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IHistoricoParametroNotaFiscalDados;
import ipp.aci.boleia.dados.IParametroNotaFiscalDados;
import ipp.aci.boleia.dominio.ParametroNotaFiscal;
import ipp.aci.boleia.dominio.historico.HistoricoParametroNotaFiscal;
import ipp.aci.boleia.dominio.historico.HistoricoParametroNotaFiscalUf;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import java.util.Date;
import java.util.stream.Collectors;
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
            final Date dataAmbiente = ambiente.buscarDataAmbiente();
            historicoParametroNotaFiscal.setDataHistorico(dataAmbiente);
            historicoParametroNotaFiscal.setNfTipoAgrupamento(parametroNotaFiscal.getNfTipoAgrupamento());
            historicoParametroNotaFiscal.setLocalDestino(parametroNotaFiscal.getLocalDestino());
            historicoParametroNotaFiscal.setSepararPorCombustivelProdutoServico(parametroNotaFiscal.getSepararPorCombustivelProdutoServico());
            historicoParametroNotaFiscal.setDadosAdicionais(parametroNotaFiscal.getDadosAdicionais());
            historicoParametroNotaFiscal.setUnidadeLocalDestinoPadrao(parametroNotaFiscal.getUnidadeLocalDestinoPadrao());
            historicoParametroNotaFiscal.setParametroNotaFiscalUfs(
                    parametroNotaFiscal.getParametroNotaFiscalUfs()
                            .stream()
                            .map(uf -> {
                                HistoricoParametroNotaFiscalUf historicoParametroNotaFiscalUf = new HistoricoParametroNotaFiscalUf();
                                historicoParametroNotaFiscalUf.setDataHistorico(dataAmbiente);
                                historicoParametroNotaFiscalUf.setParametroNotaFiscal(historicoParametroNotaFiscal);
                                historicoParametroNotaFiscalUf.setUf(uf.getUf());
                                historicoParametroNotaFiscalUf.setUnidadeLocalDestino(uf.getUnidadeLocalDestino());
                                return historicoParametroNotaFiscalUf;
                            }).collect(Collectors.toList())
            );
            historicoParametroNotaFiscal.setDataVigencia(parametroNotaFiscal.getDataVigencia());
            return historicoParametroNotaFiscalDados.armazenar(historicoParametroNotaFiscal);
        }
        return null;
    }
}
