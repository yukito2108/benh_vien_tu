package com.tuannq.store.controller.anonymous;

import com.tuannq.store.entity.Post;
import com.tuannq.store.model.dto.OrderItemDTO;
import com.tuannq.store.service.BlogService;
import com.tuannq.store.service.CategoryService;
import com.tuannq.store.service.OrderItemService;
import com.tuannq.store.util.AuthUtils;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BlogController {
    private final BlogService blogService;
    private final AuthUtils authUtils;

    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    public BlogController(BlogService blogService, AuthUtils authUtils) {
        this.blogService = blogService;
        this.authUtils = authUtils;
    }

    @GetMapping("/tin-tuc")
    public String getBlogPage(
            Model model,
            @RequestParam(required = false, defaultValue = "1") Integer page
    ) {
        var userOpt = authUtils.getUser();
        if (userOpt.isPresent()) {
            var items = orderItemService.getCartByUserId(userOpt.get().getId())
                    .stream()
                    .map(OrderItemDTO::new)
                    .collect(Collectors.toList());

            var totalAmount = items.stream().map(OrderItemDTO::getAmount).reduce(Long::sum).orElse(0L);
            model.addAttribute("cart", items);
            model.addAttribute("totalAmount", totalAmount);
        } else {
            model.addAttribute("cart", new ArrayList<>());
            model.addAttribute("totalAmount", 0);
        }
        model.addAttribute("header_categories", categoryService.findByParent());

        var posts = blogService.getListPostPublic(page);

        model.addAttribute("totalPages", posts.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("listPost", posts.getContent());

        return "blog/blog";
    }

    @GetMapping("/tin-tuc/{slug}/{id}")
    public String getPostDetailPage(
            Model model,
            @PathVariable long id,
            @PathVariable String slug
    ) throws NotFoundException {

        var userOpt = authUtils.getUser();
        if (userOpt.isPresent()) {
            var items = orderItemService.getCartByUserId(userOpt.get().getId())
                    .stream()
                    .map(OrderItemDTO::new)
                    .collect(Collectors.toList());

            var totalAmount = items.stream().map(OrderItemDTO::getAmount).reduce(Long::sum).orElse(0L);
            model.addAttribute("cart", items);
            model.addAttribute("totalAmount", totalAmount);
        } else {
            model.addAttribute("cart", new ArrayList<>());
            model.addAttribute("totalAmount", 0);
        }

        Post post = blogService.getPostById(id);
        List<Post> latestPosts = blogService.getLatestPostsNotId(id);

        model.addAttribute("header_categories", categoryService.findByParent());
        model.addAttribute("post", post);
        model.addAttribute("latestPosts", latestPosts);

        return "blog/detail";
    }

    @GetMapping("/chinh-sach")
    public String getPolicyPage(Model model) {
        return "blog/policy";
    }

    @GetMapping("/dieu-khoan")
    public String getRulesPage(Model model) {
        return "blog/rules";
    }

    @GetMapping("/hoi-dap")
    public String getQAPage(Model model) {
        return "blog/faqs";
    }
}
