package com.example.friendfinderbackend.controller;

import com.example.friendfinderbackend.model.security.Account;
import com.example.friendfinderbackend.service.ActionService;
import com.example.friendfinderbackend.service.dto.ActionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/action")
public class ActionController {

    private ActionService actionService;

    @Autowired
    public ActionController(ActionService actionService) {
        this.actionService = actionService;
    }

    @GetMapping("/top5Actions")
    ResponseEntity<List<ActionDto>>getTop5Actions(){
        return ResponseEntity.ok(actionService.getTop5());
    }

    @PostMapping("/saveAction")
    ResponseEntity<ActionDto> save(@RequestParam Account account , @RequestParam String actionText  ) {
        return ResponseEntity.ok(actionService.save(account, actionText ));
    }

    @GetMapping("/getFriendAction")
    ResponseEntity<List<ActionDto>>getTop5ForFriend(@RequestParam Long accountId){
        return ResponseEntity.ok(actionService.getTop5ForFriend(accountId));
    }
}
