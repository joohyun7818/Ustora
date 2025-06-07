package com.example.joohyun.dto;

import com.example.joohyun.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentDTO {
    private Long cid;
    private String uid;
    private long pid;
    private String nickname;
    private String content;
    private String createdAt;

    public static CommentDTO createCommentDTO(Comment comment) {
        return new CommentDTO(comment.getId(),
                                comment.getUser().getEmail(),
                                comment.getProduct().getPid(),
                                comment.getNickname(),
                                comment.getContent(),
                                comment.getCreateAt().toString());
    }
}
