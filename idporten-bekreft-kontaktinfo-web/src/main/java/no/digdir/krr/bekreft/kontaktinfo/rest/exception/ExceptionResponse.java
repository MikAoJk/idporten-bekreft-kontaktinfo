package no.digdir.krr.bekreft.kontaktinfo.rest.exception;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
    private String errorMessage;
    private String statusCode;
}
