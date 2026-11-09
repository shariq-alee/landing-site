package com.shariq.shariqtech.controller.topic;

import com.shariq.shariqtech.model.BlogPost;
import com.shariq.shariqtech.service.BlogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Articles filed under Kubernetes. */
@RestController
public class KubernetesController extends TopicController {

    public KubernetesController(BlogService blogService) {
        super(blogService, "Kubernetes");
    }

    @GetMapping("/api/topics/kubernetes")
    public List<BlogPost> posts() {
        return postsForTopic();
    }
}
