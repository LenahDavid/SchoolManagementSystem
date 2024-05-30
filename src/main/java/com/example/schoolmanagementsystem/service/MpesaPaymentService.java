package com.example.schoolmanagementsystem.service;

import com.example.schoolmanagementsystem.controllers.GenerateToken;
import com.example.schoolmanagementsystem.dto.PaymentRequest;
import okhttp3.*;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class MpesaPaymentService {

//    public String initiatePayment(String shortCode, String commandId, Long amount, String msisdn, String billRefNumber) throws IOException {
//        GenerateToken generateToken = new GenerateToken();
//        String generatedToken = generateToken.generateToken();
//        JSONObject tokenJson = new JSONObject(generatedToken);
//        String accessToken = tokenJson.getString("access_token");
//        String authorizationHeader = "Bearer " + accessToken;
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        MediaType mediaType = MediaType.parse("application/json");
//        String json = String.format("{\"ShortCode\": \"%s\",\"CommandID\": \"%s\",\"Amount\": \"%s\",\"Msisdn\": \"%s\",\"BillRefNumber\": \"%s\"}", shortCode, commandId, amount, msisdn, billRefNumber);
//
//        RequestBody body = RequestBody.create(mediaType, json);
//        Request request = new Request.Builder()
//                .url("https://sandbox.safaricom.co.ke/mpesa/c2b/v1/simulate")
//                .method("POST", body)
//                .addHeader("Authorization", authorizationHeader)
//                .addHeader("Content-Type", "application/json")
//                .addHeader("Cookie", "incap_ses_236_2742146=2D7paei1ODVn/4Q6yXBGAzHuLGYAAAAAZ2DNG/iblj+j8mOfLLg/YQ==; visid_incap_2742146=Cuc8d7s0RkWig+hfIC87OQbqLGYAAAAAQUIPAAAAAABydVO8KimWm744/kD+bKxR")
//                .build();
//
//        Response response = null;
//        try {
//            response = client.newCall(request).execute();
//            if (!response.isSuccessful()) {
//                System.err.println("Unsuccessful response: " + response.code() + " " + response.message());
//            }
//            return response.body().string();
//        } finally {
//            if (response != null && response.body() != null) {
//                response.body().close();
//            }
//        }
//    }

//    public String initiatePayment(PaymentRequest paymentRequest) {
//        try {
//            String response = initiatePayment(paymentRequest.getShortCode(), paymentRequest.getCommandId(),paymentRequest.getAmount(), paymentRequest.getMsisdn(), paymentRequest.getBillRefNumber());
//            return response;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public String stkpush(long businessShortCode, String password, Timestamp timestamp, long partyA, long partyB, long phoneNumber, String transactionType, long amount, String accountReference, String transactionDesc, URL callBackUrl) {
        GenerateToken generateToken = new GenerateToken();
        String generatedToken = generateToken.generateToken();
        JSONObject tokenJson = new JSONObject(generatedToken);
        String accessToken = tokenJson.getString("access_token");
        String authorizationHeader = "Bearer " + accessToken;

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String formattedTimestamp = formatter.format(timestamp);

        String json = String.format("{\r\n" +
                "    \"BusinessShortCode\": \"%s\",\r\n" +
                "    \"Password\": \"%s\",\r\n" +
                "    \"Timestamp\": \"%s\",\r\n" +
                "    \"TransactionType\": \"%s\",\r\n" +
                "    \"Amount\": \"%s\",\r\n" +
                "    \"PartyA\": \"%s\",\r\n" +
                "    \"PartyB\": \"%s\",\r\n" +
                "    \"PhoneNumber\": \"%s\",\r\n" +
                "    \"CallBackURL\": \"%s\",\r\n" +
                "    \"AccountReference\": \"%s\",\r\n" +
                "    \"TransactionDesc\": \"%s\"\r\n" +
                "}", businessShortCode, password, formattedTimestamp, transactionType, amount, partyA, partyB, phoneNumber, callBackUrl, accountReference, transactionDesc);
//                "}", 174379, "MTc0Mzc5YmZiMjc5ZjlhYTliZGJjZjE1OGU5N2RkNzFhNDY3Y2QyZTBjODkzMDU5YjEwZjc4ZTZiNzJhZGExZWQyYzkxOTIwMjQwNTE2MDk0NzIy", "20240516094722", "CustomerPayBillOnline", 1, "254708672521", 174379, "254708672521", "https://mydomain.com/path", "CompanyXLTD", "Payment of X" );

        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url("https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest")
                .method("POST", body)
                .addHeader("Authorization", authorizationHeader)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            
            System.out.println("Request URL: " + request.url());
            System.out.println("Request Headers: " + request.headers());
            System.out.println("Request Body: " + json);
            System.out.println("Response Code: " + response.code());

            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                System.err.println("Unsuccessful response: " + response.code() + " " + response.message());
                return "Error: " + response.code() + " " + response.message();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }


    public String initiatePayment(String shortCode, Long amount, Long msisdn, String billRefNumber, String commandId) {
        GenerateToken generateToken = new GenerateToken();
        String generatedToken = generateToken.generateToken();
        JSONObject tokenJson = new JSONObject(generatedToken);
        String accessToken = tokenJson.getString("access_token");
        String authorizationHeader = "Bearer " + accessToken;
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        String json = String.format("{\"ShortCode\": \"%s\",\"CommandID\": \"%s\",\"Amount\": \"%s\",\"Msisdn\": \"%s\",\"BillRefNumber\": \"%s\"}",
                shortCode, commandId, amount, msisdn, billRefNumber);

        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url("https://sandbox.safaricom.co.ke/mpesa/c2b/v1/simulate")
                .method("POST", body)
                .addHeader("Authorization", authorizationHeader)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cookie", "incap_ses_236_2742146=2D7paei1ODVn/4Q6yXBGAzHuLGYAAAAAZ2DNG/iblj+j8mOfLLg/YQ==; visid_incap_2742146=Cuc8d7s0RkWig+hfIC87OQbqLGYAAAAAQUIPAAAAAABydVO8KimWm744/kD+bKxR")
                .build();
        try (Response response = client.newCall(request).execute()) {
            System.out.println("Request URL: " + request.url());
            System.out.println("Request Headers: " + request.headers());
            System.out.println("Request Body: " + json);
            System.out.println("Response Code: " + response.code());

            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                System.err.println("Unsuccessful response: " + response.code() + " " + response.message());
                return "Error: " + response.code() + " " + response.message();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
    }
