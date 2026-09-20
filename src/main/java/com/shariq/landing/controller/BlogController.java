package com.shariq.landing.controller;

import com.shariq.landing.model.BlogPost;
import com.shariq.landing.service.BlogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping
    public List<BlogPost> list(@RequestParam(required = false) String tag) {
        return (tag == null || tag.isBlank()) ? blogService.findAll() : blogService.findByTag(tag);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<BlogPost> getBySlug(@PathVariable String slug) {
        return blogService.findBySlug(slug)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
