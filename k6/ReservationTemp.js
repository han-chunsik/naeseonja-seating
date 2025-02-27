import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
    vus: 100, // VUs 수 지정
    duration: '1m', // 테스트 지속 시간
};

const BASE_URL = 'http://host.docker.internal:8080/api/v1';
const concertScheduleIds = [1]; // 임의의 일정 ID 리스트
const seatStart = 1; // 시작 좌석 번호
const seatEnd = 50; // 마지막 좌석 번호
const concertId = 1;

export default function () {
    let userId = __VU; // VU 번호를 userId로 사용

    // 1. 대기열 토큰 발급
    let queueTokenRes = http.post(`${BASE_URL}/queue/token`, JSON.stringify({ userId }), {
        headers: { 'Content-Type': 'application/json' },
    });
    check(queueTokenRes, { '대기열 토큰 발급 성공': (res) => res.status === 200 });
    let queueTokenJson = queueTokenRes.json();
    let queueToken = queueTokenJson.data.token;

    sleep(1);

    // 2. 대기열 상태 확인
    let isAvailable = false;
    while (!isAvailable) {
        let positionRes = http.get(`${BASE_URL}/queue/token/position`, {
            headers: { 'Authorization': `Bearer ${queueToken}` },
        });
        check(positionRes, { '대기열 조회 성공': (res) => res.status === 200 });

        let positionJson = positionRes.json();
        isAvailable = positionJson && positionJson.data.available ? positionJson.data.available : false;

        if (!isAvailable) {
            sleep(1); // 1초 대기 후 재요청
        }
    }

    sleep(1);
    // 3. 예약 가능 일정 조회
    let scheduleRes = http.get(`${BASE_URL}/concert/available-schedule?concertId=${concertId}`,{
        headers: { 'Authorization': `Bearer ${queueToken}` },
    });
    check(scheduleRes, { '예약 일정 조회 성공': (res) => res.status === 200 });

    sleep(1);
    // 4. 예약 가능 좌석 조회
    let scheduleId = concertScheduleIds[Math.floor(Math.random() * concertScheduleIds.length)];
    let seatRes = http.get(`${BASE_URL}/concert/available-seat?concertScheduleId=${scheduleId}`,{
        headers: { 'Authorization': `Bearer ${queueToken}` },
    });
    check(seatRes, { '예약 좌석 조회 성공': (res) => res.status === 200 });
    let seatId = Math.floor(Math.random() * (seatEnd - seatStart + 1)) + seatStart;

    sleep(1);
    // 5. 임시 예약 요청
    let reservationRes = http.post(`${BASE_URL}/reservation/temporary`, JSON.stringify({ seatId, userId }), {
        headers: { 'Authorization': `Bearer ${queueToken}`, 'Content-Type': 'application/json' },
    });
    check(reservationRes, { '임시 예약 성공': (res) => res.status === 200 });
}