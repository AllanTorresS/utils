package ipp.aci.boleia.util.seguranca;

import com.auth0.jwt.interfaces.DecodedJWT;

public interface IServicosDeApiToken {
    String renovarTokenFrota(DecodedJWT token, String strToken);
}
