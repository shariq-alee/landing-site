package com.shariq.landing.model;

import java.time.LocalDate;
import java.util.List;

/**
 * A single blog post. Backed by src/main/resources/data/blogs.json -
 * add new entries there and they show up on the site with no code changes.
 */
public record BlogPost(
        String slug,
        String title,
        String excerpt,
        String content,
        String author,
        String authorInitials,
        LocalDate publishedAt,
        int readTimeMinutes,
        String coverEmoji,
        List<String> tags
) {}
