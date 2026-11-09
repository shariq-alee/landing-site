package com.shariq.shariqtech.controller.topic;

import com.shariq.shariqtech.model.BlogPost;
import com.shariq.shariqtech.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/** Articles filed under Enterprise AI, served at /enterprise-ai. */
@Controller
public class EnterpriseAiController extends TopicController {

    public EnterpriseAiController(BlogService blogService) {
        super(blogService, "Enterprise AI");
    }

    /** The readable page URL. Forwards to the shared topic page markup. */
    @GetMapping({"/enterprise-ai", "/Enterprise-AI"})
    public String page() {
        return "forward:/topic.html";
    }

    /** The JSON the page fetches to fill itself in. */
    @GetMapping("/api/enterprise-ai")
    @ResponseBody
    public List<BlogPost> posts() {
        return postsForTopic();
    }
}
