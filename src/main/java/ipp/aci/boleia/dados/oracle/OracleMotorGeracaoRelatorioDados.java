package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IMotorGeracaoRelatoriosDados;
import ipp.aci.boleia.dominio.MotorGeracaoRelatorios;
import ipp.aci.boleia.dominio.enums.StatusMotorGeradorRelatorio;
import ipp.aci.boleia.dominio.enums.TipoRelatorioMotorGerador;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIn;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaOr;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaMotorGeracaoRelatoriosVo;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Repositorio de entidades MotorGeracaoRelatorio
 */
@Repository
public class OracleMotorGeracaoRelatorioDados extends OracleRepositorioBoleiaDados<MotorGeracaoRelatorios> implements IMotorGeracaoRelatoriosDados {

    @Autowired
    private UtilitarioAmbiente ambiente;

    /**
     * Instancia o repositorio
     */
    public OracleMotorGeracaoRelatorioDados() { super(MotorGeracaoRelatorios.class); }

    @Override
    public ResultadoPaginado<MotorGeracaoRelatorios> pesquisar(FiltroPesquisaMotorGeracaoRelatoriosVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();

        if (filtro.getTipoRelatorioMotorGerador() != null && filtro.getTipoRelatorioMotorGerador().getName() != null) {
            parametros.add(new ParametroPesquisaIgual("tipoRelatorio",
                    TipoRelatorioMotorGerador.valueOf(filtro.getTipoRelatorioMotorGerador().getName()).getValue()));
        }

        parametros.add((new ParametroPesquisaIgual( "usuario", ambiente.getUsuarioLogado())));

        if (filtro.getStatusMotorGeracaoRelatorio() != null && filtro.getStatusMotorGeracaoRelatorio().getName() != null) {
            if(StatusMotorGeradorRelatorio.EXCLUIDO.name().equals(filtro.getStatusMotorGeracaoRelatorio().getName())){
                parametros.add( new ParametroPesquisaIn("status",
                        Arrays.asList(StatusMotorGeradorRelatorio.EXCLUIDO.getValue(), StatusMotorGeradorRelatorio.CONCLUIDO.getValue())));
                parametros.add(new ParametroPesquisaDataMenorOuIgual("dataDescarte", ambiente.buscarDataAmbiente()));
            }else {
                parametros.add(new ParametroPesquisaIgual("status",
                        StatusMotorGeradorRelatorio.valueOf(filtro.getStatusMotorGeracaoRelatorio().getName()).getValue()));
            }
        }

        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public MotorGeracaoRelatorios obterRelatorioParaProcessamento(Long id) {
        List<ParametroPesquisa> parametros = new ArrayList<>();

        parametros.add(new ParametroPesquisaIgual("id", id));

        return pesquisar(new InformacaoPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]))
                .getRegistros().stream().findFirst().orElse(null);
    }

    @Override
    public Boolean pesquisaRelatorioEmAndamento(String filtro, Integer tipoRelatorio) {
        List<ParametroPesquisa> parametros = new ArrayList<>();

        parametros.add(new ParametroPesquisaIgual("filtro", filtro));
        parametros.add(new ParametroPesquisaIgual("tipoRelatorio", tipoRelatorio));
        parametros.add(
                new ParametroPesquisaOr(
                    new ParametroPesquisaIgual("status", StatusMotorGeradorRelatorio.EM_ANDAMENTO_AGUARDANDO.getValue()),
                    new ParametroPesquisaIgual("status", StatusMotorGeradorRelatorio.EM_ANDAMENTO_PROCESSANDO.getValue())
                )
        );
        return pesquisar(new InformacaoPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]))
                .getRegistros().stream().findFirst().isPresent();
    }
}
