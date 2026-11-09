package com.shariq.shariqtech.controller.topic;

import com.shariq.shariqtech.model.BlogPost;
import com.shariq.shariqtech.service.BlogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Articles filed under Java. */
@RestController
public class JavaController extends TopicController {

    public JavaController(BlogService blogService) {
        super(blogService, "Java");
    }

    @GetMapping("/api/topics/java")
    public List<BlogPost> posts() {
        return postsForTopic();
    }
}
