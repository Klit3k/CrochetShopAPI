package pl.edu.wat.crochetshopapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.wat.crochetshopapi.Configuration;
import pl.edu.wat.crochetshopapi.exception.ImageAlreadyExistsException;
import pl.edu.wat.crochetshopapi.exception.ImageNotFoundException;
import pl.edu.wat.crochetshopapi.exception.InvalidTypeOfFileException;
import pl.edu.wat.crochetshopapi.model.Image;
import pl.edu.wat.crochetshopapi.repository.ImageRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    public Image get(long imageId) {
        return imageRepository.findById(imageId)
                .orElseThrow(() -> new ImageNotFoundException("Image not found."));
    }

    public void addImage(String name, MultipartFile file) throws IOException {

        if (imageRepository.findByName(name).isPresent())
            throw new ImageAlreadyExistsException("Image with this name already exits.");

        File convertFile = new File(Configuration.IMAGES_PATH + file.getOriginalFilename());

        if (file.getContentType() == null || !file.getContentType().equals("image/png"))
            throw new InvalidTypeOfFileException("Invalid type of file.");

        FileOutputStream fout = new FileOutputStream(convertFile);
        fout.write(file.getBytes());
        imageRepository.save(
                Image.builder()
                        .name(name)
                        .file(file.getBytes())
                        .build()
        );
        fout.close();
    }

    public void deleteImage(long imageId) {
        imageRepository.delete(get(imageId));
    }

    public void changeImageName(long imageId, String name) {
        Image image = get(imageId);
        image.setName(name);
        imageRepository.save(image);
    }

    public List<Image> getAll() {
        List<Image> imageList = new ArrayList<>();
        imageRepository.findAll().forEach(imageList::add);
        return imageList;
    }
}
