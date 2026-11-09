package com.shariq.shariqtech.controller.topic;

import com.shariq.shariqtech.model.BlogPost;
import com.shariq.shariqtech.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/** Articles filed under Databases, served at /databases. */
@Controller
public class DatabasesController extends TopicController {

    public DatabasesController(BlogService blogService) {
        super(blogService, "Databases");
    }

    /** The readable page URL. Forwards to the shared topic page markup. */
    @GetMapping({"/databases", "/Databases"})
    public String page() {
        return "forward:/topic.html";
    }

    /** The JSON the page fetches to fill itself in. */
    @GetMapping("/api/databases")
    @ResponseBody
    public List<BlogPost> posts() {
        return postsForTopic();
    }
}
