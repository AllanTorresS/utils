package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IDownloadArquivosDados;
import ipp.aci.boleia.dominio.DownloadArquivos;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.StatusPrecoFrete;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import ipp.aci.boleia.util.Ordenacao;
import org.springframework.stereotype.Repository;

/**
 * Respositorio de entidades de
 * DownloadArquivos
 */
@Repository
public class OracleDownloadArquivoDados extends OracleRepositorioBoleiaDados<DownloadArquivos> implements IDownloadArquivosDados {

    /**
     * Instancia o repositorio
     */
    public OracleDownloadArquivoDados() {
        super(DownloadArquivos.class);
    }

    @Override
    public DownloadArquivos obterDownloadArquivoPorChaveIdentificadora(String chaveIdentificadora) {
        return pesquisarSemIsolamentoDados(new ParametroOrdenacaoColuna("dataRequisicao", Ordenacao.DECRESCENTE),
                new ParametroPesquisaIgual("chaveIdentificadora", chaveIdentificadora)
        ).stream().findFirst().orElse(null);
    }
}

