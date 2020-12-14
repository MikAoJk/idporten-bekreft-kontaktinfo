package no.digdir.kontaktinfo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "oidc-sdk", ignoreUnknownFields = false)
public class OpenIDConnectConfigProvider {
    private int parLifetimeSeconds;
    private int authorizationLifetimeSeconds;
    private String issuer;
    private String redirectUri;
    private String clientId;
    private String secret;
    private String scope;
    private String internalId;
    private String acr;
    private String locale;

}
