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
-- 사용자 잔액 데이터 삽입
INSERT INTO balance (user_id, balance)
SELECT n, 1000
FROM
    (SELECT @n := @n + 1 AS n FROM information_schema.columns, (SELECT @n := 0) AS init LIMIT 100) AS user_data;

-- 콘서트 데이터 삽입
INSERT INTO concert (concert_name)
VALUES ('Rock Concert');

-- 콘서트 일정 데이터 삽입 (오늘 이후 날짜로 설정)
INSERT INTO concert_schedule (concert_id, schedule_date, status)
VALUES (
           (SELECT id FROM concert WHERE concert_name = 'Rock Concert'),
           CURDATE() + INTERVAL 1 DAY, -- 오늘 이후 날짜 (내일)
           'SCHEDULED'
       );

-- 좌석 데이터 삽입
-- 콘서트 일정 ID를 서브쿼리로 가져오기
INSERT INTO seat (concert_schedule_id, seat_number, status, price)
SELECT
    cs.id,
    n,
    'AVAILABLE',
    10000 -- 가격 설정
FROM
    (SELECT @n := @n + 1 AS n FROM information_schema.columns, (SELECT @n := 0) AS init LIMIT 51) AS seat_numbers,
    concert_schedule cs
WHERE cs.concert_id = (SELECT id FROM concert WHERE concert_name = 'Rock Concert')
  AND cs.schedule_date = CURDATE() + INTERVAL 1 DAY;

-- 토큰 데이터 삽입
-- 1부터 105까지 사용자에 대해 고유한 토큰을 삽입
INSERT INTO queue_token (user_id, token, status, created_at, activated_at)
SELECT
    n,
    UUID(),  -- UUID 형식으로 토큰 생성
    'AVAILABLE',  -- 상태는 'WAITING'
    NOW(),  -- 생성 시간은 현재 시간
    NULL  -- 활성화 시간은 NULL
FROM
    (SELECT @n := @n + 1 AS n FROM information_schema.columns, (SELECT @n := 0) AS init LIMIT 105) AS user_data;