package ipp.aci.boleia.util.seguranca;

public interface IServicosDeReabilitacaoSistemaExterno {
    boolean iniciarProcedimentoReabilitacao(String tokenJwtExpirado, String fingerprintRequisicao);
}
