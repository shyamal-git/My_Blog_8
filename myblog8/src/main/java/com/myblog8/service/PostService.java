package com.myblog8.service;

import com.myblog8.payload.PostDto;
import com.myblog8.payload.PostResponse;

import java.util.List;

public interface PostService {
    public PostDto createPost(PostDto postDto);

    void deletePostById(int postId);

    PostDto getPostById(int postId);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);


}
