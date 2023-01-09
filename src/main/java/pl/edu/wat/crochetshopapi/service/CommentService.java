package pl.edu.wat.crochetshopapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.crochetshopapi.Configuration;
import pl.edu.wat.crochetshopapi.exception.CommentSpamException;
import pl.edu.wat.crochetshopapi.exception.PromoCodeNotFoundException;
import pl.edu.wat.crochetshopapi.model.Comment;
import pl.edu.wat.crochetshopapi.model.Product;
import pl.edu.wat.crochetshopapi.repository.CommentRepository;
import pl.edu.wat.crochetshopapi.repository.ProductRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    ClientService clientService;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    public Comment add(long productId, long clientId, String content) {
        if (spamFilter(clientId, productId)!=0) {
            throw new CommentSpamException("Too many comments. Wait " + (Configuration.COMMENT_DELAY_TIME*60+spamFilter(clientId, productId)) + " seconds.");
        }
        Comment comment = commentRepository.save(Comment.builder()
                .author(clientService.get(clientId))
                .content(content)
                .creationTime(LocalDateTime.now())
                .build());
        Product p = productService.get(productId);
        if (p.getComments().isEmpty())
            p.setComments(new ArrayList<>());
        p.getComments().add(comment);
        productRepository.save(p);
        return comment;
    }

    public void del(long commentId) {
        commentRepository.delete(get(commentId));
    }

    public Comment edit(long commentId, Comment comment) {
        comment.setId(get(commentId).getId());
        return commentRepository.save(comment);
    }

    public Comment get(long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow();
    }

    public long spamFilter(long clientId, long productId) {
        List<Comment> commentList = productService.get(productId).getComments();
        commentList.sort(Comparator.comparing(Comment::getCreationTime));
        for (Comment el : commentList) {
            if (el.getAuthor() == clientService.get(clientId)) {
                if (LocalDateTime.now().minusMinutes(Configuration.COMMENT_DELAY_TIME).isBefore(el.getCreationTime()))
                    return Duration.between(LocalDateTime.now(), el.getCreationTime()).getSeconds();
            }
        }
        return 0;
    }
}
