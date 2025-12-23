package com.example.friendfinderbackend.controller;

import com.example.friendfinderbackend.service.FriendRequestService;
import com.example.friendfinderbackend.service.dto.FriendRequestDto;
import com.example.friendfinderbackend.service.dto.securitydto.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friendRequest")
public class FriendRequestController {

    private FriendRequestService  friendRequestService;

    @Autowired
    public FriendRequestController(FriendRequestService friendRequestService) {
        this.friendRequestService = friendRequestService;
    }


    @PostMapping("/sendRequest")
    ResponseEntity<FriendRequestDto> sendFriendRequest(@RequestBody Long receiverId){
        return ResponseEntity.ok().body(friendRequestService.sendFriendRequest(receiverId));
    }

    @PostMapping("/acceptRequest")
    ResponseEntity<Void> acceptFriendRequest(@RequestParam Long requestId){
        friendRequestService.acceptFriendRequest(requestId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/rejectRequest")
    ResponseEntity<Void> rejectFriendRequest(@RequestParam Long requestId){
        friendRequestService.rejectFriendRequest(requestId);
        return  ResponseEntity.ok().build();
    }

    @GetMapping("/getFriendRequests")
    ResponseEntity<List<FriendRequestDto>>getFriendRequestsForUser(){
        return ResponseEntity.ok().body(friendRequestService.getFriendRequestsForUser());
    }

    @GetMapping("/getFriends")
    ResponseEntity<List<AccountDto>> getFriendsForUser(){
        return ResponseEntity.ok().body(friendRequestService.getFriendsForUser());
    }

    @GetMapping("/getWhoToFollow")
    ResponseEntity<List<AccountDto>> getWhoToFollow(){
        return ResponseEntity.ok().body(friendRequestService.getWhoToFollow());
    }

    @GetMapping("/getFriendsByAccountId")
    ResponseEntity<List<AccountDto>> getFriendsForFriend(@RequestParam Long accountId){
        return ResponseEntity.ok().body(friendRequestService.getFriendsForFriend(accountId));
    }

    @GetMapping("/requestForSender")
    ResponseEntity<List <FriendRequestDto>>getFriendRequestsBySender (){
     return ResponseEntity.ok().body(friendRequestService.getFriendRequestsBySender());

    }
   @GetMapping("/acceptedNotification")
    ResponseEntity<List<FriendRequestDto>> getAcceptedFriendRequests (){
        return ResponseEntity.ok().body(friendRequestService.getAcceptedFriendRequests());

    }

    @PutMapping("/markRead")
    public ResponseEntity<Void> markNotificationRead(@RequestBody Long accountId){
        friendRequestService.markNotificationRead(accountId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAcceptedCount")
    ResponseEntity<Long> getFriendRequestCountBySender(){
        return ResponseEntity.ok().body(friendRequestService.getFriendRequestCountBySender());
    }

    @GetMapping("/getUnreadRequests")
    ResponseEntity<Long>getUnreadRequestCount(){
        return ResponseEntity.ok().body(friendRequestService.getUnreadRequestCount());
    }
}
