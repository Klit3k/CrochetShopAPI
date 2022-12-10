package pl.edu.wat.crochetshopapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.crochetshopapi.exception.VariantNotFound;
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
                .orElseThrow(() -> new VariantNotFound("Variant doesn't exists."));
    }

    public Variant addToVariant(long variantId, long productId) {
        Variant variant = get(variantId);
        if (variant.getVariants().size() == 0)
            variant.setVariants(new ArrayList<>());
        variant.getVariants().add(productService.get(productId));
        return variantRepository.save(variant);
    }

    public void delFromVariant(long variantId, long productId) {
        Variant variant = get(variantId);
        if(variant.getVariants().isEmpty())
            variantRepository.delete(variant);
        else {
            variant.getVariants()
                    .remove(productService.get(productId));
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
}
