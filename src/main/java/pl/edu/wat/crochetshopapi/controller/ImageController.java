package pl.edu.wat.crochetshopapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.wat.crochetshopapi.service.ImageService;

import java.io.IOException;

@RestController
public class ImageController {
    @Autowired
    private ImageService imageService;

    @GetMapping(value = "/image")
    public ResponseEntity<?> getImage(@RequestParam("imageId") long imageId) {
        return new ResponseEntity<>(imageService.get(imageId), HttpStatusCode.valueOf(200));
    }
    @GetMapping(value = "/images")
    public ResponseEntity<?> getAllImages() {
        return new ResponseEntity<>(imageService.getAll(), HttpStatusCode.valueOf(200));
    }

    @PostMapping(value = "/image")
    public ResponseEntity<?> addImage(@RequestParam("imageName") String name,
                                      @RequestParam("file") MultipartFile file) throws IOException {
        imageService.addImage(name, file);
        return new ResponseEntity<>("Sent successfully", HttpStatusCode.valueOf(200));
    }

    @DeleteMapping(value = "/image")
    public ResponseEntity<?> delImage(@RequestParam("imageId") long imageId) {
        imageService.deleteImage(imageId);
        return new ResponseEntity<>("Deleted successfully", HttpStatusCode.valueOf(200));
    }

    @PutMapping(value = "/image")
    public ResponseEntity<?> changeImageName(@RequestParam("imageId") long imageId,
                                             @RequestParam("imageName") String name) {
        imageService.changeImageName(imageId, name);
        return new ResponseEntity<>("Name has been changed successfully", HttpStatusCode.valueOf(200));
    }
}
