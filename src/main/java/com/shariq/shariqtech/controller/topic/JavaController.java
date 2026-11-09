package com.shariq.shariqtech.controller.topic;

import com.shariq.shariqtech.model.BlogPost;
import com.shariq.shariqtech.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/** Articles filed under Java, served at /java. */
@Controller
public class JavaController extends TopicController {

    public JavaController(BlogService blogService) {
        super(blogService, "Java");
    }

    /** The readable page URL. Forwards to the shared topic page markup. */
    @GetMapping({"/java", "/Java"})
    public String page() {
        return "forward:/topic.html";
    }

    /** The JSON the page fetches to fill itself in. */
    @GetMapping("/api/java")
    @ResponseBody
    public List<BlogPost> posts() {
        return postsForTopic();
    }
}
