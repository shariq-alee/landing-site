package com.shariq.shariqtech.controller.topic;

import com.shariq.shariqtech.model.BlogPost;
import com.shariq.shariqtech.service.BlogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Articles filed under AWS. */
@RestController
public class AwsController extends TopicController {

    public AwsController(BlogService blogService) {
        super(blogService, "AWS");
    }

    @GetMapping("/api/topics/aws")
    public List<BlogPost> posts() {
        return postsForTopic();
    }
}
