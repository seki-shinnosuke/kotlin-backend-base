USE base;
SET CHARSET utf8mb4;

-- Master table

-- Transaction table
-- access_log_management CREATE INDEX
CREATE INDEX ix_access_log_management_ip_address ON access_log_management(ip_address);
CREATE INDEX ix_access_log_management_access_datetime ON access_log_management(access_datetime);

-- email_send CREATE INDEX
CREATE INDEX ix_email_send_send_status ON email_send(send_status);
CREATE INDEX ix_email_send_send_reservation_datetime ON email_send(send_reservation_datetime);

-- System table
