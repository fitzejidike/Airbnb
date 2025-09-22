package com.example.airbnb.paystack;
import lombok.Getter;
import lombok.Setter;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Configuration
@ConfigurationProperties(prefix = "paystack")
@Setter
@Getter
public class PayStackConfig {
    private String baseUrl;
    private String secretKey;

    public String getInitiatePaymentUrl() {
        return baseUrl + "/transaction/initialize";
    }

    public String getVerifyPaymentUrl() {
        return baseUrl + "/transaction/verify/";
    }

    public String getTransferUrl() {
        return baseUrl + "/transfer";
    }

    public String getVerifyTransferUrl() {
        return baseUrl + "/transfer/verify/";
    }

    public String getBanksUrl() {
        return baseUrl + "/bank";
    }
    @Bean
    public OkHttpClient okHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(chain -> {
                    int tryCount = 0;
                    int maxRetries = 3;
                    IOException lastException = null;

                    while (tryCount < maxRetries) {
                        try {
                            return chain.proceed(chain.request());
                        } catch (IOException e) {
                            lastException = e;
                            tryCount++;
                            if (tryCount >= maxRetries) {
                                throw e;
                            }
                        }
                    }
                    throw lastException;
                })
                .retryOnConnectionFailure(true)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
    }

}