package com.shariq.shariqtech.controller.topic;

import com.shariq.shariqtech.model.BlogPost;
import com.shariq.shariqtech.service.BlogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Articles filed under Enterprise AI. */
@RestController
public class EnterpriseAiController extends TopicController {

    public EnterpriseAiController(BlogService blogService) {
        super(blogService, "Enterprise AI");
    }

    @GetMapping("/api/topics/enterprise-ai")
    public List<BlogPost> posts() {
        return postsForTopic();
    }
}
