import sql from 'k6/x/sql';
import driver from "k6/x/sql/driver/mysql";
const db = sql.open(driver, 'application:application@tcp(localhost:3306)/hhplus');

export let options = {
    vus: 100,
    duration: '1m'
};


export default function () {
    let userId = Math.floor(Math.random() * 1000);

    // 1. 기존 대기열 토큰 존재 여부 확인
    let result = db.query(`SELECT id, token, status FROM queue_token WHERE user_id = ? AND status != 'EXPIRED'LIMIT 1 FOR UPDATE`, userId);

    if (result.length > 0) {
        let queueTokenId = result[0].id;

        // 2. 기존 토큰 만료
        db.exec(`UPDATE queue_token SET status = 'EXPIRED' WHERE id = ?`, queueTokenId);
    }

    // 3. 토큰 발급
    let newToken = `TOKEN_${userId}_${Date.now()}`;

    // 4. 토큰 저장
    db.exec(`INSERT INTO queue_token (user_id, token, status, created_at, activated_at)VALUES (?, ?, 'AVAILABLE', NOW(), NOW())`, userId, newToken);
}

export function teardown() {
    db.close();
}