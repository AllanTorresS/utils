package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.INfeArmazemDados;
import ipp.aci.boleia.dominio.NfeArmazem;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.util.Ordenacao;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

/**
 * Repositório de entidades NfeArmazem
 */
@Repository
public class OracleNfeArmazemDados extends OracleRepositorioBoleiaDados<NfeArmazem> implements INfeArmazemDados {

    /**
     * Instancia o repositorio
     */
    public OracleNfeArmazemDados() {
        super(NfeArmazem.class);
    }

    @Override
    public Date obterUltimaDataLeitura() {
        InformacaoPaginacao paginacao = new InformacaoPaginacao();
        paginacao.setParametrosOrdenacaoColuna( Arrays.asList(new ParametroOrdenacaoColuna("dataUltimaLeitura", Ordenacao.DECRESCENTE),
                                                new ParametroOrdenacaoColuna("dataEnvio", Ordenacao.DECRESCENTE)));
        paginacao.setPagina(1);
        paginacao.setTamanhoPagina(1);

        Optional<NfeArmazem> resultado = pesquisar(paginacao, null).getRegistros().stream().findFirst();
        return resultado.isPresent() ? resultado.get().getDataUltimaLeitura() : null;
    }
}
