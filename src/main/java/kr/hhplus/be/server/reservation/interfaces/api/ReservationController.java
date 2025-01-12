package kr.hhplus.be.server.reservation.interfaces.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.reservation.application.ReservationFacade;
import kr.hhplus.be.server.reservation.interfaces.dto.request.ReservationTemporaryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/balance")
@Tag(name = "예약", description = "임시 예약/예약")
public class ReservationController {

    private final ReservationFacade reservationFacade;

    @Operation(
            summary = "임시 예약",
            description = "특정 좌석을 임시 예약한다."
    )
    @PostMapping("/temporary")
    public ResponseEntity<?> chargeBalance(@RequestBody ReservationTemporaryRequest request) {
        boolean isReserved = reservationFacade.reserveTemporary(request);
//        if (isReserved) {
//            return ResponseEntity.ok(
//                    CommonResponse.<Void>builder()
//                            .code(SuccessCode.SUCCESS.getCode())
//                            .message(SuccessCode.SUCCESS.getMessage())
//                            .build()
//            );
//        }else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
//                    CommonResponse.<ReservationTemporaryResponse>builder()
//                            .code(HttpStatus.BAD_REQUEST.value())
//                            .message("임시 예약에 실패했습니다.")
//                            .build()
//            );
//        }
       return null;
    }
}
