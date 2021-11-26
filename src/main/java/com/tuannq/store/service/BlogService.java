package com.tuannq.store.service;


import com.tuannq.store.entity.Post;
import com.tuannq.store.entity.Users;
import com.tuannq.store.model.dto.PostDTO;
import com.tuannq.store.model.request.CreatePostReq;
import com.tuannq.store.model.request.PostSearchForm;
import com.tuannq.store.model.response.PageResponse;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BlogService {
    Post createPost(CreatePostReq req, Users user);

    void updatePost(CreatePostReq req, Users user, long id) throws NotFoundException;

    void deletePost(long id) throws NotFoundException;

    PageResponse<Post> getListPostPublic(Integer page);

    Post getPostById(long id) throws NotFoundException;

    List<Post> getLatestPostsNotId(long id);

    List<Post> getLatestPost();

    PageResponse<Post> adminGetListPost(String title, String status, Integer page, String order, String direction);

    PageResponse<PostDTO> searchByAdmin(PostSearchForm form);
}
