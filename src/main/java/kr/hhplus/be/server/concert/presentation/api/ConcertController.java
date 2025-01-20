package kr.hhplus.be.server.concert.presentation.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import kr.hhplus.be.server.common.code.SuccessCode;
import kr.hhplus.be.server.common.dto.CommonResponse;
import kr.hhplus.be.server.concert.domain.dto.ConcertScheduleResult;
import kr.hhplus.be.server.concert.domain.dto.ConcertSeatResult;
import kr.hhplus.be.server.concert.domain.service.ConcertService;
import kr.hhplus.be.server.concert.presentation.dto.response.ConcertScheduleResponse;
import kr.hhplus.be.server.concert.presentation.dto.response.ConcertSeatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/concert")
@Tag(name = "콘서트", description = "날짜/좌석 조회")
public class ConcertController {

    private final ConcertService concertService;

    @Operation(
            summary = "예약 가능 날짜 조회",
            description = "예약 가능 날짜를 조회한다."
    )
    @GetMapping("/available-schedule")
    public CommonResponse<List<ConcertScheduleResponse>> getAvailableScheduleList(@RequestParam(required = false) @Positive(message = "콘서트 ID는 음수일 수 없습니다.") Long concertId){
        List<ConcertScheduleResult> concertScheduleResults = concertService.getAvailableScheduleList(concertId);
        List<ConcertScheduleResponse> responseList = concertScheduleResults.stream()
                .map(ConcertScheduleResponse::from)
                .collect(Collectors.toList());
        return new CommonResponse<>(SuccessCode.SUCCESS.getCode(), SuccessCode.SUCCESS.getMessage(), responseList);
    }

    @Operation(
        summary = "예약 가능 좌석 조회",
        description = "예약 가능 좌석 조회한다."
            )
    @GetMapping("/available-seat")
    public CommonResponse<List<ConcertSeatResponse>> getAvailableSeatList(@RequestParam(required = false)  @Positive(message = "스케줄 ID는 음수일 수 없습니다.") Long concertScheduleId){
        List<ConcertSeatResult> concertSeatResults = concertService.getAvailableSeatList(concertScheduleId);
        List<ConcertSeatResponse> responseList = concertSeatResults.stream()
                .map(ConcertSeatResponse::from)
                .collect(Collectors.toList());
        return new CommonResponse<>(SuccessCode.SUCCESS.getCode(), SuccessCode.SUCCESS.getMessage(), responseList);
    }
}
