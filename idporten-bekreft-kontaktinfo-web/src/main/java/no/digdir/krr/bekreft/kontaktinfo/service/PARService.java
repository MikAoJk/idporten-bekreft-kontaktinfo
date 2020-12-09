package no.digdir.krr.bekreft.kontaktinfo.service;

import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.digdir.krr.bekreft.kontaktinfo.config.JwtConfigProvider;
import no.digdir.krr.bekreft.kontaktinfo.crypto.JwtSigningService;
import no.digdir.krr.bekreft.kontaktinfo.domain.PersonResource;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PARService {
    public static final String PAR_REQUEST_URI_PREFIX = "urn:kontaktinfo:";
    private static final String DIGITALCONTACTREGISTER_PID = "digitalcontactregister-pid";
    private static final String DIGITALCONTACTREGISTER_RESERVED = "digitalcontactregister-reserved";
    private static final String DIGITALCONTACTREGISTER_POSTBOXOPERATOR = "digitalcontactregister-postboxoperator";
    private static final String DIGITALCONTACTREGISTER_EMAIL = "digitalcontactregister-email";
    private static final String DIGITALCONTACTREGISTER_MOBILE = "digitalcontactregister-mobile";
    private static final String DIGITALCONTACTREGISTER_STATUS = "digitalcontactregister-status";

    private final JwtConfigProvider jwtConfigProvider;
    private final JwtSigningService jwtSigningService;

    public boolean isValidRequestUri(String uri) {
        return uri != null && uri.matches("^" + PAR_REQUEST_URI_PREFIX + ".+$");
    }

    public String generateRequestUri() {
        return PAR_REQUEST_URI_PREFIX + IdGenerator.generateId(32);
    }

    public String makeJwt(PersonResource personResource) {
        String postboxOperator = personResource.getDigitalPost() == null
                ? null
                : personResource.getDigitalPost().getPostkasseleverandoernavn();

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                // nonce
                .audience(jwtConfigProvider.getAud())
                .issuer(jwtConfigProvider.getIss())
                .claim(DIGITALCONTACTREGISTER_PID, personResource.getPersonIdentifikator())
                .claim(DIGITALCONTACTREGISTER_EMAIL, personResource.getEmail())
                .claim(DIGITALCONTACTREGISTER_MOBILE, personResource.getMobile())
                .claim(DIGITALCONTACTREGISTER_RESERVED, personResource.getReserved())
                .claim(DIGITALCONTACTREGISTER_POSTBOXOPERATOR, postboxOperator)
                .claim(DIGITALCONTACTREGISTER_STATUS, personResource.getStatus())
                .jwtID(UUID.randomUUID().toString())
                .issueTime(new Date(Clock.systemUTC().millis()))
                .expirationTime(new Date(Clock.systemUTC().millis() + 120000)) // Expiration time is 120 sec.
                .build();

        return jwtSigningService.signJwt(claims);
    }
}
