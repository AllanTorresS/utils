package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IAgenciadorFreteTaxaDados;
import ipp.aci.boleia.dados.IPrecoFreteDados;
import ipp.aci.boleia.dominio.agenciadorfrete.AgenciadorFreteTaxa;
import ipp.aci.boleia.dominio.agenciadorfrete.PrecoFrete;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Respositorio de entidades Agenciador de Frete
 */
@Repository
public class OraclePrecoFreteDados extends OracleRepositorioBoleiaDados<PrecoFrete> implements IPrecoFreteDados {

    /**
     * Instancia o repositorio
     */
    public OraclePrecoFreteDados() {
        super(PrecoFrete.class);
    }

}