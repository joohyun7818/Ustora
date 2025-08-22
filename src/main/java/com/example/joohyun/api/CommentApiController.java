package com.example.joohyun.api;

import com.example.joohyun.dto.CommentDTO;
import com.example.joohyun.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class CommentApiController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/api/comments/{cid}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable(name = "cid") Long cid) {
        CommentDTO comment = commentService.findCommentById(cid);
        if(comment== null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(comment);
        }
    }

    @PostMapping("/api/comments/{productId}")
    public ResponseEntity<CommentDTO> comment(@PathVariable(name = "productId") Long productId, @RequestBody CommentDTO cmt) {
        CommentDTO createdcmt = commentService.create(productId, cmt);
        if(createdcmt == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(createdcmt);
    }

    @PutMapping("/api/comments")
    public ResponseEntity<CommentDTO> updateComment(@RequestBody CommentDTO cmt) {
        if (!commentService.macthUserComment(cmt.getCid(), cmt.getUid())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        CommentDTO updatedcmt = commentService.update(cmt);
        if(updatedcmt == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(updatedcmt);
        }
    }

    @DeleteMapping("/api/comments")
    public ResponseEntity<Object> deleteComment(@RequestBody HashMap<String, Object> map) {
        Long delCid = new Long((int)map.get("cid"));
        String delUid = (String)map.get("uid");
        if (!commentService.macthUserComment(delCid, delUid)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        boolean success = commentService.deleteComment(delCid);
        if(success){
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
