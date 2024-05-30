package com.example.schoolmanagementsystem.service;

import okhttp3.*;

import java.io.IOException;

public class MpesaGenerateToken {
    OkHttpClient client = new OkHttpClient.Builder().build();
    MediaType mediaType = MediaType.parse("text/plain");
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    RequestBody body = RequestBody.create(JSON, "{}");
    Request request = new Request.Builder()
            .url("https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials")
            .get()
            .addHeader("Authorization", "Basic bm5Nd0hkdlRka21HbG1MWTI0NEtYNjk3S1pPSk5VUHJJMDhWeVpOSzBvbG1NeExrOnd3bzdFOUdlVFEzWllDMUVsOU9IVjB3c0packoyaGh1TXFrY1Z4V1pxeDMzdUo1dXAzZ2dDdTNBOFEzc3UwVXQ=")
            .addHeader("Cookie", "incap_ses_1353_2742146=P80TIk6pw1seyUwtXtLGEkqQNGYAAAAAbWoh6wdDVbx/84jwiFgTtA==; visid_incap_2742146=Cuc8d7s0RkWig+hfIC87OQbqLGYAAAAAQUIPAAAAAABydVO8KimWm744/kD+bKxR")
            .build();
    Response response;

    {
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getResponse() {
        try {
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
