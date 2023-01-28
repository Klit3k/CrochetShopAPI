package pl.edu.wat.crochetshopapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CommentDTO {
    private long id;
    private AuthorDTO author;
    private String content;
    private String creationTime;
}
