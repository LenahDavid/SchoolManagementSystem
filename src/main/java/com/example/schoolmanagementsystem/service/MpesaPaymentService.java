package com.example.schoolmanagementsystem.service;

import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MpesaService {

    public String simulatePayBill(int shortCode, int amount, String msisdn, String billRefNumber) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        String json = String.format("{\"ShortCode\": %d,\"CommandID\": \"CustomerPayBillOnline\",\"Amount\": %d,\"Msisdn\": \"%s\",\"BillRefNumber\": \"%s\"}",
                shortCode, amount, msisdn, billRefNumber);
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url("https://sandbox.safaricom.co.ke/mpesa/c2b/v1/simulate")
                .method("POST", body)
                .addHeader("Authorization", "Bearer 19o3CnE5KGWLPrGtb4RhXZ0QCJr7")
                .addHeader("Content-Type", "application/json")
                .addHeader("Cookie", "incap_ses_236_2742146=2D7paei1ODVn/4Q6yXBGAzHuLGYAAAAAZ2DNG/iblj+j8mOfLLg/YQ==; visid_incap_2742146=Cuc8d7s0RkWig+hfIC87OQbqLGYAAAAAQUIPAAAAAABydVO8KimWm744/kD+bKxR")
                .build();
        Response response = client.newCall(request).execute();

        // Handle the response
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }

        // Return the response body
        return response.body().string();
    }
}
