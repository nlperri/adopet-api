package adopet.api.domain.email;

public interface EmailService {

    void sendEmail(
            String to,
            String subject,
            String message
    );
}
