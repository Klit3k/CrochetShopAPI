package pl.edu.wat.crochetshopapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.crochetshopapi.exception.ProductAlreadyInVariantException;
import pl.edu.wat.crochetshopapi.exception.VariantAlreadyExistsException;
import pl.edu.wat.crochetshopapi.exception.VariantNotFoundException;
import pl.edu.wat.crochetshopapi.model.Product;
import pl.edu.wat.crochetshopapi.model.Variant;
import pl.edu.wat.crochetshopapi.repository.VariantRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class VariantService {
    @Autowired
    ProductService productService;
    @Autowired
    VariantRepository variantRepository;

    public Variant get(long variantId) {
        return variantRepository.findById(variantId)
                .orElseThrow(() -> new VariantNotFoundException("Variant doesn't exists."));
    }

    public Variant create(String name) {
        if(variantRepository.findByName(name).isPresent())
            throw new VariantAlreadyExistsException("Variant is already existing.");
        return variantRepository.save(Variant.builder()
                .name(name)
                .build());
    }

    public Variant addToVariant(long variantId, long productId) {
        Variant variant = get(variantId);
        Product product = productService.get(productId);
        if(variant.getVariants().contains(product))
            throw new ProductAlreadyInVariantException("Product is already in this variant.");
        if (variant.getVariants().size() == 0)
            variant.setVariants(new ArrayList<>());
        variant.getVariants().add(product);
        return variantRepository.save(variant);
    }
    public void deleteVariant(long variantId) {
        variantRepository.delete(get(variantId));
    }
    public void delFromVariant(long variantId, long productId) {
        Variant variant = get(variantId);
        if (variant.getVariants().isEmpty()) {
            variantRepository.delete(variant);
        }
        else {
            variant.getVariants()
                    .remove(productService.get(productId));
            variantRepository.save(variant);
        }
    }

    public List<Product> getProducts(long variantId) {
        return get(variantId).getVariants();
    }

    public List<Variant> getVariants() {
        List<Variant> variants = new ArrayList<>();
        variantRepository.findAll().forEach(variants::add);
        return variants;
    }

    public Variant modify(long variantId, Variant editedVariant) {
        editedVariant.setId(get(variantId).getId());
        return variantRepository.save(editedVariant);
    }

}
