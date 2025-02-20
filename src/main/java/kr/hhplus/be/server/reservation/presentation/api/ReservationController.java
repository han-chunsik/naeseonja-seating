package kr.hhplus.be.server.reservation.presentation.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.common.dto.CommonResponse;
import kr.hhplus.be.server.reservation.application.dto.ReservationResult;
import kr.hhplus.be.server.reservation.application.dto.ReservationTemporaryResult;
import kr.hhplus.be.server.reservation.application.service.ReservationConfirm;
import kr.hhplus.be.server.reservation.application.service.ReservationTemporary;
import kr.hhplus.be.server.reservation.presentation.dto.request.ReservationRequest;
import kr.hhplus.be.server.reservation.presentation.dto.request.ReservationTemporaryRequest;
import kr.hhplus.be.server.reservation.presentation.dto.response.ReservationResponse;
import kr.hhplus.be.server.reservation.presentation.dto.response.ReservationTemporaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/reservation")
@Tag(name = "예약", description = "임시 예약/예약")
public class ReservationController {

    private final ReservationTemporary reservationTemporary;
    private final ReservationConfirm reservationConfirm;

    @Operation(
            summary = "임시 예약",
            description = "콘서트를 임시 예약한다."
    )
    @PostMapping("/temporary")
    public CommonResponse<ReservationTemporaryResponse> temporary(@RequestBody ReservationTemporaryRequest request) {
        Long seatId = request.getSeatId();
        Long userId = request.getUserId();

        ReservationTemporaryResult reservationTemporaryResult = reservationTemporary.reserveTemporary(seatId, userId);
        ReservationTemporaryResponse response = ReservationTemporaryResponse.from(reservationTemporaryResult);
        return new CommonResponse<ReservationTemporaryResponse>().success(response);
    }

    @Operation(
            summary = "예약 확정",
            description = "임시예약을 확정한다."
    )
    @PostMapping("/confirm")
    public CommonResponse<ReservationResponse> confirm(@RequestBody ReservationRequest request) {
        Long userId = request.getUserId();
        Long reservationId = request.getReservationId();

        ReservationResult reservationResult = reservationConfirm.reserveConfirm(userId, reservationId);
        ReservationResponse response = ReservationResponse.from(reservationResult);
        return new CommonResponse<ReservationResponse>().success(response);
    }
}
