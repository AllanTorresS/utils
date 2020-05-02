package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.ITokenDados;
import ipp.aci.boleia.dominio.enums.TipoToken;
import ipp.aci.boleia.dominio.vo.TokenVo;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import ipp.aci.boleia.util.seguranca.UtilitarioCriptografia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Implementa a comunicacao com a integração de autorização para obtençao de Token
 */
@Repository
public class OracleAutorizacaoTokenDados implements ITokenDados {

    @PersistenceContext
    private EntityManager gerenciadorDeEntidade;

    @Autowired
    private UtilitarioAmbiente utilitarioAmbiente;

    @Override
    public TokenVo novoToken(TipoToken tipoToken) {
        TokenVo token = new TokenVo();

        token.setToken(tipoToken.getAlfanumerico() ?
                UtilitarioCriptografia.gerarTokenAlfanumerico(tipoToken.getTamanho()) : UtilitarioCriptografia.gerarTokenNumerico(tipoToken.getTamanho()));
        if(tipoToken.getMinutosExpiracao()!=null) {
            token.setDataExpiracaoToken(UtilitarioCalculoData.adicionarMinutosData(utilitarioAmbiente.buscarDataAmbiente(), tipoToken.getMinutosExpiracao()));
        }

        if(tipoToken.getUnico()){
            while(!verificarUnicidadeToken(token.getToken(),tipoToken)) {
                token.setToken(tipoToken.getAlfanumerico() ?
                        UtilitarioCriptografia.gerarTokenAlfanumerico(tipoToken.getTamanho()) : UtilitarioCriptografia.gerarTokenNumerico(tipoToken.getTamanho()));
            }
        }

        return token;
    }

    /**
     * Verifica se o token gerado randomicamente é unico nos registros da entidade
     * @param token Token randomico gerado
     * @param tipo Tipo do token
     * @return True para token unico
     */
    private Boolean verificarUnicidadeToken(String token, TipoToken tipo) {
        CriteriaBuilder builder = gerenciadorDeEntidade.getCriteriaBuilder();
        CriteriaQuery criteria = builder.createQuery(tipo.getClasseEntidade());
        Root entityRoot = criteria.from(tipo.getClasseEntidade());
        criteria.select(entityRoot);
        criteria.where(builder.equal(entityRoot.get(tipo.getCampoClasse()), token));
        TypedQuery query = gerenciadorDeEntidade.createQuery(criteria);

        return query.getResultList().isEmpty();
    }
}
