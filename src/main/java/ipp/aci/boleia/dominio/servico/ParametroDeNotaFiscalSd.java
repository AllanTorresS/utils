package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IParametroCicloDados;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.ParametroCiclo;
import ipp.aci.boleia.dominio.interfaces.IParametroNotaFiscal;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import java.util.Calendar;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implementa as regras de negocio relacionadas a entidade ParametroDeNotaFiscal
 */
@Component
public class ParametroDeNotaFiscalSd {

    @Autowired
    private UtilitarioAmbiente ambiente;

    @Autowired
    private IParametroCicloDados parametroCicloDados;

    @Autowired
    private TransacaoConsolidadaSd transacaoConsolidadaSd;

    private static final int QUANTIDADE_DIAS_PARA_EXIBIR_ALERTA_NOVO_PARAMETRO = 3;

    /**
     * Calcula a data de vigência do parametro de nota fiscal de uma frota.
     * O valor deve ser a data inicial do próximo cíclo da frota;
     *
     * @param frota a frota do parametro de nota.
     * @return a data de vigência
     */
    public Date calcularDataVigencia(Frota frota) {
        ParametroCiclo parametroCiclo = parametroCicloDados.obterParametroCicloDaFrota(frota.getId());
        Date dataAtual = ambiente.buscarDataAmbiente();
        final int prazoCiclo = parametroCiclo.getPrazoCiclo().intValue();
        int diaInicioCicloAtual = (((UtilitarioCalculoData.obterCampoData(dataAtual, Calendar.DAY_OF_MONTH) - 1) / prazoCiclo) * prazoCiclo + 1);
        Date dataInicioCiclo = UtilitarioCalculoData.definirTempoData(dataAtual, diaInicioCicloAtual, Calendar.DAY_OF_MONTH);
        Date dataFimCiclo = transacaoConsolidadaSd.calcularDataFimPeriodo(dataInicioCiclo, parametroCiclo);
        Date dataInicioCicloAtual = UtilitarioCalculoData.adicionarDiasData(UtilitarioCalculoData.obterPrimeiroInstanteDia(dataFimCiclo),1);
        return dataInicioCicloAtual;
    }

    public Boolean deveAlertarNovoParametroNfe(IParametroNotaFiscal notaFiscal){
        Calendar dataLimite = Calendar.getInstance();
        dataLimite.setTime(ambiente.buscarDataAmbiente());
        dataLimite.add(Calendar.DATE,QUANTIDADE_DIAS_PARA_EXIBIR_ALERTA_NOVO_PARAMETRO);
        Date prazo = dataLimite.getTime();
       return notaFiscal.getDataVigencia().before(prazo) || notaFiscal.getDataVigencia().equals(prazo);
    }
}
