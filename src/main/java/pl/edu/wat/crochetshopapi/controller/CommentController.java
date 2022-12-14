package pl.edu.wat.crochetshopapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wat.crochetshopapi.service.CommentService;

@RestController
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping(value = "/comment")
    public ResponseEntity<?> addComment(@RequestParam("productId") long productId,
                                        @RequestParam("clientId") long clientId,
                                        @RequestParam("content") String content) {
        return new ResponseEntity<>(commentService.add(productId, clientId, content), HttpStatusCode.valueOf(200));
    }

    @DeleteMapping(value = "/comment")
    public ResponseEntity<?> delComment(@RequestParam("commentId") long commentId) {
        commentService.del(commentId);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }
    @PostMapping(value = "/spam")
    public ResponseEntity<?> spamFilter(@RequestParam("productId") long productId,
                                        @RequestParam("clientId") long clientId) {
        return new ResponseEntity<>(commentService.spamFilter(clientId, productId), HttpStatusCode.valueOf(200));
    }
}
