# 좌석 예약 서비스

## Getting Started
### Prerequisites
#### Running Docker Containers
`local` profile 로 실행하기 위하여 인프라가 설정되어 있는 Docker 컨테이너를 실행해주셔야 합니다.

```bash
docker-compose up -d
```

```
docker-compose -f ./docker-compose-redis.yml up -d
docker-compose -f ./docker-compose-kafka.yml up -d
```

---
## Docs
### [요구사항 분석 및 설계](https://github.com/han-chunsik/naeseonja-seating/wiki/%EC%84%A4%EA%B3%84-%7C-%EC%9A%94%EA%B5%AC%EC%82%AC%ED%95%AD-%EC%A0%95%EC%9D%98-%EB%B0%8F-%EC%84%A4%EA%B3%84)  
### [동시성 제어 기법(Lock) 비교 분석 및 구현](https://github.com/han-chunsik/naeseonja-seating/wiki/%EB%8F%99%EC%8B%9C%EC%84%B1-%EC%A0%9C%EC%96%B4-%EA%B8%B0%EB%B2%95(Lock)-%EB%B9%84%EA%B5%90-%EB%B6%84%EC%84%9D-%EB%B0%8F-%EA%B5%AC%ED%98%84)  
### [API 성능 테스트 및 개선](https://github.com/han-chunsik/naeseonja-seating/wiki/%5B%EC%84%B1%EB%8A%A5-%EA%B0%9C%EC%84%A0%5D-%EC%A2%8C%EC%84%9D-%EC%98%88%EC%95%BD-%EC%8B%9C%EC%8A%A4%ED%85%9C-API-%EC%84%B1%EB%8A%A5-%ED%85%8C%EC%8A%A4%ED%8A%B8-%EA%B0%9C%EC%84%A0-%EB%B3%B4%EA%B3%A0%EC%84%9C)  