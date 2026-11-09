CREATE TABLE contact_submission (
    id          BIGSERIAL     PRIMARY KEY,
    name        VARCHAR(100)  NOT NULL,
    email       VARCHAR(150)  NOT NULL,
    message     VARCHAR(2000) NOT NULL,
    received_at TIMESTAMP(6) WITH TIME ZONE NOT NULL
);

-- The inbox is always read newest first.
CREATE INDEX idx_contact_submission_received_at
    ON contact_submission (received_at DESC);
