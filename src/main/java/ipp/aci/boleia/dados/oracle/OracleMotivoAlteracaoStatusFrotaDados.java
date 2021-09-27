package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IMotivoAlteracaoStatusFrotaDados;
import ipp.aci.boleia.dominio.MotivoAlteracaoStatusFrota;
import ipp.aci.boleia.dominio.enums.TipoAlteracaoStatusFrota;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.util.Ordenacao;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Respositorio dos motivos de inativacao e ativacao da frota
 */
@Repository
public class OracleMotivoAlteracaoStatusFrotaDados extends OracleRepositorioBoleiaDados<MotivoAlteracaoStatusFrota> implements IMotivoAlteracaoStatusFrotaDados {

    /**
     * Instancia o repositorio
     */
    public OracleMotivoAlteracaoStatusFrotaDados() {
        super(MotivoAlteracaoStatusFrota.class);
    }


    public MotivoAlteracaoStatusFrota obterUltimaInativacao(Long idFrota){
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("frota.id", idFrota));
        parametros.add(new ParametroPesquisaIgual("tipoAlteracaoStatus", TipoAlteracaoStatusFrota.INATIVACAO.getValue()));
        InformacaoPaginacao paginacao = new InformacaoPaginacao();
        paginacao.setPagina(1);
        paginacao.setTamanhoPagina(1);
        paginacao.getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna("dataCriacao",Ordenacao.DECRESCENTE));
        List<MotivoAlteracaoStatusFrota> inativacoes = pesquisar(paginacao, parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
        if(inativacoes.isEmpty()){
            return null;
        }
        else{
            return inativacoes.get(0);
        }
    }
}