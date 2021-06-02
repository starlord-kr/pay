package com.starlord.payment.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@NoArgsConstructor
public enum ExceptionMessage {
    VAT_GREATER_THAN_PRICE("부가가치세는 결제금액보다 클 수 없습니다."),
    PAYMENT_NOT_FOUND_EXCEPTION("결제내역이 없습니다."),
    PAYMENT_ALREADY_CANCELD_EXCEPTION("이미 취소 완료 되었습니다."),
    PAYMENT_CANCEL_PRICE_OVER_EXCEPTION("취소요청 금액이 더 큽니다."),
    PAYMENT_CANCEL_VAT_OVER_EXCEPTION("취소요청 VAT가 더 큽니다."),
    PAYMENT_CANCEL_VAT_REMAINED_EXCEPTION("취소할 VAT가 남았습니다."),
    ;

    private String message;
    ExceptionMessage(String message) {
        this.message = message;
    }
}
