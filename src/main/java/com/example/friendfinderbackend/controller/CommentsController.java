package com.example.friendfinderbackend.controller.vm;

import com.example.friendfinderbackend.service.CommentsService;
import com.example.friendfinderbackend.service.dto.CommentsDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentsController {

    private CommentsService commentsService;

    @Autowired
    public CommentsController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @PostMapping("/add")
    ResponseEntity<CommentsDto>  addComment(@RequestBody @Valid CommentsDto commentsDto) {
        return ResponseEntity.ok(commentsService.addComment(commentsDto));
    }


    @GetMapping("getComments")
    ResponseEntity<List<CommentsDto>> getCommentsForPost(@RequestParam Long postId) {
        return ResponseEntity.ok(commentsService.getCommentsForPost(postId));
    }

    @DeleteMapping("/deleteComment")
    ResponseEntity<Void> deleteComment(@RequestParam Long commentId){
        commentsService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
        }

}
