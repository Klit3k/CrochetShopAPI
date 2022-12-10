package pl.edu.wat.crochetshopapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.crochetshopapi.service.VariantService;

@RestController
public class VariantController {
    @Autowired
    private VariantService variantService;

    @GetMapping(value = "/variant")
    public ResponseEntity<?> getVariant(@RequestParam("variantId") long variantId) {
        return new ResponseEntity<>(variantService.get(variantId), HttpStatusCode.valueOf(200));
    }
    @GetMapping(value = "/variants")
    public ResponseEntity<?> getVariants() {
        return new ResponseEntity<>(variantService.getVariants(), HttpStatusCode.valueOf(200));
    }
    @GetMapping(value = "/variant-products")
    public ResponseEntity<?> getProducts(@RequestParam("variantId") long variantId) {
        return new ResponseEntity<>(variantService.getProducts(variantId), HttpStatusCode.valueOf(200));
    }
    @PostMapping(value = "/variant-create")
    public ResponseEntity<?> createVariant(@RequestParam("variantName") String name) {
        return new ResponseEntity<>(variantService.create(name), HttpStatusCode.valueOf(200));
    }
    @PostMapping(value = "/variant")
    public ResponseEntity<?> addToVariant(@RequestParam("variantId") long variantId,
                                          @RequestParam("productId") long productId) {
        return new ResponseEntity<>(variantService.addToVariant(variantId, productId), HttpStatusCode.valueOf(200));
    }
    @DeleteMapping(value = "/variant-product")
    public ResponseEntity<?> delFromVariant(@RequestParam("variantId") long variantId,
                                          @RequestParam("productId") long productId) {
        variantService.delFromVariant(variantId, productId);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }
    @DeleteMapping(value = "/variant")
    public ResponseEntity<?> deleteVariant(@RequestParam("productId") long productId) {
        variantService.deleteVariant(productId);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }
}
