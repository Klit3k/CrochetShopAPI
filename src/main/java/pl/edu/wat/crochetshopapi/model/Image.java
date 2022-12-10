package pl.edu.wat.crochetshopapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.springframework.web.multipart.MultipartFile;

@Entity
public class Image {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private byte[] file;
}
