package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IComandaDigitalDados;
import ipp.aci.boleia.dados.IEmpresaAgregadaDados;
import ipp.aci.boleia.dados.IFrotaDados;
import ipp.aci.boleia.dados.IGrupoOperacionalDados;
import ipp.aci.boleia.dados.IMotoristaDados;
import ipp.aci.boleia.dados.IUnidadeDados;
import ipp.aci.boleia.dados.IUsuarioDados;
import ipp.aci.boleia.dados.IVeiculoDados;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.Motorista;
import ipp.aci.boleia.dominio.Unidade;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Serviços de domínio da entidade Unidade.
 */
@Component
public class UnidadeSd {

    public static final Long ID_UNIDADE_MATRIZ = -1L;
    public static final String NOME_UNIDADE_MATRIZ = "Matriz";

    @Autowired
    private UtilitarioAmbiente ambiente;

    @Autowired
    private IUnidadeDados repositorio;

    @Autowired
    private IFrotaDados repositorioFrota;

    @Autowired
    private IComandaDigitalDados repositorioComandaDigital;

    @Autowired
    private IGrupoOperacionalDados grupoOperacionalDados;

    @Autowired
    private IMotoristaDados motoristaDados;

    @Autowired
    private IVeiculoDados veiculoDados;

    @Autowired
    private IUsuarioDados usuarioDados;

    @Autowired
    private IEmpresaAgregadaDados empresaAgregadaDados;

    @Autowired
    private HistoricoMotoristaSd historicoMotoristaSd;

    @Autowired
    private FluxoAbastecimentoSd fluxoAbastecimentoSd;


    /**
     * Armazena os dados de uma unidade
     * @param unidade A unidade  ser armazenada
     * @return A unidade armazenada
     */
    public Unidade armazenar(Unidade unidade) {
        return repositorio.armazenar(unidade);
    }

    /**
     * Obtem a unidade matriz para a frota logada
     * @return Unidade matriz
     */
    public Unidade criarUnidadeMatriz() {
        return criarUnidadeMatriz(null);
    }

    /**
     * Obtem a unidade matriz para uma frota específica.
     * @param frota Frota que será obtida a unidade matriz.
     * @return Unidade matriz
     */
    public Unidade criarUnidadeMatriz(Frota frota) {
        Unidade unidadeMatriz = new Unidade();
        unidadeMatriz.setId(ID_UNIDADE_MATRIZ);
        if (frota == null) {
            frota = repositorioFrota.obterPorId(ambiente.getFrotaUsuarioLogado().getId());
        }
        unidadeMatriz.setNome(frota.getRazaoSocial());
        unidadeMatriz.setFrota(frota);

        unidadeMatriz.setUf(frota.getUnidadeFederativa());
        unidadeMatriz.setMunicipio(frota.getMunicipio());
        unidadeMatriz.setBairro(frota.getBairro());
        unidadeMatriz.setCep(frota.getCep());
        unidadeMatriz.setLogradouroEndereco(frota.getLogradouro());
        unidadeMatriz.setNumeroEndereco(frota.getNumero());
        unidadeMatriz.setComplementoEndereco(frota.getComplemento());
        unidadeMatriz.setLatitude(frota.getLatitude());
        unidadeMatriz.setLongitude(frota.getLongitude());

        unidadeMatriz.setDddTelefone(frota.getDddTelefone());
        unidadeMatriz.setTelefone(frota.getTelefone());
        unidadeMatriz.setEmail(frota.getEmail());
        unidadeMatriz.setCnpj(frota.getCnpj());
        unidadeMatriz.setInscrEstadual(frota.getInscricaoEstadual());

        unidadeMatriz.setPostoInterno(frota.getPostoInterno());
        unidadeMatriz.setExcluido(frota.getExcluido());
        unidadeMatriz.setExigeNotaFiscal(frota.isSemNotaFiscal() == null || !frota.isSemNotaFiscal());

        return unidadeMatriz;
    }

    /**
     * Exclui uma lista de unidades.
     *
     * @param ids Lista dos identificadores das unidades a serem excluídas.
     */
    public void excluir(Long... ids) {
        desvincularEntidadesRelacionadas(ids);
        repositorioComandaDigital.excluirPorUnidade(ids);
        repositorio.excluir(ids);
    }

    /**
     * Obtem o número de registros relacionados
     * @param id id da Unidade
     * @return Quantidade
     */
    public Long obterQuantidadeRelacionados(Long id) {
        Unidade unidade = repositorio.obterPorId(id);
        return obterQuantidadeRelacionados(unidade);
    }

    /**
     * Obtem o número de registros relacionados
     * @param unidade Unidade a ser usada na busca.
     * @return Quantidade
     */
    public Long obterQuantidadeRelacionados(Unidade unidade) {
        Long quantidade = 0L;
        quantidade += unidade.getGrupo().size();
        quantidade += unidade.getMotorista().size();
        quantidade += unidade.getVeiculo().size();
        quantidade += unidade.getUsuario().size();
        quantidade += unidade.getComandaDigitais().size();
        return quantidade;
    }

    /**
     * Remove o vinculo que uma lista de unidades possui com as demais entidades do sistema.
     *
     * @param ids lista de ids das unidades
     */
    private void desvincularEntidadesRelacionadas(Long... ids) {
        for (Long id : ids) {
            List<Motorista> motoristas = motoristaDados.obterPorUnidade(id);

            grupoOperacionalDados.desvincularUnidades(id);
            motoristaDados.desvincularUnidades(id);
            veiculoDados.desvincularUnidades(id);
            usuarioDados.desvincularUnidades(id);
            empresaAgregadaDados.desvincularUnidades(id);
            if(motoristas != null){
                historicoMotoristaSd.armazenarHistoricoMotoristaAoExcluirEmpresaAgregada(motoristas,ambiente);
                motoristas.forEach(m -> fluxoAbastecimentoSd.excluirFluxoAbastecimentoMotorista(m, ambiente.getUsuarioLogado(), ambiente.buscarDataAmbiente()));
            }
        }
    }
}
