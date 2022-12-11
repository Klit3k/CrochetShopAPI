package pl.edu.wat.crochetshopapi.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.crochetshopapi.model.PromoCode;
import pl.edu.wat.crochetshopapi.exception.PromoCodeNotFoundException;
import pl.edu.wat.crochetshopapi.repository.PromoCodeRepository;

import java.time.LocalDateTime;

@Service
public class PromoCodeService {
    @Autowired
    private PromoCodeRepository promoCodeRepository;
    public PromoCode generateCode(LocalDateTime validFrom, LocalDateTime validTo) {
        return promoCodeRepository.save(PromoCode.builder()
                .code(RandomStringUtils.random(8, true, true))
                .validFrom(validFrom)
                .validTo(validTo)
                .build());
    }

    public void delete(long codeId){
        promoCodeRepository.delete(get(codeId));
    }

    public boolean check(String code){
        if(promoCodeRepository.findByCode(code).isEmpty()) return false;
        PromoCode promoCode = promoCodeRepository.findByCode(code).get();
        return promoCode.getCode().equals(code)
                && promoCode.getValidTo().isBefore(LocalDateTime.now())
                && promoCode.getValidTo().isAfter(LocalDateTime.now());
    }
    public PromoCode get(long codeId){
        return promoCodeRepository.findById(codeId)
                .orElseThrow(() -> new PromoCodeNotFoundException("Not found promo code"));
    }

}
