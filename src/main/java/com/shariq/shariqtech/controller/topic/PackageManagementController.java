package com.shariq.shariqtech.controller.topic;

import com.shariq.shariqtech.model.BlogPost;
import com.shariq.shariqtech.service.BlogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Articles filed under Package Management. */
@RestController
public class PackageManagementController extends TopicController {

    public PackageManagementController(BlogService blogService) {
        super(blogService, "Package Management");
    }

    @GetMapping("/api/topics/package-management")
    public List<BlogPost> posts() {
        return postsForTopic();
    }
}
