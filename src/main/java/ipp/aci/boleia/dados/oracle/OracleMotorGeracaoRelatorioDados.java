package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IMotorGeracaoRelatoriosDados;
import ipp.aci.boleia.dominio.MotorGeracaoRelatorios;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
    public MotorGeracaoRelatorios obterRelatorioParaProcessamento(Long id) {
        List<ParametroPesquisa> parametros = new ArrayList<>();

        parametros.add(new ParametroPesquisaIgual("id",id));
//        parametros.add(new ParametroPesquisaIgual("status", StatusMotorGeradorRelatorio.EM_ANDAMENTO_AGUARDANDO.getValue()));

        return pesquisar(new InformacaoPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]))
                .getRegistros().stream().findFirst().orElse(null);
    }
}
