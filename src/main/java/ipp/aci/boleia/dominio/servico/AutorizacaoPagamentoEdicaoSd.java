package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IAutorizacaoPagamentoDados;
import ipp.aci.boleia.dados.IAutorizacaoPagamentoEdicaoDados;
import ipp.aci.boleia.dominio.AutorizacaoChamado;
import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.AutorizacaoPagamentoEdicao;
import ipp.aci.boleia.dominio.EmpresaAgregada;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.GrupoOperacional;
import ipp.aci.boleia.dominio.ItemAutorizacaoPagamento;
import ipp.aci.boleia.dominio.Motorista;
import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.SubTipoVeiculo;
import ipp.aci.boleia.dominio.Unidade;
import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.dominio.enums.StatusEdicao;
import ipp.aci.boleia.dominio.enums.TipoItemAutorizacaoPagamento;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * Implementa regras de negócio relacionadas à edição de abastecimentos.
 */
@Component
public class AutorizacaoPagamentoEdicaoSd {

    @Autowired
    private IAutorizacaoPagamentoDados repositorioAutorizacaoPagamento;

    @Autowired
    private IAutorizacaoPagamentoEdicaoDados repositorioAutorizacaoPagamentoEdicao;

    @Autowired
    private UtilitarioAmbiente utilitarioAmbiente;


    /**
     * Obtem um registro de edição a partir de um abastecimento que será editado
     * @param abastecimento o abastecimento
     * @param chamado o chamado que justifica a edição
     * @param statusEdicao status da edição.
     * @return o item do histórico
     */
    public AutorizacaoPagamentoEdicao obterAutorizacaoPgtoEdicao(AutorizacaoPagamento abastecimento, AutorizacaoChamado chamado, StatusEdicao statusEdicao) {
        AutorizacaoPagamentoEdicao abastecimentoEdicao = new AutorizacaoPagamentoEdicao(abastecimento, chamado, statusEdicao.getValue());
        abastecimentoEdicao.setUsuario(utilitarioAmbiente.getUsuarioLogado());
        abastecimentoEdicao.setDataEdicao(utilitarioAmbiente.buscarDataAmbiente());
        return abastecimentoEdicao;
    }

    /**
     * Efetua a alteração da frota.
     * @param abastecimento abastecimento que será editado.
     * @param frota A frota alterada.
     */
    public void alterarDadosAbastecimentoFrota(AutorizacaoPagamento abastecimento, Frota frota) {
        abastecimento.setFrota(frota);
        abastecimento.setFrotaExigeNF(abastecimento.isFrotaExigeNF());
        abastecimento.setCnpjFrota(frota.getCnpj());
        abastecimento.setRazaoSocialFrota(frota.getNomeRazaoFrota());
    }

    /**
     * Efetua a alteração do motorista.
     * @param abastecimento abastecimento que será editado.
     * @param motorista motorista que será editado.
     */
    public void alterarDadosAbastecimentoMotorista(AutorizacaoPagamento abastecimento, Motorista motorista) {
        abastecimento.setMotorista(motorista);
        abastecimento.setCpfMotorista(motorista.getCpf());
        abastecimento.setNomeMotorista(motorista.getNome());
        abastecimento.setAgregadoMotorista(motorista.getAgregado());

        if(motorista.getUnidade() != null){
            abastecimento.setCnpjUnidadeMotorista(motorista.getUnidade().getCnpj());
            abastecimento.setNomeUnidadeMotorista(motorista.getUnidade().getNome());
        }

        if(motorista.getGrupo() != null){
            abastecimento.setCodigoGrupoMotorista(motorista.getGrupo().getCodigoCC());
            abastecimento.setNomeGrupoMotorista(motorista.getGrupo().getNome());
        }

        if(motorista.getEmpresaAgregada() != null){
            abastecimento.setCnpjEmpresaMotorista(motorista.getEmpresaAgregada().getCnpj());
            abastecimento.setRazaoSocialEmpresaMotorista(motorista.getEmpresaAgregada().getRazaoSocial());
        }
    }

