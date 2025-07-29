//package com.example.airbnb.paystack;
//
//import com.example.airbnb.config.PayStackConfig;
//import com.example.airbnb.dtos.request.PaymentRequest;
//import com.example.airbnb.dtos.request.TransferRequest;
//import com.example.airbnb.dtos.responses.PayStackData;
//import com.example.airbnb.dtos.responses.PaymentResponse;
//import com.nimbusds.oauth2.sdk.Response;
//import net.minidev.json.JSONObject;
//import okhttp3.FormBody;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//
//
//import java.io.IOException;
//
//public class PayStackServiceImpl implements PayStackService {
//    private  final PayStackConfig payStackConfig;
//    private final OkHttpClient client = new OkHttpClient();
//
//    public PayStackServiceImpl(PayStackConfig payStackConfig) {
//        this.payStackConfig = payStackConfig;
//    }
//    @Override
//    public PaymentResponse initiatePayment(PaymentRequest paymentRequest) {
//        // Validate request
//        if (paymentRequest.getAmount() <= 0 || paymentRequest.getEmail() == null) {
//            throw new IllegalArgumentException("Invalid payment request");
//        }
//
//        // Build request body
//        RequestBody requestBody = new FormBody.Builder()
//                .add("amount", String.valueOf(paymentRequest.getAmount()))
//                .add("email", paymentRequest.getEmail())
//                .build();
//
//        // Build HTTP request
//        Request request = new Request.Builder()
//                .url(payStackConfig.getPayStackInitiatePaymentUrl())
//                .header("Authorization", "Bearer " + payStackConfig.getPayStackSecretKey())
//                .post(requestBody)
//                .build();
//
//        // Execute request
//        try (Response response = client.newCall(request).execute()) {
//            if (!response.isSuccessful()) {
//                throw new RuntimeException("HTTP Error: " + response.code() + " - " + response.message());
//            }
//
//            String jsonResponse = response.body() != null ? response.body().string() : "";
//            JSONObject jsonObject = new JSONObject(jsonResponse);
//
//            // Parse response
//            boolean status = jsonObject.getBoolean("status");
//            String message = jsonObject.getString("message");
//            JSONObject data = jsonObject.optJSONObject("data");
//
//            if (data == null) {
//                throw new JSONException("Missing 'data' in response");
//            }
//
//            PayStackData paystackData = new PayStackData();
//            paystackData.setAuthorizationUrl(data.getString("authorization_url"));
//            paystackData.setAccessCode(data.getString("access_code"));
//            paystackData.setReference(data.getString("reference"));
//
//            PaymentResponse paymentResponse = new PaymentResponse();
//            paymentResponse.setStatus(status);
//            paymentResponse.setMessage(message);
//            paymentResponse.setData(paystackData);
//
//            return paymentResponse;
//        } catch (IOException e) {
//            throw new RuntimeException("IO Error during payment initiation", e);
//        } catch (JSONException e) {
//            throw new RuntimeException("JSON Parsing Error", e);
//        }
//    }
//
//
//    @Override
//    public PaymentResponse verifyPayment(String reference) {
//        Request request = new Request.Builder()
//                .url(payStackConfig.getPayStackVerifyPaymentUrl() + reference)
//                .header("Authorization", "Bearer "
//                        + payStackConfig.getPayStackSecretKey())
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            String jsonResponse = response.body().string();
//            JSONObject jsonObject = new JSONObject(jsonResponse);
//
//            boolean status = jsonObject.getBoolean("status");
//            String message = jsonObject.getString("message");
//            JSONObject data = jsonObject.getJSONObject("data");
//
//            PayStackData paystackData = new PayStackData();
//            paystackData.setAuthorizationUrl(data.getString("authorization_url"));
//            paystackData.setAccessCode(data.getString("access_code"));
//            paystackData.setReference(data.getString("reference"));
//
//            PaymentResponse paymentResponse = new PaymentResponse();
//            paymentResponse.setStatus(status);
//            paymentResponse.setMessage(message);
//            paymentResponse.setData(paystackData);
//
//            return paymentResponse;
//        } catch (IOException | JSONException e) {
//            throw new RuntimeException("Error verifying payment", e);
//        }
//    }
//
//    @Override
//    public PaymentResponse initiateTransfer(TransferRequest transferRequest) {
//        return null;
//    }
//
//    @Override
//    public PaymentResponse initiateTransfer(TransferRequest transferRequest) {
//            RequestBody requestBody = new FormBody.Builder()
//                    .add("source", "balance")
//                    .add("amount", String.valueOf(transferRequest.getAmount()))
//                    .add("recipient", transferRequest.getRecipientCode())
//                    .add("reason", transferRequest.getReason())
//                    .build();
//
//            Request request = new Request.Builder()
//                    .url(payStackConfig.getTransferUrl())
//                    .header("Authorization", "Bearer "
//                            + payStackConfig.getPayStackSecretKey())
//                    .post(requestBody)
//                    .build();
//
//            try (Response response = client.newCall(request).execute()) {
//                String jsonResponse = response.body().string();
//                JSONObject jsonObject = new JSONObject(jsonResponse);
//
//                boolean status = jsonObject.getBoolean("status");
//                String message = jsonObject.getString("message");
//                JSONObject data = jsonObject.getJSONObject("data");
//
//                PayStackData paystackData = new PayStackData();
//                paystackData.setTransferCode(data.getString("transfer_code"));
//                paystackData.setReference(data.getString("reference"));
//
//                PaymentResponse paymentResponse = new PaymentResponse();
//                paymentResponse.setStatus(status);
//                paymentResponse.setMessage(message);
//                paymentResponse.setData(paystackData);
//
//                return paymentResponse;
//            } catch (IOException | JSONException e) {
//                throw new RuntimeException("Error initiating transfer", e);
//            }
//    }
//
//    @Override
//    public PaymentResponse verifyTransfer(String reference) {
//        Request request = new Request.Builder()
//                .url(payStackConfig.getPayStackVerifyPaymentUrl() + reference)
//                .header("Authorization", "Bearer "
//                        + payStackConfig.getPayStackSecretKey())
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            String jsonResponse = response.body().string();
//            JSONObject jsonObject = new JSONObject(jsonResponse);
//
//            boolean status = jsonObject.getBoolean("status");
//            String message = jsonObject.getString("message");
//            JSONObject data = jsonObject.getJSONObject("data");
//
//            PayStackData paystackData = new PayStackData();
//            paystackData.setTransferCode(data.getString("transfer_code"));
//            paystackData.setReference(data.getString("reference"));
//
//            PaymentResponse paymentResponse = new PaymentResponse();
//            paymentResponse.setStatus(status);
//            paymentResponse.setMessage(message);
//            paymentResponse.setData(paystackData);
//
//            return paymentResponse;
//        } catch (IOException|JSONException e) {
//            throw new RuntimeException("Error verifying transfer", e);
//        }
//    }
//    @Override
//    public String getBanks() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + payStackConfig.getPayStackSecretKey());
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<String> response = restTemplate.exchange(
//                "https://api.paystack.co/bank", HttpMethod.GET, entity, String.class);
//
//        return response.getBody();
//    }
//
//}
