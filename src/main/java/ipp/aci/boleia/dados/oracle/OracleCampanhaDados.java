package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.ICampanhaDados;
import ipp.aci.boleia.dominio.campanha.Campanha;
import ipp.aci.boleia.dominio.enums.StatusCampanha;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaior;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenor;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDiferente;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaOr;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaCampanhaVo;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Respositorio de entidades Campanha
 */
@Repository
public class OracleCampanhaDados extends OracleRepositorioBoleiaDados<Campanha> implements ICampanhaDados {

    /**
     * Instancia o repositorio
     */
    public OracleCampanhaDados() {
        super(Campanha.class);
    }

    @Autowired
    protected UtilitarioAmbiente ambiente;

    @Override
    public ResultadoPaginado<Campanha> pesquisar(FiltroPesquisaCampanhaVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();

        if(filtro.getNome() != null) {
            povoarParametroLike("nome", filtro.getNome(), parametros);
        }

        if(filtro.getDataInicio() != null){
            parametros.add(new ParametroPesquisaDataMaiorOuIgual("dataInicio", UtilitarioCalculoData.obterPrimeiroInstanteDia(filtro.getDataInicio())));
        }
        if(filtro.getDataFim() != null){
            parametros.add(new ParametroPesquisaDataMenorOuIgual("dataFim", UtilitarioCalculoData.obterUltimoInstanteDia(filtro.getDataFim())));
        }

        if(filtro.getStatus() != null && filtro.getStatus().getName() != null) {
            if (StatusCampanha.ATIVO.name().equals(filtro.getStatus().getName())) {
                Date dataAmbiente = ambiente.buscarDataAmbiente();
                parametros.add(new ParametroPesquisaIgual("status", StatusCampanha.ATIVO.getValue()));
                parametros.add(new ParametroPesquisaDataMenorOuIgual("dataInicio", dataAmbiente));
                parametros.add(new ParametroPesquisaDataMaiorOuIgual("dataFim", dataAmbiente));
            } else if (StatusCampanha.AGENDADO.name().equals(filtro.getStatus().getName())) {
                Date dataAmbiente = ambiente.buscarDataAmbiente();
                parametros.add(new ParametroPesquisaIgual("status", StatusCampanha.ATIVO.getValue()));
                parametros.add(new ParametroPesquisaDataMaior("dataInicio", dataAmbiente));
            } else if (StatusCampanha.ENCERRADO.name().equals(filtro.getStatus().getName())) {
                Date dataAmbiente = ambiente.buscarDataAmbiente();
                parametros.add(new ParametroPesquisaIgual("status", StatusCampanha.ATIVO.getValue()));
                parametros.add(new ParametroPesquisaDataMenor("dataFim", dataAmbiente));
            } else {
                parametros.add(new ParametroPesquisaIgual("status", StatusCampanha.valueOf(filtro.getStatus().getName()).getValue()));
            }
        }

        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public ResultadoPaginado<Campanha> buscarAtivasEAgendadas() {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(
                new ParametroPesquisaOr(
                        new ParametroPesquisaIgual("status", StatusCampanha.ATIVO.getValue()),
                        new ParametroPesquisaIgual("status", StatusCampanha.AGENDADO.getValue())
                )
        );
        parametros.add(new ParametroPesquisaDataMaior("dataFim", ambiente.buscarDataAmbiente()));

        return pesquisar(new InformacaoPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public ResultadoPaginado<Campanha> buscarAtivas(Date dataAbastecimento) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("status", StatusCampanha.ATIVO.getValue()));
        parametros.add(new ParametroPesquisaDataMenorOuIgual("dataInicio", dataAbastecimento));
        parametros.add(new ParametroPesquisaDataMaiorOuIgual("dataFim", dataAbastecimento));

        return pesquisar(new InformacaoPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public List<Campanha> buscarCampanhasASeremAlteradas() {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        povoarParametrosPesquisa(parametros);
        return pesquisar((InformacaoPaginacao) null, parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
    }

    /**
     * Cria a lista de parâmetros a serem passados para a pesquisa de campanhas a terem status alterado
     * @param parametros a lista de parâmetros
     */
    private void povoarParametrosPesquisa(List<ParametroPesquisa> parametros) {
        ParametroPesquisa parametroNotEncerrada = new ParametroPesquisaDiferente("status", StatusCampanha.ENCERRADO.getValue());
        parametros.add(parametroNotEncerrada);
    }
}
