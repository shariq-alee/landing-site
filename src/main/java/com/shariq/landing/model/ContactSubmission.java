package com.shariq.landing.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "contact_submission")
public class ContactSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 150)
    private String email;

    @Column(nullable = false, length = 2000)
    private String message;

    @Column(name = "received_at", nullable = false)
    private Instant receivedAt;

    protected ContactSubmission() {}

    public ContactSubmission(String name, String email, String message, Instant receivedAt) {
        this.name = name;
        this.email = email;
        this.message = message;
        this.receivedAt = receivedAt;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getMessage() { return message; }
    public Instant getReceivedAt() { return receivedAt; }
}
