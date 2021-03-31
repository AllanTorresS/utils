package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.vo.IncluirExigenciaNfeRespostaVo;


public interface ISalesForceParametroNotaFiscalDados {
    /**
     * Atualiza exigencia de nota fiscal no SalesForce
     * @param cnpj cnpj da frota
     * @param exigeNotaFiscal exigencia de nota fiscal
     * @return  resposta do sales force
     */
    IncluirExigenciaNfeRespostaVo atualizarExigenciaNotaFiscal(String cnpj, boolean exigeNotaFiscal);
}
