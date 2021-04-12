package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.INfeAnexosArmazemDados;
import ipp.aci.boleia.dominio.NfeAnexosArmazem;
import ipp.aci.boleia.dominio.NfeArmazem;
import ipp.aci.boleia.dominio.vo.DanfeVo;
import ipp.aci.boleia.dominio.vo.ItemDanfeVo;
import ipp.aci.boleia.util.ConstantesNotaFiscal;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.UtilitarioFormatacaoData;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.i18n.Mensagens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * Implementa regras de negócio relativas ao armazém de anexos (notas fiscais) da recolha automática
 */
@Component
public class NfeAnexosArmazemSd {

    @Autowired
    private INfeAnexosArmazemDados repositorio;

    @Autowired
    private NotaFiscalSd notaFiscalSd;

    @Autowired
    private Mensagens mensagens;

    private static final Integer ZERO = 0;

    /**
     * Preenche os dados para persistência do anexo no armazém
     *
     * @param dadoArmazem O registro do armazém que contém as informações do e-mail que originou o anexo
     * @param nota Objeto que representa a DANFe
     * @param statusAnexo O status do anexo a ser persistido
     * @param justificativaFalha Motivo pelo qual o processamento do anexo falhou
     * @return Retorna o anexo persistido no armazém
     */
    public NfeAnexosArmazem preencheDadosAnexo(NfeArmazem dadoArmazem, DanfeVo nota, Integer statusAnexo, String justificativaFalha) {
        NfeAnexosArmazem anexoArmazem = new NfeAnexosArmazem();

        if (nota.getDestCnpjCpf() != null) {
            anexoArmazem.setCnpjFrota(Long.parseLong(UtilitarioFormatacao.obterNumeracaoSemFormatacao(nota.getDestCnpjCpf())));
        }
        if (nota.getEmitCnpj() != null) {
            anexoArmazem.setCnpjPtov(Long.parseLong(UtilitarioFormatacao.obterNumeracaoSemFormatacao(nota.getEmitCnpj())));
        }
        if (nota.getValorTotalNota() != null) {
            anexoArmazem.setValorTotalNota(nota.getValorTotalNota());
        }
        if (nota.getDhEmiDataEmissao() != null) {
            anexoArmazem.setDataEmissao(UtilitarioFormatacaoData.lerDataHoraMinutosSegundos(nota.getDhEmiDataEmissao()));
        }
        if (nota.getSerieNota() != null) {
            anexoArmazem.setSerieNota(nota.getSerieNota());
        }
        if (nota.getNumeroDaNota() != null) {
            anexoArmazem.setNumeroNota(ConstantesNotaFiscal.NOTA_FISCAL_PREFIX + nota.getNumeroDaNota());
        }
        if (nota.getChaveAcesso() != null) {
            String chaveAcesso = nota.getChaveAcesso().replaceAll(" ","");
            anexoArmazem.setChaveAcesso(chaveAcesso.length() > ConstantesNotaFiscal.TAMANHO_CHAVE_ACESSO
                    ? chaveAcesso.substring(0, ConstantesNotaFiscal.TAMANHO_CHAVE_ACESSO)
                    : chaveAcesso);
        }
        if(nota.getDadosDanfe() != null && !nota.getDadosDanfe().isEmpty()) {
            List<ItemDanfeVo> itensNota = nota.getDadosDanfe();
            BigDecimal valorCombustivel = notaFiscalSd.obterValorTotalCombustivelNota(itensNota);
            BigDecimal valorProdServ = notaFiscalSd.obterValorTotalProdutosNota(itensNota);
            anexoArmazem.setValorCombustivel(valorCombustivel.compareTo(BigDecimal.ZERO) > 0 ? valorCombustivel : null);
            anexoArmazem.setValorProdutosServicos(valorProdServ.compareTo(BigDecimal.ZERO) > 0 ? valorProdServ : null);
        }

        anexoArmazem.setEmail(dadoArmazem);
        anexoArmazem.setStatus(statusAnexo);
        if (justificativaFalha != null) {
            anexoArmazem.setMotivoFalhaImportacao(justificativaFalha);
        }
        anexoArmazem.setNumeroTentativasAtualizacao(ZERO);
        return repositorio.armazenar(anexoArmazem);
    }

    /**
     * Obtém a mensagem correspondente ao erro de validação de nota fiscal
     *
     * @param erro Erro de validação encontrado
     * @return A mensagem correspondente ao erro
     */
    public String obterMotivoFalhaProcessamentoOuImportacao(Erro erro) {
        return mensagens.obterMensagem(erro.getChaveMensagem());
    }
}
