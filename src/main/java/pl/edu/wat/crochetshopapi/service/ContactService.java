package pl.edu.wat.crochetshopapi.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import pl.edu.wat.crochetshopapi.service.GmailAPIService;

import javax.mail.MessagingException;
import java.io.IOException;

@Service
public class ContactService {

    private final GmailAPIService gmailAPIService;

    public ContactService(GmailAPIService gmailAPIService) {
        this.gmailAPIService = gmailAPIService;
    }

    public void submitContactRequest(
            String subject,
            String description) {

        try {

            gmailAPIService.sendMessage(
                    subject,
                    description
            );

        } catch (MessagingException | IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Not able to process request.");
        }

    }

}