    /**
     * Efetua a alteração do veículo.
     * @param abastecimento abastecimento que será editado.
     * @param veiculo veiculo contendo as alterações a serem persistidas.
     */
    public void alterarDadosAbastecimentoVeiculo(AutorizacaoPagamento abastecimento, Veiculo veiculo) {
        abastecimento.setVeiculo(veiculo);
        abastecimento.setPlacaVeiculo(veiculo.getPlaca());
        abastecimento.setAgregadoVeiculo(veiculo.getAgregado());
        EmpresaAgregada empresaAgregada = veiculo.getEmpresaAgregada();
        abastecimento.setCnpjEmpresaVeiculo(empresaAgregada != null ? empresaAgregada.getCnpj() : null);
        abastecimento.setRazaoSocialEmpresaVeiculo(empresaAgregada != null ? empresaAgregada.getRazaoSocial() : null);
        Unidade unidade = veiculo.getUnidade();
        abastecimento.setCnpjUnidadeVeiculo(unidade != null ? unidade.getCnpj() : null);
        abastecimento.setNomeUnidadeVeiculo(unidade != null ? unidade.getNome() : null);
        GrupoOperacional grupo = veiculo.getGrupo();
        abastecimento.setCodigoGrupoVeiculo(grupo != null ? grupo.getCodigoCC() : null);
        abastecimento.setNomeGrupoVeiculo(grupo != null ? veiculo.getGrupo().getNome() : null);
        SubTipoVeiculo subtipoVeiculo = veiculo.getSubtipoVeiculo();
        abastecimento.setSubTipoVeiculo(subtipoVeiculo != null ? subtipoVeiculo.getDescricao() : null);
        abastecimento.setCotaVeiculo(null);
        abastecimento.setConsumidoVeiculo(null);
        abastecimento.setHodometro(veiculo.getHodometro());
        abastecimento.setHorimetro(veiculo.getHorimetro());
        AutorizacaoPagamento autorizacaoPagamentoAnterior = repositorioAutorizacaoPagamento.obterAutorizacaoPagamentoAnterior(abastecimento);
        if (autorizacaoPagamentoAnterior != null) {
            abastecimento.setHodometroAnterior(autorizacaoPagamentoAnterior.getHodometro());
            abastecimento.setHorimetroAnterior(autorizacaoPagamentoAnterior.getHorimetro());
        }
        abastecimento.setHodometroHorimetroAnteriorEdicao(null);
        abastecimento.setHodometroHorimetroUsuarioEdicao(null);
        abastecimento.setHodometroHorimetroDataHoraEdicao(null);
    }

    /**
     * Efetua a alteração do ponto de venda.
     * @param abastecimento abastecimento que será editado.
     * @param pv Ponto de venda alterado.
     */
    public void alterarDadosAbastecimentoPontoVenda(AutorizacaoPagamento abastecimento, PontoDeVenda pv) {
        abastecimento.setPontoVenda(pv);
        abastecimento.setCodigoCorporativoPv(pv.getCodigoCorporativo());
        abastecimento.setNumeroAbadiPv(pv.getNumeroAbadi().longValue());
        abastecimento.setCnpjAreaAbastecimento(pv.getComponenteAreaAbastecimento().getCodigoPessoa());
        abastecimento.setNomePv(pv.getNome());
        abastecimento.setEnderecoPv(pv.getComplementoEndereco());
        abastecimento.setMunicipioPV(pv.getMunicipio());
        abastecimento.setEstadoPV(pv.getUf());
        abastecimento.setLatitudePosto(pv.getLatitude());
        abastecimento.setLongitudePosto(pv.getLongitude());
        abastecimento.setBandeiraAreaAbastecimento(pv.getComponenteAreaAbastecimento().getBandeira().getDescricao());
    }

    /**
     * Efetua a alteração dos itens de abastecimento.
     * @param abastecimento abastecimento que será editado.
     * @param itensNovos itens contendo as alterações a serem persistidas.
     */
    public void alterarDadosAbastecimentoItens(AutorizacaoPagamento abastecimento, List<ItemAutorizacaoPagamento> itensNovos) {
        int quantidadeItensAdicionais = 0;
        BigDecimal valorTotal = BigDecimal.ZERO;
        for (ItemAutorizacaoPagamento item: itensNovos) {
            if (TipoItemAutorizacaoPagamento.isAbastecimento(item.getTipoItem())) {
                abastecimento.setTotalLitrosAbastecimento(item.getQuantidade());
                abastecimento.setValorUnitarioAbastecimento(item.getValorUnitario());
                abastecimento.setCodigoItemAbastecimento(item.getCodigoCombustivelOuProduto());
                abastecimento.setNomeItemAbastecimento(item.getNome());
            } else {
                quantidadeItensAdicionais++;
            }
            valorTotal = abastecimento.isPostoInterno()? null : valorTotal.add(item.getValorTotal());
            item.setAutorizacaoPagamento(abastecimento);
            item.removeCampanha();
        }
        abastecimento.setValorTotal(valorTotal);
        abastecimento.setQuantidadeItensAdicionais(quantidadeItensAdicionais);
        abastecimento.setItems(itensNovos);
    }

    /**
     * Efetua a alteração dos descontos aplicados ao abastecimento
     * @param abastecimento o abastecimento
     * @param codigoVip o codigo vip a ser editado
     */
    public void alterarDadosVip(AutorizacaoPagamento abastecimento, String codigoVip) {
        abastecimento.setCodigoVip(codigoVip);
    }

    /**
     * Obtém a última autorização pagamento edição.
     * @param autorizacaoPagamento o abastecimento atual.
     * @return AutorizacaoPagamentoEdicao edição de abastecimento concluída ou pendente caso o ciclo esteja em aberto.
     */
    public AutorizacaoPagamentoEdicao obterUltimaAutorizacaoPgtoEdicao(AutorizacaoPagamento autorizacaoPagamento) {
        return repositorioAutorizacaoPagamentoEdicao.obterUltimaAutorizacaoPgtoEdicao(autorizacaoPagamento);
    }
}
