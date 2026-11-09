package com.shariq.shariqtech.controller.topic;

import com.shariq.shariqtech.model.BlogPost;
import com.shariq.shariqtech.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/** Articles filed under Docker, served at /docker. */
@Controller
public class DockerController extends TopicController {

    public DockerController(BlogService blogService) {
        super(blogService, "Docker");
    }

    /** The readable page URL. Forwards to the shared topic page markup. */
    @GetMapping({"/docker", "/Docker"})
    public String page() {
        return "forward:/topic.html";
    }

    /** The JSON the page fetches to fill itself in. */
    @GetMapping("/api/docker")
    @ResponseBody
    public List<BlogPost> posts() {
        return postsForTopic();
    }
}
