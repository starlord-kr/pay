package com.starlord.payment.api;

import com.starlord.payment.api.model.*;
import com.starlord.payment.domain.payment.application.PaymentService;
import com.starlord.payment.domain.payment.model.PaymentResultDto;
import com.starlord.payment.domain.payment.model.PaymentSearchResultDto;
import com.starlord.payment.exception.PaymentException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.GeneralSecurityException;

@Api(value = "/v1/payments")
@ApiResponses(value = {
        @ApiResponse(code = 400, message = "Wrong Type Parameter"),
        @ApiResponse(code = 404, message = "Does not exists sellers"),
        @ApiResponse(code = 500, message = "Server Error")})
@Slf4j
@RequestMapping("/v1/payments")
@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @ApiOperation(value = "결제 승인 API")
    @PostMapping("/approve")
    public PaymentResponse approval(@Valid @RequestBody ApproveRequest approveRequest) throws PaymentException {

        PaymentResultDto paymentResultDto = paymentService.approvePayment(approveRequest);
        return PaymentResponse.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(ApproveResponse.builder()
                        .paymentSerialNumber(paymentResultDto.getPaymentSerialNumber())
                        .datayPayload(paymentResultDto.getDataPayload())
                        .build())
                .build();
    }

    @ApiOperation(value = "결제 취소 API")
    @PostMapping("/cancel")
    public PaymentResponse cancel(@Valid @RequestBody CancelRequest cancelRequest)
            throws PaymentException, GeneralSecurityException {

        PaymentResultDto paymentResultDto = paymentService.cancelPayment(cancelRequest);
        return PaymentResponse.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(CancelResponse.builder()
                        .paymentSerialNumber(paymentResultDto.getPaymentSerialNumber())
                        .datayPayload(paymentResultDto.getDataPayload())
                        .build())
                .build();
    }

    @ApiOperation(value = "결제 조회 API")
    @GetMapping()
    public PaymentResponse getPaymentList(@RequestParam("id") String paymentSerialNumber) {
        PaymentSearchResultDto resultDto = paymentService.findOnePayment(paymentSerialNumber);
        return PaymentResponse.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(resultDto)
                .build();
    }
}
