package com.example.joohyun.service;

import com.example.joohyun.dto.CommentDTO;
import com.example.joohyun.entity.Comment;
import com.example.joohyun.entity.Product;
import com.example.joohyun.entity.User;
import com.example.joohyun.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private ProductService productService;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserService userService;

    public CommentDTO create(Long productId, CommentDTO cmt) {
        Optional<Product> get = productService.getProductById(productId);
        Comment created = null;
        User user = userService.findByEmail(cmt.getUid());
        if(get.isPresent() && user != null){
            Comment comment = Comment.createComment(user, get.get(), cmt);
            created = commentRepository.save(comment);
        }
        return CommentDTO.createCommentDTO(created);
    }

    public CommentDTO update(CommentDTO cmt){
        Comment target = commentRepository.findById(cmt.getCid()).orElse(null);
        if (target != null){
            target.setNickname(cmt.getNickname());
            target.setContent(cmt.getContent());
            target.setCreateAt(LocalDateTime.now());
            return CommentDTO.createCommentDTO(commentRepository.save(target));
        }
        return null;
    }

    public List<CommentDTO> findCommentList(Product product){
        List<Comment> cmtList = commentRepository.findAllByProduct(product);
        List<CommentDTO> cmtDTOList = new ArrayList<>();
        for(Comment cmt : cmtList){
            cmtDTOList.add(CommentDTO.createCommentDTO(cmt));
        }
        return cmtDTOList;
    }

    public CommentDTO findCommentById(Long commentId){
        Comment cmt = commentRepository.findById(commentId).orElse(null);
        if(cmt == null){
            return null;
        }
        return CommentDTO.createCommentDTO(cmt);
    }

    public boolean deleteComment(Long commentId){
        Comment target = commentRepository.findById(commentId).orElse(null);
        if(target != null){
            commentRepository.delete(target);
            return true;
        }
        return false;
    }

    public boolean macthUserComment(Long commentId, String userEmail){
        Comment target = commentRepository.findById(commentId).orElse(null);
        if(target != null){
            System.out.println(target.getUser().getEmail().equals(userEmail));
            return target.getUser().getEmail().equals(userEmail);
        }
        return false;
    }
}
