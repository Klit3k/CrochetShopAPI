package pl.edu.wat.crochetshopapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.crochetshopapi.service.PromoCodeService;

import java.time.LocalDateTime;

@RestController
public class PromoCodeController {
    @Autowired
    PromoCodeService promoCodeService;

    @PostMapping(value = "/promo")
    public ResponseEntity<?> applyCode(@RequestParam("code") String code) {
        if (promoCodeService.apply(code))
            return new ResponseEntity<>("Code submitted", HttpStatusCode.valueOf(200));
        else
            return new ResponseEntity<>("Invalid code", HttpStatusCode.valueOf(403));
    }
    @PostMapping(value = "/promo-check")
    public ResponseEntity<?> checkCode(@RequestParam("code") String code) {
            return new ResponseEntity<>(promoCodeService.check(code), HttpStatusCode.valueOf(200));
    }
    @PostMapping(value = "/promo-generate")
    public ResponseEntity<?> generateCode(@RequestParam("validFrom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime validFrom,
                                          @RequestParam("validTo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime validTo) {
        return new ResponseEntity<>(promoCodeService.generate(validFrom, validTo), HttpStatusCode.valueOf(200));
    }
    @PostMapping(value = "/promo-add")
    public ResponseEntity<?> generateCode(@RequestParam("validFrom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime validFrom,
                                          @RequestParam("validTo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime validTo,
                                          @RequestParam("code") String code) {
        return new ResponseEntity<>(promoCodeService.add(validFrom, validTo, code), HttpStatusCode.valueOf(200));
    }
    @GetMapping(value = "/promos")
    public ResponseEntity<?> getAllCodes() {
        return new ResponseEntity<>(promoCodeService.getAll(), HttpStatusCode.valueOf(200));
    }

    @DeleteMapping(value = "/promo")
    public ResponseEntity<?> applyCode(@RequestParam("codeId") long codeId) {
        promoCodeService.delete(codeId);
        return new ResponseEntity<>("Code deleted.", HttpStatusCode.valueOf(200));
    }
}