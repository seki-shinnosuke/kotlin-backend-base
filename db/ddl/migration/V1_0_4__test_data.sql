USE base;
SET CHARSET utf8mb4;

-- client_user
-- client_user_password
-- ID: base / Pass: Qazwsx1234
INSERT INTO client_user (client_user_id, client_user_login_id, user_name, email_address, authority, user_status, create_by, create_at, update_by, update_at) VALUES (1, 'base', 'テストアカウント', 'sample@example.com', 'ADMINISTRATOR', 'ACTIVE', 'SYSTEM', '2024-01-01 00:00:00', 'SYSTEM', '2024-01-01 00:00:00');
INSERT INTO client_user_password (client_user_id, password, password_status, create_by, create_at, update_by, update_at) VALUES (1, '$2a$10$zZX/nf04R1NmprZTcGJTguiWzMeYR89It2vixGxzwWUQ7mp..fKAK', 'CONFIGURED', 'SYSTEM', '2024-01-01 00:00:00', 'SYSTEM', '2024-01-01 00:00:00');
