package com.tuannq.store.controller.admin;

import com.tuannq.store.entity.Post;
import com.tuannq.store.model.dto.PostDTO;
import com.tuannq.store.model.request.CreatePostReq;
import com.tuannq.store.model.request.PostSearchForm;
import com.tuannq.store.model.response.PageResponse;
import com.tuannq.store.model.response.SuccessResponse;
import com.tuannq.store.security.CustomUserDetails;
import com.tuannq.store.service.BlogService;
import com.tuannq.store.service.ImageService;
import com.tuannq.store.util.AuthUtils;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ManagePostController {
    private final BlogService blogService;
    private final ImageService imageService;
    private final AuthUtils authUtils;

    @Autowired
    public ManagePostController(BlogService blogService, ImageService imageService, AuthUtils authUtils) {
        this.blogService = blogService;
        this.imageService = imageService;
        this.authUtils = authUtils;
    }

    @GetMapping("/admin/posts")
    public String getPostManagePage(
            Model model,
            @Validated PostSearchForm form
    ) {
        var data = blogService.searchByAdmin(form);
        model.addAttribute("posts", data.getContent());
        model.addAttribute("page", data);
        model.addAttribute("formSearch", form);

        return "admin/blog/list";
    }

    @GetMapping("/admin/posts/create")
    public String getPostCreatePage(Model model) {
        // Get list image of user
        var userOpt = authUtils.getUser();
        if (userOpt.isEmpty())
            return "error/401";
        var user = userOpt.get();

        List<String> images = imageService.getListImageOfUser(user.getId());
        model.addAttribute("images", images);

        return "admin/blog/create";
    }


    @GetMapping("/api/admin/posts")
    public ResponseEntity<SuccessResponse<PageResponse<PostDTO>>> getPostManagePage(
            @Validated PostSearchForm form
    ) {
        var data = blogService.searchByAdmin(form);
        return ResponseEntity.ok(new SuccessResponse<>(data));
    }


    @PostMapping("/api/admin/posts")
    public ResponseEntity<?> createPost(@Valid @RequestBody CreatePostReq req) {
        var user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        Post post = blogService.createPost(req, user);

        return ResponseEntity.ok(post.getId());
    }

    @GetMapping("/admin/posts/{id}")
    public String getPostDetailPage(Model model, @PathVariable long id) throws NotFoundException {
        // Get list image of user
        var user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        List<String> images = imageService.getListImageOfUser(user.getId());
        model.addAttribute("images", images);

        var post = blogService.getPostById(id);
        model.addAttribute("post", post);

        return "admin/blog/detail";
    }

    @PutMapping("/api/admin/posts/{id}")
    public ResponseEntity<?> updatePost(@Valid @RequestBody CreatePostReq req, @PathVariable long id) throws NotFoundException {
        var user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        blogService.updatePost(req, user, id);

        return ResponseEntity.ok("Cập nhật thành công");
    }

    @DeleteMapping("/api/admin/posts/{id}")
    public ResponseEntity<?> updatePost(@PathVariable long id) throws NotFoundException {
        blogService.deletePost(id);

        return ResponseEntity.ok("Xóa thành công");
    }
}
