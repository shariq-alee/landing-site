package com.shariq.shariqtech.controller.topic;

import com.shariq.shariqtech.model.BlogPost;
import com.shariq.shariqtech.service.BlogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Articles filed under Docker. */
@RestController
public class DockerController extends TopicController {

    public DockerController(BlogService blogService) {
        super(blogService, "Docker");
    }

    @GetMapping("/api/topics/docker")
    public List<BlogPost> posts() {
        return postsForTopic();
    }
}
