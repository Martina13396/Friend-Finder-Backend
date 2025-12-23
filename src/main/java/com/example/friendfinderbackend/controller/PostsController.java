package com.example.friendfinderbackend.controller;

import com.example.friendfinderbackend.service.PostService;
import com.example.friendfinderbackend.service.dto.PostsDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.path;

@RestController
@RequestMapping("/posts")
public class PostsController {

    private PostService postService;

    @Autowired
    public PostsController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/createPost")
    ResponseEntity<PostsDto> createPost(@RequestParam("content") String content , @RequestParam(value = "file" , required = false) MultipartFile file ) throws IOException {
       PostsDto postsDto = new PostsDto();
       postsDto.setContent(content);
       if(file != null && !file.isEmpty()) {
           String fileName = file.getOriginalFilename();
           String fileType = file.getContentType();
           Path uploadDir = Paths.get("uploads/");
           if(!Files.exists(uploadDir)) {
               Files.createDirectories(uploadDir);
           }
           Path filePath = uploadDir.resolve(fileName);
           Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
           postsDto.setMediaUrl(fileName);
           postsDto.setMediaType(fileType.startsWith("image") ? "image" : "video");
       }
       return ResponseEntity.ok(postService.createPost(postsDto));
    }

    @GetMapping("/getPosts")
    ResponseEntity<List<PostsDto>>getPostsForUserAndFriends() {
        return ResponseEntity.ok(postService.getPostsForUserAndFriends());
    }

    @GetMapping("/getUserPosts")
    ResponseEntity<List<PostsDto>>getPostsByAccountId() {
        return ResponseEntity.ok(postService.getPostsByAccountId());
    }

    @PutMapping("/deletePost")
    ResponseEntity<List<PostsDto>> deletePost(@RequestParam Long postId , @RequestParam String viewType) {
       return  ResponseEntity.ok(postService.deletePost(postId, viewType));
    }

    @GetMapping("/getMedia")
    ResponseEntity<List<Map<String,Object>>> getAllMediaUrls(){
        return ResponseEntity.ok(postService.getAllMediaUrls());
    }


    @GetMapping("/getFriendAlbum")
    ResponseEntity<List<Map<String,Object>>> getFriendsMediaUrls(@RequestParam Long accountId) {
        return ResponseEntity.ok(postService.getFriendAlbum(accountId));

    }
    @GetMapping("/getFriendPosts")
    ResponseEntity<List<PostsDto>> getFriendPosts(@RequestParam Long accountId){
        return ResponseEntity.ok(postService.getFriendPosts(accountId));

    }

}
