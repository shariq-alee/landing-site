package com.shariq.shariqtech.controller.topic;

import com.shariq.shariqtech.model.BlogPost;
import com.shariq.shariqtech.service.BlogService;

import java.util.List;

/**
 * Shared behaviour for the per-topic controllers. Every topic in the menu bar
 * has its own controller and its own endpoint; only the tag they look up
 * differs, so the lookup itself lives here.
 */
abstract class TopicController {

    private final BlogService blogService;
    private final String tag;

    protected TopicController(BlogService blogService, String tag) {
        this.blogService = blogService;
        this.tag = tag;
    }

    protected List<BlogPost> postsForTopic() {
        return blogService.findByTag(tag);
    }
}
