package kr.hhplus.be.server.concert.interfaces.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.common.code.SuccessCode;
import kr.hhplus.be.server.common.dto.CommonResponse;
import kr.hhplus.be.server.concert.domain.dto.ConcertScheduleResult;
import kr.hhplus.be.server.concert.domain.dto.ConcertSeatResult;
import kr.hhplus.be.server.concert.domain.service.ConcertService;
import kr.hhplus.be.server.concert.interfaces.dto.response.ConcertScheduleResponse;
import kr.hhplus.be.server.concert.interfaces.dto.response.ConcertSeatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    public CommonResponse<List<ConcertScheduleResponse>> getAvailableScheduleList(@RequestParam(required = false)Long concertId){
        List<ConcertScheduleResult> concertScheduleResults = concertService.getAvailableScheduleList(concertId);
        List<ConcertScheduleResponse> responseList = new ArrayList<>();

        for (ConcertScheduleResult concertScheduleResult : concertScheduleResults) {
            ConcertScheduleResponse response = ConcertScheduleResponse.builder()
                    .id(concertScheduleResult.getId())
                    .concertId(concertScheduleResult.getConcertId())
                    .scheduleDate(concertScheduleResult.getScheduleDate())
                    .build();
            responseList.add(response);
        }
        return new CommonResponse<>(SuccessCode.SUCCESS.getCode(), SuccessCode.SUCCESS.getMessage(), responseList);
    }

    @Operation(
        summary = "예약 가능 좌석 조회",
        description = "예약 가능 좌석 조회한다."
            )
    @GetMapping("/available-seat")
    public CommonResponse<List<ConcertSeatResponse>> getAvailableSeatList(@RequestParam(required = false)Long concertScheduleId){
        List<ConcertSeatResult> concertSeatResults = concertService.getAvailableSeatList(concertScheduleId);
        List<ConcertSeatResponse> responseList = new ArrayList<>();

        for (ConcertSeatResult concertSeatResult : concertSeatResults) {
            ConcertSeatResponse response = ConcertSeatResponse.builder()
                    .concertScheduleId(concertSeatResult.getConcertScheduleId())
                    .seatNumber(concertSeatResult.getSeatNumber())
                    .price(concertSeatResult.getPrice())
                    .build();
            responseList.add(response);
        }
        return new CommonResponse<>(SuccessCode.SUCCESS.getCode(), SuccessCode.SUCCESS.getMessage(), responseList);
    }
}
