import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
    vus: 100, // VUs 수 지정
    duration: '1m', // 테스트 지속 시간
};

const BASE_URL = 'http://host.docker.internal:8080/api/v1';

export default function () {
    let userId = __VU; // VU 번호를 userId로 사용
    let amount = 200;

    // 1. 포인트 충전
    let chargePointRes = http.post(`${BASE_URL}/balance/charge`, JSON.stringify({ userId , amount}), {
        headers: { 'Content-Type': 'application/json' },
    });
    check(chargePointRes, { '포인트 충전 성공': (res) => res.status === 200 });

    sleep(1)

    // 2. 포인트 조회
    let getPointRes = http.get(`${BASE_URL}/balance/${userId}`, {
        headers: { 'Content-Type': 'application/json' },
    });
    check(getPointRes, { '포인트 조회 성공': (res) => res.status === 200 });
}