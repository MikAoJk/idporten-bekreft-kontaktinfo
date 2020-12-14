package no.digdir.krr.bekreft.kontaktinfo.audit;

import no.idporten.log.audit.AuditLogger;
import no.idporten.log.audit.AuditLoggerELFImpl;
import no.idporten.log.elf.ELFWriter;
import no.idporten.log.elf.FileRollerDailyImpl;
import no.idporten.log.elf.WriterCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AuditLoggerProvider {

    /*
    TODO: dette burde fungere men får feil
    @Value("${application.logging.audit.dir}")
    private String auditLogDir;

    @Value("${application.logging.audit.file}")
    private String auditLogFile;
     */

    private String auditLogDir = "/var/log/idporten-bekreft-kontaktinfo/audit/";
    private String auditLogFile = "audit.log";

    @Bean
    public AuditLogger auditLogger() {
        ELFWriter elfWriter = new ELFWriter(
                new FileRollerDailyImpl(auditLogDir, auditLogFile),
                new WriterCreator()
        );
        AuditLoggerELFImpl logger = new AuditLoggerELFImpl();
        logger.setELFWriter(elfWriter);
        logger.setDataSeparator("|");
        return logger;
    }
}