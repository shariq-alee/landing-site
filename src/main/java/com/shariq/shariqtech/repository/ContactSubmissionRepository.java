package com.shariq.shariqtech.repository;

import com.shariq.shariqtech.model.ContactSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactSubmissionRepository extends JpaRepository<ContactSubmission, Long> {

    List<ContactSubmission> findAllByOrderByReceivedAtDesc();
}
