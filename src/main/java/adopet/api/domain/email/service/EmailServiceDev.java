package adopet.api.domain.email.service;

import adopet.api.domain.email.EmailService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("default")
public class EmailServiceDev implements EmailService {
    public void sendEmail(String to, String subject, String message) {
        System.out.println("Enviando email fake");
        System.out.println("Destinatario: " +to);
        System.out.println("Assunto: " +subject);
        System.out.println("Mensagem: " +message);
    }
}
