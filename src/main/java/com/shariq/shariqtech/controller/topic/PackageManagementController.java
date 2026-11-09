package com.shariq.shariqtech.controller.topic;

import com.shariq.shariqtech.model.BlogPost;
import com.shariq.shariqtech.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/** Articles filed under Package Management, served at /package-management. */
@Controller
public class PackageManagementController extends TopicController {

    public PackageManagementController(BlogService blogService) {
        super(blogService, "Package Management");
    }

    /** The readable page URL. Forwards to the shared topic page markup. */
    @GetMapping({"/package-management", "/Package-Management"})
    public String page() {
        return "forward:/topic.html";
    }

    /** The JSON the page fetches to fill itself in. */
    @GetMapping("/api/package-management")
    @ResponseBody
    public List<BlogPost> posts() {
        return postsForTopic();
    }
}
