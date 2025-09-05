package com.example.airbnb.paystack;


import com.example.airbnb.dtos.request.PaymentRequest;
import com.example.airbnb.dtos.request.TransferRequest;
import com.example.airbnb.dtos.responses.PayStackData;
import com.example.airbnb.dtos.responses.PaymentResponse;
import com.example.airbnb.exception.PayStackException;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class PayStackServiceImpl implements PayStackService {

    private final PayStackConfig payStackConfig;
    private final PayStackHttpClient httpClient;

    @Override
    @Retry(name = "paystack", fallbackMethod = "fallbackPayment")
    @CircuitBreaker(name = "paystack", fallbackMethod = "fallbackPayment")
    public PaymentResponse initiatePayment(PaymentRequest paymentRequest) {
        if (paymentRequest.getAmount() <= 0 || paymentRequest.getEmail() == null) {
            throw new IllegalArgumentException("Invalid payment request");
        }

        RequestBody body = new FormBody.Builder()
                .add("amount", String.valueOf(paymentRequest.getAmount()))
                .add("email", paymentRequest.getEmail())
                .build();

        String response = httpClient.post(payStackConfig.getInitiatePaymentUrl(), body);
        return parsePaymentResponse(response, true);
    }


    @Override
    @Retry(name = "paystack", fallbackMethod = "fallbackVerifyPayment")
    @CircuitBreaker(name = "paystack", fallbackMethod = "fallbackVerifyPayment")
    public PaymentResponse verifyPayment(String reference) {
        String response = httpClient.get(payStackConfig.getVerifyPaymentUrl() + reference);
        return parsePaymentResponse(response, true);
    }


    @Override
    @Retry(name = "paystack", fallbackMethod = "fallbackTransfer")
    @CircuitBreaker(name = "paystack", fallbackMethod = "fallbackTransfer")
    public PaymentResponse initiateTransfer(TransferRequest transferRequest) {
        RequestBody body = new FormBody.Builder()
                .add("source", "balance")
                .add("amount", String.valueOf(transferRequest.getAmount()))
                .add("recipient", transferRequest.getRecipientCode())
                .add("reason", transferRequest.getReason())
                .build();

        String response = httpClient.post(payStackConfig.getTransferUrl(), body);
        return parsePaymentResponse(response, false);
    }


    @Override
    @Retry(name = "paystack", fallbackMethod = "fallbackVerifyTransfer")
    @CircuitBreaker(name = "paystack", fallbackMethod = "fallbackVerifyTransfer")
    public PaymentResponse verifyTransfer(String reference) {
        String response = httpClient.get(payStackConfig.getVerifyTransferUrl() + reference);
        return parsePaymentResponse(response, false);
    }


    @Override
    public String getBanks() {
        return httpClient.get(payStackConfig.getBanksUrl());
    }


    public PaymentResponse fallbackPayment(PaymentRequest request, Throwable t) {
        return new PaymentResponse(false,
                "Fallback: Could not initiate payment. Reason: " + t.getMessage(),
                null);
    }

    public PaymentResponse fallbackVerifyPayment(String reference, Throwable t) {
        return new PaymentResponse(false,
                "Fallback: Could not verify payment for ref=" + reference + ". Reason: " + t.getMessage(),
                null);
    }

    public PaymentResponse fallbackTransfer(TransferRequest transferRequest, Throwable t) {
        return new PaymentResponse(false,
                "Fallback: Could not initiate transfer. Reason: " + t.getMessage(),
                null);
    }

    public PaymentResponse fallbackVerifyTransfer(String reference, Throwable t) {
        return new PaymentResponse(false,
                "Fallback: Could not verify transfer for ref=" + reference + ". Reason: " + t.getMessage(),
                null);
    }

    
    private PaymentResponse parsePaymentResponse(String jsonResponse, boolean isPayment) {
        JSONObject json = new JSONObject(jsonResponse);

        boolean status = json.getBoolean("status");
        String message = json.getString("message");
        JSONObject data = json.optJSONObject("data");

        if (data == null) {
            throw new PayStackException("Missing 'data' in Paystack response");
        }

        PayStackData paystackData = new PayStackData();
        if (isPayment) {
            paystackData.setAuthorizationUrl(data.optString("authorization_url", null));
            paystackData.setAccessCode(data.optString("access_code", null));
        } else {
            paystackData.setTransferCode(data.optString("transfer_code", null));
        }
        paystackData.setReference(data.optString("reference", null));

        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setStatus(status);
        paymentResponse.setMessage(message);
        paymentResponse.setData(paystackData);

        return paymentResponse;
    }
}
