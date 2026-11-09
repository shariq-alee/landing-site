package com.shariq.shariqtech.controller.topic;

import com.shariq.shariqtech.model.BlogPost;
import com.shariq.shariqtech.service.BlogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Articles filed under Databases. */
@RestController
public class DatabasesController extends TopicController {

    public DatabasesController(BlogService blogService) {
        super(blogService, "Databases");
    }

    @GetMapping("/api/topics/databases")
    public List<BlogPost> posts() {
        return postsForTopic();
    }
}
