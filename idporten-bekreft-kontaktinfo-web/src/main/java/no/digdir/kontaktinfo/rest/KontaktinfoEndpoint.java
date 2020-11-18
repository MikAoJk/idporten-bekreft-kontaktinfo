package no.digdir.kontaktinfo.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.digdir.kontaktinfo.domain.PersonResource;
import no.digdir.kontaktinfo.domain.ContactInfoResource;
import no.digdir.kontaktinfo.service.ClientService;
import no.digdir.kontaktinfo.service.KontaktinfoCache;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class KontaktinfoEndpoint {
    public static List<MediaType> ACCEPT_MEDIA_TYPES = Collections.singletonList(MediaType.APPLICATION_JSON);

    private final ClientService clientService;
    private final KontaktinfoCache kontaktinfoCache;

    @PostMapping("/kontaktinfo")
    public ResponseEntity<ContactInfoResource> updateKontaktinfo(@RequestBody ContactInfoResource updatedResource) {

        PersonResource personResource = kontaktinfoCache.getPersonResource(updatedResource.getCode());

        if (personResource == null){
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

        // TODO: feilhåndtering hvis oppdatering feiler
        clientService.updateKontaktinfo(personResource.getPersonIdentifikator(), updatedResource.getEmail(), updatedResource.getMobile());

        ContactInfoResource responseResource = prepareAndCacheResponseResource(updatedResource,personResource);
        return new ResponseEntity<ContactInfoResource>(responseResource, HttpStatus.OK);
    }

    private ContactInfoResource prepareAndCacheResponseResource(ContactInfoResource updatedResource, PersonResource personResource){
        personResource.setEmail(updatedResource.getEmail());
        personResource.setMobile(updatedResource.getMobile());

        String code = kontaktinfoCache.putPersonResource(personResource);
        personResource.setCode(code);

        return ContactInfoResource.fromPersonResource(personResource);
    }

}