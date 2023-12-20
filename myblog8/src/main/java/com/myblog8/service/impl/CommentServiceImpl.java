package com.myblog8.service.impl;

import com.myblog8.entity.Comment;
import com.myblog8.entity.Post;
import com.myblog8.exception.ResourceNotFound;
import com.myblog8.payload.CommentDto;
import com.myblog8.repository.CommentRepository;
import com.myblog8.repository.PostRepository;
import com.myblog8.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    public ModelMapper modelMapper;

    private CommentRepository commentRepo;
    private PostRepository postRepo;

    public CommentServiceImpl(CommentRepository commentRepo, PostRepository postRepo, ModelMapper modelMapper) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
        this.modelMapper=modelMapper;
    }

    @Override
    public CommentDto saveComment(CommentDto dto, long posId) {
        Post post = postRepo.findById(posId).orElseThrow(
                () -> new ResourceNotFound("Post not found with id: " + posId)
        );
//        Comment comment = new Comment();
//        comment.setId(dto.getId());
//        comment.setName(dto.getName());
//        comment.setEmail(dto.getEmail());
//        comment.setBody(dto.getBody());
//        comment.setPost(post);
        Comment comment = mapToComment(dto);
        comment.setPost(post);

        Comment savedComment = commentRepo.save(comment);
        CommentDto commentDto = new CommentDto();
        commentDto.setId(savedComment.getId());
        commentDto.setName(savedComment.getName());
        commentDto.setEmail(savedComment.getEmail());
        commentDto.setBody(savedComment.getBody());
        return commentDto;
    }

    @Override
    public void deleteByCommentId(long id) {
        commentRepo.deleteById(id);
    }

    @Override
    public CommentDto updateByCommentId(CommentDto commentDto, long id) {
        Comment comment = commentRepo.findById(id).orElseThrow(
                () -> new ResourceNotFound("Comment not found for id: " + id)
        );
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment updatedComment = commentRepo.save(comment);

        CommentDto dto = new CommentDto();
        dto.setId(updatedComment.getId());
        dto.setName(updatedComment.getName());
        dto.setEmail(updatedComment.getEmail());
        dto.setBody(updatedComment.getBody());
        return dto;
    }
    public Comment mapToComment(CommentDto dto){
        Comment comment = modelMapper.map(dto, Comment.class);
//        Comment classomment = new Comment();
//        comment.setId(dto.getId());
//        comment.setName(dto.getName());
//        comment.setEmail(dto.getEmail());
//        comment.setBody(dto.getBody());
        return comment;
    }
}
