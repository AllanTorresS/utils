package ipp.aci.boleia.dominio.vo.frotista;

import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;

import java.util.ArrayList;

/**
 * Informação Paginação Frotista
 * 
 */
public class InformacaoPaginacaoFrtVo extends InformacaoPaginacao {

    /**
     * Construtor da informacao de paginacao
     */
    public InformacaoPaginacaoFrtVo() {
        setPagina(1);
        setTamanhoPagina(100);
    }

    /**
     * Construtor da informacao de paginacao
     * @param pagina A pagina
     * @param parametros Os parametros de ordenacao por coluna
     */
    public InformacaoPaginacaoFrtVo(Integer pagina, ParametroOrdenacaoColuna ... parametros) {
        this();
        setPagina(pagina != null ? pagina : 1);
        setParametrosOrdenacaoColuna(new ArrayList<>());
        for(ParametroOrdenacaoColuna parametro : parametros) {
            getParametrosOrdenacaoColuna().add(parametro);
        }
    }

}