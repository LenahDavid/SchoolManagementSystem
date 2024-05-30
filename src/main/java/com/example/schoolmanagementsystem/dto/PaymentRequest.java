package com.example.schoolmanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
@Data
public class PaymentRequest implements Serializable {
    private Long studentId;
    @JsonProperty("Amount")
    private Long amount;
    private String status;
    private String paymentMethod;
    private String invoiceId;
    @JsonProperty("ShortCode")
   private String shortCode;
    @JsonProperty("Msisdn")
    private Long msisdn;
    @JsonProperty("BillRefNumber")
private String billRefNumber;
    @JsonProperty("CommandID")
private String commandId;
private String customerPayBillOnline;
}
