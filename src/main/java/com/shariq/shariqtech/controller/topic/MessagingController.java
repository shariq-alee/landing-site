package com.shariq.shariqtech.controller.topic;

import com.shariq.shariqtech.model.BlogPost;
import com.shariq.shariqtech.service.BlogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Articles filed under Messaging. */
@RestController
public class MessagingController extends TopicController {

    public MessagingController(BlogService blogService) {
        super(blogService, "Messaging");
    }

    @GetMapping("/api/topics/messaging")
    public List<BlogPost> posts() {
        return postsForTopic();
    }
}
