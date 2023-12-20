package com.myblog8.service;

import com.myblog8.payload.CommentDto;

public interface CommentService {
    CommentDto saveComment(CommentDto dto, long posId);

    void deleteByCommentId(long id);

    CommentDto updateByCommentId(CommentDto commentDto, long id);
}
