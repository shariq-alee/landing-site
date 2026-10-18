package com.shariq.landing.controller;

import com.shariq.landing.model.ContactSubmission;
import com.shariq.landing.repository.ContactSubmissionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final ContactSubmissionRepository repository;

    public AdminController(ContactSubmissionRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/submissions")
    public List<ContactSubmission> submissions() {
        return repository.findAllByOrderByReceivedAtDesc();
    }
}
