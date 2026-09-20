package com.shariq.landing.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.shariq.landing.model.BlogPost;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Loads blog posts from the bundled data/blogs.json at startup and serves
 * them in memory, newest first - same lightweight approach as the contact
 * form storage until this needs a real database.
 */
@Service
public class BlogService {

    private final List<BlogPost> posts;

    public BlogService() throws IOException {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        BlogPost[] loaded = mapper.readValue(
                new ClassPathResource("data/blogs.json").getInputStream(), BlogPost[].class);
        this.posts = List.of(loaded).stream()
                .sorted(Comparator.comparing(BlogPost::publishedAt).reversed())
                .toList();
    }

    public List<BlogPost> findAll() {
        return posts;
    }

    public List<BlogPost> findByTag(String tag) {
        return posts.stream()
                .filter(p -> p.tags().stream().anyMatch(t -> t.equalsIgnoreCase(tag)))
                .toList();
    }

    public Optional<BlogPost> findBySlug(String slug) {
        return posts.stream().filter(p -> p.slug().equals(slug)).findFirst();
    }
}
