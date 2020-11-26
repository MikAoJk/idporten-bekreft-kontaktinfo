package no.digdir.krr.bekreft.kontaktinfo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

import static lombok.AccessLevel.PACKAGE;

@Getter
@Builder(access = PACKAGE)
public class PARRequest implements Serializable {

    @JsonProperty(value = "response_type")
    String responseType;

    @JsonProperty(value = "response_mode")
    String responseMode;

    @JsonProperty(value = "pid")
    String pid;

    @JsonProperty(value = "redirect_uri")
    String redirectUri;

    @JsonProperty(value = "client_id")
    String clientId;

    @JsonProperty(value = "state")
    String state;

    //egne openam-felt
    @JsonProperty(value = "goto")
    String gotoParam;

    @JsonProperty(value = "locale")
    String locale;

    @JsonProperty(value = "service")
    String service;
}
