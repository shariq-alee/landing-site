package com.shariq.landing.repository;

import com.shariq.landing.model.ContactSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactSubmissionRepository extends JpaRepository<ContactSubmission, Long> {

    List<ContactSubmission> findAllByOrderByReceivedAtDesc();
}
