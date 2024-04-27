USE base;
SET CHARSET utf8mb4;

-- client_user ADD FOREIGN KEY
ALTER TABLE client_user
    ADD CONSTRAINT fk_client_user_registration_user_id
    FOREIGN KEY (registration_user_id)
    REFERENCES client_user(client_user_id);

ALTER TABLE client_user
    ADD CONSTRAINT fk_client_user_update_user_id
    FOREIGN KEY (update_user_id)
    REFERENCES client_user(client_user_id);

-- client_user_password ADD FOREIGN KEY
ALTER TABLE client_user_password
    ADD CONSTRAINT fk_client_user_password_client_user_id
    FOREIGN KEY (client_user_id)
    REFERENCES client_user(client_user_id);

-- email_send ADD FOREIGN KEY
ALTER TABLE email_send
    ADD CONSTRAINT fk_email_send_send_user_id
    FOREIGN KEY (send_user_id)
    REFERENCES client_user(client_user_id);
