package no.digdir.krr.bekreft.kontaktinfo.rest;

import lombok.extern.slf4j.Slf4j;
import no.digdir.krr.bekreft.kontaktinfo.controller.ContactInfoController;
import no.digdir.krr.bekreft.kontaktinfo.domain.PARRequest;
import no.digdir.krr.bekreft.kontaktinfo.domain.PARResponse;
import no.digdir.krr.bekreft.kontaktinfo.domain.PersonResource;
import no.digdir.krr.bekreft.kontaktinfo.service.KontaktinfoCache;
import no.digdir.krr.bekreft.kontaktinfo.service.PARService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api")
public class PAREndpoint {
    private final ContactInfoController contactInfoController;
    private final PARService parService;
    private final KontaktinfoCache kontaktinfoCache;

    @Value("${par.cache.ttl-in-s:120}")
    private int ttl;

    public PAREndpoint(PARService parService, KontaktinfoCache kontaktinfoCache, ContactInfoController contactInfoController) {
        this.contactInfoController = contactInfoController;
        this.parService = parService;
        this.kontaktinfoCache = kontaktinfoCache;
    }

    @PostMapping("/par")
    @ResponseBody
    public ResponseEntity<PARResponse> par(@RequestBody PARRequest parRequest) {
        log.warn("Behandler par");
        String requestUri = parService.generateRequestUri();
        kontaktinfoCache.putParRequest(requestUri, parRequest);
        kontaktinfoCache.putPid(requestUri, parRequest.getPid());
        PARResponse response = new PARResponse(requestUri, ttl);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/authorize")
    public Object authorize(@RequestParam String requestUri) {
        log.warn("Behandler authorize med requestUri={}", requestUri);
        PARRequest parRequest = kontaktinfoCache.getParRequest(requestUri);
        return contactInfoController.confirm(kontaktinfoCache.getPid(requestUri), parRequest.getGotoParam(), parRequest.getLocale());
    }

    @PostMapping("/token")
    public Object token(@RequestBody String code) {
        log.warn("Behandler token med code={}", code);
        PersonResource kontaktinfo = kontaktinfoCache.getPersonResource(code);
        String jwt = parService.makeJwt(kontaktinfo);
        kontaktinfoCache.removePersonResource(code);
        return jwt;
    }
}
