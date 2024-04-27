USE base;
SET CHARSET utf8mb4;

-- Master table

-- Transaction table
-- client_user
CREATE TABLE client_user (
    client_user_id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'クライアントユーザーID',
    client_user_login_id VARCHAR(50) NOT NULL COMMENT 'クライアントユーザーログインID',
    user_name VARCHAR(50) NOT NULL COMMENT 'ユーザー名',
    email_address VARCHAR(319) COMMENT 'メールアドレス',
    authority ENUM('USER', 'ADMINISTRATOR') NOT NULL DEFAULT 'USER' COMMENT '権限(ユーザー/管理者',
    user_status ENUM('ACTIVE', 'INVITED', 'INACTIVE') NOT NULL DEFAULT 'ACTIVE' COMMENT 'ユーザー状態(アクティブ/招待済/非アクティブ)',
    last_login_datetime DATETIME COMMENT '最終ログイン日時',
    registration_user_id BIGINT COMMENT '登録者ID',
    registration_datetime DATETIME COMMENT '登録日時',
    update_user_id BIGINT COMMENT '更新者ID',
    update_datetime DATETIME COMMENT '更新日時',
    create_by VARCHAR(64) COMMENT 'レコード作成者',
    create_at DATETIME COMMENT 'レコード作成日時',
    update_by VARCHAR(64) COMMENT 'レコード更新者',
    update_at DATETIME COMMENT 'レコード更新日時',
    PRIMARY KEY(client_user_id),
    UNIQUE un_client_user_client_user_login_id_key (client_user_login_id)
) COMMENT = 'クライアントユーザー';

-- client_user_password
CREATE TABLE client_user_password (
    client_user_id BIGINT NOT NULL COMMENT 'クライアントユーザーID',
    password VARCHAR(1024) COMMENT 'パスワード',
    password_status ENUM('TEMPORARY', 'CONFIGURED') NOT NULL DEFAULT 'TEMPORARY' COMMENT 'パスワード状態(仮状態/設定済)',
    temporary_password VARCHAR(255) COMMENT '仮パスワード',
    temporary_password_expiry_datetime DATETIME COMMENT '仮パスワード有効期限日時',
    create_by VARCHAR(64) COMMENT 'レコード作成者',
    create_at DATETIME COMMENT 'レコード作成日時',
    update_by VARCHAR(64) COMMENT 'レコード更新者',
    update_at DATETIME COMMENT 'レコード更新日時',
    PRIMARY KEY(client_user_id)
) COMMENT = 'クライアントユーザーパスワード';

-- access_log_management
CREATE TABLE access_log_management
(
    access_log_id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'アクセスログID',
    ip_address VARCHAR(32) NOT NULL COMMENT 'IPアドレス',
    access_type VARCHAR(128) NOT NULL COMMENT 'アクセス種別',
    access_datetime DATETIME NOT NULL COMMENT 'アクセス日時',
    user_agent VARCHAR(512) NOT NULL COMMENT 'ユーザーエージェント',
    access_limit_datetime DATETIME COMMENT 'アクセス制限日時',
    create_by VARCHAR(64) COMMENT 'レコード作成者',
    create_at DATETIME COMMENT 'レコード作成日時',
    update_by VARCHAR(64) COMMENT 'レコード更新者',
    update_at DATETIME COMMENT 'レコード更新日時',
    PRIMARY KEY(access_log_id)
) COMMENT='アクセスログ管理';

-- email_send
CREATE TABLE email_send (
    email_send_id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'メール送信ID',
    send_user_id BIGINT COMMENT '送信先ユーザーID',
    send_email_address VARCHAR(319) NOT NULL COMMENT '送信先メールアドレス',
    email_title VARCHAR(100) NOT NULL COMMENT 'メール件名',
    email_text TEXT NOT NULL COMMENT 'メール本文',
    send_reservation_datetime DATETIME NOT NULL COMMENT '送信予約日時',
    send_completed_datetime DATETIME COMMENT '送信完了日時',
    send_status ENUM('RESERVED', 'COMPLETED', 'NG', 'SUPPRESSED') NOT NULL COMMENT '送信状態(送信予約済/送信完了済/送信失敗/抑止中)',
    create_by VARCHAR(64) COMMENT 'レコード作成者',
    create_at DATETIME COMMENT 'レコード作成日時',
    update_by VARCHAR(64) COMMENT 'レコード更新者',
    update_at DATETIME COMMENT 'レコード更新日時',
    PRIMARY KEY(email_send_id)
) COMMENT = 'メール送信';

-- System table
-- batch_start_control
CREATE TABLE batch_start_control (
    batch_id VARCHAR(64) NOT NULL COMMENT 'バッチID',
    start_datetime DATETIME NOT NULL COMMENT '起動日時',
    create_by VARCHAR(64) COMMENT 'レコード作成者',
    create_at DATETIME COMMENT 'レコード作成日時',
    update_by VARCHAR(64) COMMENT 'レコード更新者',
    update_at DATETIME COMMENT 'レコード更新日時',
    PRIMARY KEY(batch_id)
) COMMENT='バッチ起動制御';

-- virtual_datetime_management
CREATE TABLE virtual_datetime_management (
    virtual_datetime DATETIME NOT NULL COMMENT '仮想日時',
    registration_datetime DATETIME NOT NULL COMMENT '登録日時',
    PRIMARY KEY(virtual_datetime)
) COMMENT='仮想日時管理';
