package org.message.consumer.service;

import lombok.RequiredArgsConstructor;
import org.message.consumer.entity.Comment;
import org.message.consumer.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public void saveAllComments(List<Comment> commentList) {
        commentRepository.saveAll(commentList);
        commentRepository.flush();
    }
}
