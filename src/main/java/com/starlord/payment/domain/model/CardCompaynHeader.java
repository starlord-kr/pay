package com.starlord.payment.domain.model;

import com.starlord.payment.utils.StringUtilsWrapper;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CardCompaynHeader {

    @Builder
    public CardCompaynHeader(String dataLength,
                             String dataType,
                             String paymentSerialNumber) {

        // 공통헤더
        this.dataLength = StringUtilsWrapper.leftPaddingWithBlank(dataLength, 4);
        this.dataType = StringUtilsWrapper.rightPaddingWithBlank(dataType, 10);
        this.paymentSerialNumber = StringUtilsWrapper.rightPaddingWithBlank(paymentSerialNumber, 20);
    }

    private String dataLength; // 데이터길이
    private String dataType; // 데이터구분
    private String paymentSerialNumber; // 관리번호

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(dataLength);
        stringBuilder.append(dataType);
        stringBuilder.append(paymentSerialNumber);
        return stringBuilder.toString();
    }
}
