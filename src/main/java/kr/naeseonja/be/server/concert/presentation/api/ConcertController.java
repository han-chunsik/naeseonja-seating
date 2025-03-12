package kr.naeseonja.be.server.concert.presentation.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import kr.naeseonja.be.server.common.code.SuccessCode;
import kr.naeseonja.be.server.common.dto.CommonResponse;
import kr.naeseonja.be.server.concert.domain.dto.ConcertResult;
import kr.naeseonja.be.server.concert.domain.dto.ConcertScheduleResult;
import kr.naeseonja.be.server.concert.domain.dto.ConcertSeatResult;
import kr.naeseonja.be.server.concert.domain.service.ConcertService;
import kr.naeseonja.be.server.concert.presentation.dto.request.ConcertRequest;
import kr.naeseonja.be.server.concert.presentation.dto.response.ConcertResponse;
import kr.naeseonja.be.server.concert.presentation.dto.response.ConcertScheduleResponse;
import kr.naeseonja.be.server.concert.presentation.dto.response.ConcertSeatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/concert")
@Tag(name = "콘서트", description = "날짜/좌석 조회")
public class ConcertController {

    private final ConcertService concertService;

    @Operation(
            summary = "콘서트 등록",
            description = "콘서트를 등록한다."
    )
    @PostMapping("")
    public CommonResponse<ConcertResponse> createConcert(@RequestBody ConcertRequest request){
        String concertName = request.getConcertName();
        ConcertResult concertResult = concertService.createConcert(concertName);
        ConcertResponse concertResponse = ConcertResponse.from(concertResult);
        return new CommonResponse<>(SuccessCode.SUCCESS.getCode(), SuccessCode.SUCCESS.getMessage(), concertResponse);
    }

    @Operation(
            summary = "콘서트 조회",
            description = "콘서트를 조회한다."
    )
    @GetMapping("{concertId}")
    public CommonResponse<ConcertResponse> getConcert(@RequestParam(required = false) @Positive(message = "콘서트 ID는 음수일 수 없습니다.") @PathVariable Long concertId){
        ConcertResult concertResult = concertService.getConcert(concertId);
        ConcertResponse concertResponse = ConcertResponse.from(concertResult);
        return new CommonResponse<>(SuccessCode.SUCCESS.getCode(), SuccessCode.SUCCESS.getMessage(), concertResponse);
    }

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
