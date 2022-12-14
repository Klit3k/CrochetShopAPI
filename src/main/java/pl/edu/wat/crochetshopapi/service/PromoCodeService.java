package pl.edu.wat.crochetshopapi.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.crochetshopapi.exception.PromoCodeAlreadyExistsException;
import pl.edu.wat.crochetshopapi.exception.PromoCodeNotFoundException;
import pl.edu.wat.crochetshopapi.model.PromoCode;
import pl.edu.wat.crochetshopapi.model.PromoCodeStatus;
import pl.edu.wat.crochetshopapi.repository.PromoCodeRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PromoCodeService {
    @Autowired
    private PromoCodeRepository promoCodeRepository;

    public PromoCode generate(LocalDateTime validFrom, LocalDateTime validTo) {
        return promoCodeRepository.save(PromoCode.builder()
                .code(RandomStringUtils.random(8, true, true))
                .validFrom(validFrom)
                .validTo(validTo)
                .build());
    }
    public PromoCode add(LocalDateTime validFrom, LocalDateTime validTo, String code) {
        if(promoCodeRepository.findByCode(code).isPresent()) {
            throw new PromoCodeAlreadyExistsException("Code already exists.");
        }

        return promoCodeRepository.save(PromoCode.builder()
                .code(code)
                .validFrom(validFrom)
                .validTo(validTo)
                .build());
    }

    public void delete(long codeId) {
        promoCodeRepository.delete(get(codeId));
    }

    public PromoCodeStatus check(String code) {
        if (promoCodeRepository.findByCode(code).isEmpty())
            return PromoCodeStatus.NOT_FOUND;
        PromoCode promoCode = promoCodeRepository.findByCode(code).get();
        if (!(promoCode.getValidFrom().isBefore(LocalDateTime.now()) && promoCode.getValidTo().isAfter(LocalDateTime.now())))
            return PromoCodeStatus.EXPIRED;

        return promoCode.getCode().equals(code) && !promoCode.isUsed() ? PromoCodeStatus.ACTIVE : PromoCodeStatus.USED;
    }

    // TODO: We should connect code with order status.
    public boolean apply(String code) {
        if (check(code) == PromoCodeStatus.ACTIVE) {
            PromoCode promoCode = promoCodeRepository.findByCode(code).get();
            promoCode.setUsed(true);
            promoCodeRepository.save(promoCode);
            return true;
        } else
            return false;
    }

    public PromoCode get(long codeId) {
        return promoCodeRepository.findById(codeId)
                .orElseThrow(() -> new PromoCodeNotFoundException("Not found promo code"));
    }

    public List<PromoCode> getAll() {
        List<PromoCode> list = new ArrayList<>();
        promoCodeRepository.findAll().forEach(list::add);
        return list;
    }
}
