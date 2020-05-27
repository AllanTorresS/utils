package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.vo.CredenciaisIntegradorVo;
import ipp.aci.boleia.dominio.vo.PontoVendaIntegradorVo;
import ipp.aci.boleia.dominio.vo.RespostaAbastecimentoIntegradorVo;

import java.util.Date;

/**
 * Contrato para implementacao de repositorios
 * do Integrador
 */
public interface IIntegradorDados {

    /**
     * Dispara uma requisição para o integrador para obter um token jwt e um refresh token.
     * @return as credenciais de acesso
     */
    CredenciaisIntegradorVo obterCredenciaisAcesso();

    /**
     * Dispara uma requisição para o integrador para obter dados sobre um ponto de venda.
     * @param tokenJWT o token jwt para autenticação
     * @param refreshToken o refresh token para o caso de jwt expirado
     * @param codigoPv o código corporativo do ponto de venda
     * @return os dados sobre o ponto de venda
     */
    PontoVendaIntegradorVo obterDadosPontoVenda(String tokenJWT, String refreshToken, Long codigoPv);

    /**
     * Obtém os abastecimentos de um ponto de venda em um bico no integrador.
     * @param tokenJWT o token jwt para autenticação
     * @param refreshToken o refresh token para o caso do jwt expirado
     * @param codigoPontoVendaCorporativo o código corporativo do ponto de venda
     * @param codigoBico o bico que foi realizado o abastecimento
     * @param dataAbastecimentoDesde Filtra pela data do abastecimento a partir da data informada
     * @param offset em caso de uso de paginação
     * @return os abastecimentos realizados com os dados fornecidos
     */
    RespostaAbastecimentoIntegradorVo obterAbastecimentos(String tokenJWT, String refreshToken, Long codigoPontoVendaCorporativo, String codigoBico, Date dataAbastecimentoDesde, Integer offset);
}
