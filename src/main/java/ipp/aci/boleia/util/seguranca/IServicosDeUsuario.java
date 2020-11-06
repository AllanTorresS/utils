package ipp.aci.boleia.util.seguranca;

import ipp.aci.boleia.dominio.Usuario;

public interface IServicosDeUsuario {

    Usuario obterPorCpfComPermissoes(String cpf, Boolean usuarioMotorista);
    Usuario obterPorLoginComPermissoes(String login);
    Usuario obterPorEmailComPermissoes(String email);
    boolean possuiBloqueioTemporario(Long idUsuario);
    Boolean registrarSucessoAutenticacao(Long idUsuario);
    boolean registrarErroAutenticacao(Long idUsuario);
    Usuario obterPorIdComPermissoes(long id);
    Usuario povoarPermissoesUsuario(Usuario usuario);
    boolean credenciaisValidas(Usuario usuario, String senha);
    Usuario obterPorIdSemIsolamentoComPermissoes(long id);
}
