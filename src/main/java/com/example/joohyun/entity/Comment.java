package com.example.joohyun.entity;

import com.example.joohyun.dto.CommentDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_pid")
    private Product product;

    @Column
    private String nickname;

    @Column
    private String content;

    @Column
    private LocalDateTime createAt;

    public static Comment createComment(User user, Product product, CommentDTO cmt) {
        if(cmt.getCid() != null)
            throw new IllegalArgumentException("Comment already exists");
        if(cmt.getPid() != product.getPid())
            throw new IllegalArgumentException("Product does not match");

        if(user == null){
            throw new IllegalArgumentException("User does not match");
        }

        return new Comment(user, product, cmt.getNickname(), cmt.getContent());
    }

    public Comment(User user, Product product, String nickname, String content) {
        this.user = user;
        this.product = product;
        this.nickname = nickname;
        this.content = content;
        this.createAt = LocalDateTime.now();
    }
}
