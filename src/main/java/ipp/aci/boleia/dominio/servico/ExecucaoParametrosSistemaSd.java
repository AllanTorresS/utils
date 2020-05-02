package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IFrotaParametroSistemaDados;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.enums.GrupoExecucaoParametroSistema;
import ipp.aci.boleia.dominio.enums.ParametroSistema;
import ipp.aci.boleia.dominio.enums.TipoRestritividade;
import ipp.aci.boleia.dominio.parametros.ILogicaParametroSistema;
import ipp.aci.boleia.dominio.parametros.ITratadorViolacoesParametros;
import ipp.aci.boleia.dominio.vo.ContextoExecucaoParametroSistemaVo;
import ipp.aci.boleia.dominio.vo.ResultadoExecucaoParametroSistemaVo;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Concentra a logica de execucao dos parametros dinamicos de funcionamento do sistema
 */
@Component
public class ExecucaoParametrosSistemaSd implements ApplicationContextAware {

    private ApplicationContext appContext;

    @Autowired
    private IFrotaParametroSistemaDados repoParams;

    /**
     * Executa um grupo de parametros do sistema. Caso algum dos parametros seja violado, interrompe a execucao,
     * retornando imediatamente o erro obtido.
     *
     * @param frota A frota em questao
     * @param grupo O grupo desejado
     * @param dados O contexto de execucao do grupo
     * @param <D> A classe dos dados necessarios a execucao
     * @return O resultado final do processamento dos parametros
     * @throws ExcecaoValidacao Em caso de infracao de regras de negocio ou de nao cumprimento do contrato do servico
     */
    public <D> ContextoExecucaoParametroSistemaVo<D> executarParametros(Frota frota, GrupoExecucaoParametroSistema grupo, D dados) throws ExcecaoValidacao {

        ContextoExecucaoParametroSistemaVo<D> contexto = new ContextoExecucaoParametroSistemaVo<>(dados);
        List<FrotaParametroSistema> listaFrotaParams = repoParams.buscarPorFrotaGrupoExecucaoOrdenadosPorRestritividade(frota.getId(), grupo);
        for (FrotaParametroSistema frotaParam : listaFrotaParams) {
            ParametroSistema param = ParametroSistema.obterPorCodigo(frotaParam.getParametroSistema());
            if(frotaParam.isAtivo() && grupo.contem(param)) {
                ILogicaParametroSistema<D> logica = obterLogicaExecucao(param);
                ResultadoExecucaoParametroSistemaVo<D> resultado = logica.executar(contexto, frotaParam);
                resultado.setParametro(frotaParam);
                contexto.acumularResultado(resultado);
                if(resultado.isErro() && resultado.isRestritivo()) {
                    break;
                }
            }
        }

        if (!contexto.isSucesso()) {
            ITratadorViolacoesParametros<D> tratadorViolacoes = obterTratadorViolacoes(grupo);
            if(contexto.possuiViolacaoRestritiva()) {
                ResultadoExecucaoParametroSistemaVo violacaoRestritiva = contexto.getViolacaoRestritiva();
                if(TipoRestritividade.RESTRITIVA.equals(violacaoRestritiva.getParametroSistema().getTipoRestritividade())) {
                    tratadorViolacoes.tratarViolacaoRegraRestritiva(contexto);
                } else {
                    tratadorViolacoes.tratarViolacaoRegraVersatil(contexto);
                }
            } else if(contexto.possuiViolacaoInformativa()) {
                tratadorViolacoes.tratarViolacoesInformativas(contexto);
            }
        }

        return contexto;
    }

    /**
     * Obtem uma instancia da classe responsavel por processar a logica de execucao do parametro informado
     * @param parametro O parametro de sistema desejado
     * @return A logica de execucao do parametro
     */
    private <D> ILogicaParametroSistema<D> obterLogicaExecucao(ParametroSistema parametro) {
        Class<ILogicaParametroSistema<?>> classeLogicaExecucao = parametro.getLogicaExecucao();
        return (ILogicaParametroSistema<D>) appContext.getBean(classeLogicaExecucao);
    }

    /**
     * Obtem uma instancia da classe responsavel por tratar as violacoes de parametros de sistema
     * @param grupoParametros O grupo de parametros para o qual as violacoes ocorreram
     * @return O tratador de violacoes
     */
    private <D> ITratadorViolacoesParametros<D> obterTratadorViolacoes(GrupoExecucaoParametroSistema grupoParametros) {
        Class<ITratadorViolacoesParametros<?>> classeTratador = grupoParametros.getTratadorViolacoes();
        return (ITratadorViolacoesParametros<D>) appContext.getBean(classeTratador);
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appContext = applicationContext;
    }
}
