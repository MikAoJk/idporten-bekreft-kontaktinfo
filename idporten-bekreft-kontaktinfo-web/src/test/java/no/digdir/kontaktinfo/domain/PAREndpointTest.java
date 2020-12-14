package no.digdir.kontaktinfo.domain;

import no.digdir.kontaktinfo.config.JwtConfigProvider;
import no.digdir.kontaktinfo.config.OpenIDConnectConfigProvider;
import no.digdir.kontaktinfo.controller.ContactInfoController;
import no.digdir.kontaktinfo.crypto.KeyProvider;
import no.digdir.kontaktinfo.crypto.KeyStoreProvider;
import no.digdir.kontaktinfo.rest.PAREndpoint;
import no.digdir.kontaktinfo.service.KontaktinfoCache;
import no.idporten.sdk.oidcserver.OpenIDConnectIntegration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PAREndpoint.class)
public class PAREndpointTest {

    public static final String REQUEST_URI = "urn:kontaktinfo:4bD7kHmUBEl4bILyGNbSEE8r6DE_9M4NYE8mkhnIBQM";
    private static final String GOTO_URL = "http://example.com";
    private static final String LOCALE = "nb";
    private static final String PID = "007";
    @MockBean
    ContactInfoController contactInfoController;
    @MockBean
    KontaktinfoCache kontaktinfoCache;
    @MockBean
    OpenIDConnectIntegration openIDConnectSdk;
    @MockBean
    OpenIDConnectConfigProvider openIDConnectConfigProvider;
    @MockBean
    JwtConfigProvider jwtConfigProvider;
    @MockBean
    KeyStoreProvider keyStoreProvider;
    @MockBean
    KeyProvider keyProvider;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void feilUrlFinnesIkke() throws Exception {
        mockMvc.perform(get("/api/monkey"))
                .andExpect(status().isNotFound());
    }

}