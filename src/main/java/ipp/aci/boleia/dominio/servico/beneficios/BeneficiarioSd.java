package ipp.aci.boleia.dominio.servico.beneficios;

import ipp.aci.boleia.dados.IBeneficiarioDados;
import ipp.aci.boleia.dados.IFrotaDados;
import ipp.aci.boleia.dominio.Motorista;
import ipp.aci.boleia.dominio.beneficios.Beneficiario;
import ipp.aci.boleia.dominio.beneficios.ContaBeneficiario;
import ipp.aci.boleia.dominio.enums.OrigemBeneficiario;
import ipp.aci.boleia.dominio.enums.StatusAtivacao;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * SD da entidade Beneficiario.
 *
 * @author pedro.silva
 */
@Component
public class BeneficiarioSd {

    @Autowired
    private UtilitarioAmbiente ambiente;

    @Autowired
    private ContaBeneficiarioSd contaBeneficiarioSd;

    @Autowired
    private IBeneficiarioDados repositorio;

    @Autowired
    private IFrotaDados repositorioFrota;

    /**
     * Cria um novo {@link Beneficiario} para um {@link Motorista}.
     *
     * @param motorista Motorista usado na criação.
     * @return Beneficiário criado.
     */
    public Beneficiario criarBeneficiario(Motorista motorista) {
        Beneficiario beneficiario = instanciarBeneficiario(motorista);
        beneficiario = repositorio.armazenar(beneficiario);
        beneficiario.setContaBeneficiario(contaBeneficiarioSd.criarContaBeneficiario(beneficiario.getId()));
        return beneficiario;
    }

    /**
     * Atualiza as informações de um {@link Beneficiario} para um {@link Motorista}.
     * @param motorista Motorista usado na atualização.
     * @return Beneficiário atualizado.
     */
    public Beneficiario atualizarBeneficiario(Motorista motorista) {
        Beneficiario beneficiario = repositorio.obterBeneficiariosPorCpfFrota(motorista.getCpf(), motorista.getFrota().getId());
        if (beneficiario != null) {
            beneficiario.setNome(motorista.getNome());
            beneficiario.setStatus(motorista.getStatus());
            beneficiario.setDataAtualizacao(ambiente.buscarDataAmbiente());
            return repositorio.armazenarSemIsolamentoDeDados(beneficiario);
        }
        return null;
    }

    /**
     * Exclui um {@link Beneficiario}.
     *
     * @param idFrota Identificador da frota do beneficiário.
     * @param cpf CPF do beneficiário.
     */
    public void excluirBeneficiario(Long idFrota, Long cpf) {
        Beneficiario beneficiario = repositorio.obterBeneficiariosPorCpfFrota(cpf, idFrota);
        if(beneficiario != null) {
            repositorio.excluir(beneficiario.getId());
            if(beneficiario.getContaBeneficiario() != null) {
                beneficiario.getContaBeneficiario().setDataEncerramento(ambiente.buscarDataAmbiente());
            }
        }
    }

    /**
     * Cria uma nova instância de {@link Beneficiario} a partir de um {@link Motorista} da solução.
     *
     * @param motorista Motorista utilizado.
     * @return Instância criada.
     */
    private Beneficiario instanciarBeneficiario(Motorista motorista) {
        Beneficiario beneficiario = new Beneficiario();
        beneficiario.setFrota(repositorioFrota.pesquisarPorCnpj(motorista.getFrota().getCnpj()));
        beneficiario.setNome(motorista.getNome());
        beneficiario.setCpf(motorista.getCpf());
        beneficiario.setStatus(motorista.getStatus());
        beneficiario.setOrigem(OrigemBeneficiario.MOTORISTA.getValue());
        beneficiario.setDataCriacao(ambiente.buscarDataAmbiente());
        beneficiario.setDataAtualizacao(ambiente.buscarDataAmbiente());
        beneficiario.setVersao(motorista.getVersao());
        return beneficiario;
    }
}
