package com.starlord.payment.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class ApproveResponse {

    private String paymentSerialNumber;
    private String datayPayload;

}
