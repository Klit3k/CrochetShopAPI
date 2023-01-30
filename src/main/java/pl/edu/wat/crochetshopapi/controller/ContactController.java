package pl.edu.wat.crochetshopapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.wat.crochetshopapi.service.ContactService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/contact")
public class ContactController {

    private final ContactService contactService;

    @PostMapping(path = "/request")
    public void submitContactRequest(
            @RequestParam("subject") String subject,
            @RequestParam("description") String description) {
        contactService.submitContactRequest(subject, description);
    }

}
