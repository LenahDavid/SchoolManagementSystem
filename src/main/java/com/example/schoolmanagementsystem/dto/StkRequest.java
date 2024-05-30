package com.example.schoolmanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;


@Data
public class StkRequest implements Serializable {
    @JsonProperty("BusinessShortCode")
    private Long businessShortCode; // Numeric
    @JsonProperty("Password")
    private String password; // String
    @JsonProperty("Timestamp")
    private String timestamp;
    public StkRequest() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        this.timestamp = formatter.format(new Date());
    }
    @JsonProperty("TransactionType")
    private String transactionType; // String
    @JsonProperty("Amount")
    private Long amount; // Numeric
    @JsonProperty("PartyA")
    private Long partyA; // Numeric
    @JsonProperty("PartyB")
    private Long partyB; // Numeric
    @JsonProperty("PhoneNumber")
    private Long phoneNumber; // Numeric
    @JsonProperty("CallBackURL")
    private String callBackUrl; // URL
    @JsonProperty("AccountReference")
    private String accountReference; // Alpha-Numeric
    @JsonProperty("TransactionDesc")
    private String transactionDesc; // String

}
