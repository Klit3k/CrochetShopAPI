package pl.edu.wat.crochetshopapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class PromoCode {
    // TODO: Nice to have number of possible usage
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String code;
    private LocalDateTime validFrom;
    private LocalDateTime validTo;
    private boolean isUsed = false;
}
