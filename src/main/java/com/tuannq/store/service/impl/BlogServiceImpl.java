package com.tuannq.store.service.impl;


import com.github.slugify.Slugify;
import com.tuannq.store.entity.Post;
import com.tuannq.store.entity.Users;
import com.tuannq.store.exception.BadRequestException;
import com.tuannq.store.exception.InternalServerException;
import com.tuannq.store.model.dto.PostDTO;
import com.tuannq.store.model.request.CreatePostReq;
import com.tuannq.store.model.request.PostSearchForm;
import com.tuannq.store.model.response.PageResponse;
import com.tuannq.store.model.type.StatusPost;
import com.tuannq.store.repository.PostRepository;
import com.tuannq.store.service.BlogService;
import com.tuannq.store.util.ConverterUtils;
import com.tuannq.store.util.PageUtil;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static com.tuannq.store.config.DefaultVariable.LIMIT;

@Component
public class BlogServiceImpl implements BlogService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ConverterUtils converterUtils;

    @Override
    public Post createPost(CreatePostReq req, Users user) {
        Post post = new Post();

        post.setTitle(req.getTitle());
        post.setContent(req.getContent());
        Slugify slg = new Slugify();
        post.setSlug(slg.slugify(req.getTitle()));
        post.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        post.setCreatedBy(user);

        if (req.getStatus() == StatusPost.PUBLIC.ordinal()) {
            // Public post
            if (req.getDescription().isEmpty()) {
                throw new BadRequestException("Để công khai bài viết vui lòng nhập mô tả");
            }
            if (req.getImage().isEmpty()) {
                throw new BadRequestException("Vui lòng chọn ảnh cho bài viết trước khi công khai");
            }
            post.setPublishedAt(Instant.now());
        } else {
            if (req.getStatus() != StatusPost.DRAFT.ordinal()) {
                throw new BadRequestException("Trạng thái bài viết không hợp lệ");
            }
        }
        post.setDescription(req.getDescription());
        post.setThumbnail(req.getImage());
        post.setStatus(req.getStatus().toString());

        postRepository.save(post);

        return post;
    }

    @Override
    public void updatePost(CreatePostReq req, Users user, long id) throws NotFoundException {
        Optional<Post> rs = postRepository.findById(id);
        if (rs.isEmpty()) {
            throw new NotFoundException("Bài viết không tồn tại");
        }

        Post post = rs.get();
        post.setTitle(req.getTitle());
        post.setContent(req.getContent());
        Slugify slg = new Slugify();
        post.setSlug(slg.slugify(req.getTitle()));
        post.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        post.setModifiedBy(user);
        if (req.getStatus() == StatusPost.PUBLIC.ordinal()) {
            // Public post
            if (req.getDescription().isEmpty()) {
                throw new BadRequestException("Để công khai bài viết vui lòng nhập mô tả");
            }
            if (req.getImage().isEmpty()) {
                throw new BadRequestException("Vui lòng chọn ảnh cho bài viết trước khi công khai");
            }
            if (post.getPublishedAt() == null) {
                post.setPublishedAt(Instant.now());
            }
        } else {
            if (req.getStatus() != StatusPost.PUBLIC.ordinal()) {
                throw new BadRequestException("Trạng thái bài viết không hợp lệ");
            }
        }
        post.setDescription(req.getDescription());
        post.setThumbnail(req.getImage());
        post.setStatus(req.getStatus().toString());

        try {
            postRepository.save(post);
        } catch (Exception ex) {
            throw new InternalServerException("Lỗi khi cập nhật bài viết");
        }
    }

    @Override
    public void deletePost(long id) throws NotFoundException {
        Optional<Post> rs = postRepository.findById(id);
        if (rs.isEmpty()) {
            throw new NotFoundException("Bài viết không tồn tại");
        }

        try {
            postRepository.delete(rs.get());
        } catch (Exception ex) {
            throw new InternalServerException("Lỗi khi xóa bài viết");
        }
    }

    @Override
    public PageResponse<Post> getListPostPublic(Integer page) {
        var data = postRepository.findByPublic(new PageUtil(page, LIMIT).getPageRequest());
        return new PageResponse<>(data);
    }

    @Override
    public Post getPostById(long id) throws NotFoundException {
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) {
            throw new NotFoundException("Không tìm thấy tin tức");
        }

        return post.get();
    }

    @Override
    public List<Post> getLatestPostsNotId(long id) {
        return postRepository.getLatestPostsNotId(StatusPost.PUBLIC.ordinal(), id, 8);
    }

    @Override
    public List<Post> getLatestPost() {
        return postRepository.getLatestPosts(0, 8);
    }

    @Override
    public PageResponse<Post> adminGetListPost(String title, String status, Integer page, String order, String direction) {
        int limit = 15;

        return new PageResponse<>();
    }

    @Override
    public PageResponse<PostDTO> searchByAdmin(PostSearchForm form) {
        var data = postRepository.searchByAdmin(
                form,
                new PageUtil(form.getPage(), LIMIT, form.getOrder(), form.getDirection()).getPageRequest()
        ).map(PostDTO::new);

        return new PageResponse<>(data);
    }
}
