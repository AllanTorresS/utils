package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.vo.AtualizarExigenciaNfeErroVo;


public interface ISalesForceParametroNotaFiscalDados {
    /**
     * Atualiza exigencia de nota fiscal no SalesForce
     * @param cnpj cnpj da frota
     * @param exigeNotaFiscal exigencia de nota fiscal
     * @return  resposta do sales force
     */
    AtualizarExigenciaNfeErroVo atualizarExigenciaNotaFiscal(String cnpj, boolean exigeNotaFiscal);
}
