package com.shariq.shariqtech.controller.topic;

import com.shariq.shariqtech.model.BlogPost;
import com.shariq.shariqtech.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/** Articles filed under AWS, served at /aws. */
@Controller
public class AwsController extends TopicController {

    public AwsController(BlogService blogService) {
        super(blogService, "AWS");
    }

    /** The readable page URL. Forwards to the shared topic page markup. */
    @GetMapping({"/aws", "/AWS"})
    public String page() {
        return "forward:/topic.html";
    }

    /** The JSON the page fetches to fill itself in. */
    @GetMapping("/api/aws")
    @ResponseBody
    public List<BlogPost> posts() {
        return postsForTopic();
    }
}
