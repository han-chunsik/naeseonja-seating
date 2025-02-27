import sql from 'k6/x/sql';
import driver from "k6/x/sql/driver/mysql";
const db = sql.open(driver, 'application:application@tcp(localhost:3306)/hhplus');

export let options = {
    vus: 100,
    duration: '1m'
};


export default function () {
    let userId = __VU;
    let chargeAmount = 200;

    // 1. 포인트 조회
    let result = db.query('SELECT id, balance FROM balance WHERE user_id = ? FOR UPDATE', userId)
    let balanceId = result[0].id;
    let newBalance = result[0].balance + chargeAmount;

    // 2. 포인트 충전
    db.exec('UPDATE balance SET balance = ? WHERE user_id = ?', newBalance, userId);

    // 3. 포인트 이력 저장
    db.exec('INSERT INTO balance_history (balance_id, amount, type, created_at) VALUES (?, ?, "CHARGE", NOW())', balanceId, chargeAmount);
}

export function teardown() {
    db.close();
}