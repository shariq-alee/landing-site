package com.shariq.shariqtech.controller.topic;

import com.shariq.shariqtech.model.BlogPost;
import com.shariq.shariqtech.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/** Articles filed under Messaging, served at /messaging. */
@Controller
public class MessagingController extends TopicController {

    public MessagingController(BlogService blogService) {
        super(blogService, "Messaging");
    }

    /** The readable page URL. Forwards to the shared topic page markup. */
    @GetMapping({"/messaging", "/Messaging"})
    public String page() {
        return "forward:/topic.html";
    }

    /** The JSON the page fetches to fill itself in. */
    @GetMapping("/api/messaging")
    @ResponseBody
    public List<BlogPost> posts() {
        return postsForTopic();
    }
}
