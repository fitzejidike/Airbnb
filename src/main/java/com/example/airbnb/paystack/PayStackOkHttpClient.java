package com.example.airbnb.paystack;
import com.example.airbnb.exception.PayStackException;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class PayStackOkHttpClient implements PayStackHttpClient {

    private final OkHttpClient client;

    @Override
    public String post(String url, RequestBody body) {
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return execute(request);
    }

    @Override
    public String get(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        return execute(request);
    }

    private String execute(Request request) {
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new PayStackException("HTTP Error: " + response.code() + " - " + response.message());
            }
            return response.body() != null ? response.body().string() : "{}";
        } catch (IOException e) {
            throw new PayStackException("IO error calling Paystack API", e);
        }
    }
}

