package com.shariq.shariqtech.controller.topic;

import com.shariq.shariqtech.model.BlogPost;
import com.shariq.shariqtech.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/** Articles filed under Kubernetes, served at /kubernetes. */
@Controller
public class KubernetesController extends TopicController {

    public KubernetesController(BlogService blogService) {
        super(blogService, "Kubernetes");
    }

    /** The readable page URL. Forwards to the shared topic page markup. */
    @GetMapping({"/kubernetes", "/Kubernetes"})
    public String page() {
        return "forward:/topic.html";
    }

    /** The JSON the page fetches to fill itself in. */
    @GetMapping("/api/kubernetes")
    @ResponseBody
    public List<BlogPost> posts() {
        return postsForTopic();
    }
}
