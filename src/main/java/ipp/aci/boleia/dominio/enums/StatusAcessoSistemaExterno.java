package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.excecao.Erro;

/**
 * Enumera os possiveis status de acesso de um sistema externo
 */
public enum StatusAcessoSistemaExterno {

    SUCESSO                                 (1),
    COMANDA_EXCLUIDA                        (4012),
    COMANDA_NAO_HABILITADA                  (4013),
    COMANDA_BLOQUEADA                       (4014),
    USUARIO_INVALIDO                        (Erro.AUTENTICACAO_USUARIO_INVALIDO.getCodigo()),
    TIPO_TOKEN_JWT_INVALIDO                 (Erro.TOKEN_JWT_INVALIDO.getCodigo()),
    DISPOSITIVO_MOTORISTA_STATUS_INVALIDO   (Erro.DISPOSITIVO_MOTORISTA_STATUS_INVALIDO.getCodigo()),
    DISPOSITIVO_MOTORISTA_EXCLUIDO          (Erro.DISPOSITIVO_MOTORISTA_EXCLUIDO.getCodigo()),
    DISPOSITIVO_MOTORISTA_BLOQUEADO         (Erro.DISPOSITIVO_MOTORISTA_BLOQUEADO.getCodigo()),
    DISPOSITIVO_MOTORISTA_NAO_HABILITADO    (Erro.DISPOSITIVO_MOTORISTA_NAO_HABILITADO.getCodigo()),
    PERMISSAO_NEGADA                        (Erro.PERMISSAO_NEGADA.getCodigo()),
    API_EXTERNA_PERMISSAO_NEGADA            (Erro.API_PERMISSAO_NEGADA.getCodigo()),
    TOKEN_JWT_EXPIRADO                      (Erro.TOKEN_JWT_EXPIRADO.getCodigo());

    private final int codigo;

    StatusAcessoSistemaExterno(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }
}
