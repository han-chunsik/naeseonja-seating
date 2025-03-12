package kr.naeseonja.be.server.concert.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConcertRequest {
    @NotNull(message = "콘서트 이름은 빈 값일 수 없습니다.")
    private String concertName;

    public ConcertRequest(String concertName) {
        this.concertName = concertName;
    }
}
