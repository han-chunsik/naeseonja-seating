-- 테이블 생성
CREATE TABLE balance (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     user_id BIGINT NOT NULL UNIQUE,
     balance BIGINT NOT NULL
);

CREATE TABLE balance_history (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     balance_id BIGINT NOT NULL,
     amount BIGINT NOT NULL,
     type ENUM('CHARGE', 'USE') NOT NULL,
     created_at TIMESTAMP NOT NULL
);

CREATE TABLE concert (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     concert_name VARCHAR(255) NOT NULL
);

CREATE TABLE concert_schedule (
      id BIGINT AUTO_INCREMENT PRIMARY KEY,
      concert_id BIGINT NOT NULL,
      schedule_date DATE NOT NULL,
      status ENUM('SCHEDULED', 'AVAILABLE', 'SOLDOUT') NOT NULL
);

CREATE TABLE seat (
      id BIGINT AUTO_INCREMENT PRIMARY KEY,
      concert_schedule_id BIGINT NOT NULL,
      seat_number INT NOT NULL,
      status ENUM('AVAILABLE', 'NOT_AVAILABLE') NOT NULL,
      price BIGINT NOT NULL
);

CREATE TABLE queue_token (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     user_id BIGINT NOT NULL,
     token VARCHAR(36) NOT NULL,
     status ENUM('WAITING', 'AVAILABLE', 'EXPIRED') NOT NULL,
     created_at TIMESTAMP NOT NULL,
     activated_at TIMESTAMP
);

CREATE TABLE reservation (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     seat_id BIGINT NOT NULL,
     user_id BIGINT NOT NULL,
     price BIGINT NOT NULL,
     status ENUM('HOLD', 'EXPIRED', 'RESERVED') NOT NULL,
     created_at TIMESTAMP NOT NULL,
     reserved_at TIMESTAMP,
     expired_at TIMESTAMP
);

-- 테스트 데이터
INSERT INTO balance (user_id, balance) VALUES (1, 0);
INSERT INTO balance (user_id, balance) VALUES (2, 1000);
INSERT INTO balance (user_id, balance) VALUES (3, 2000);