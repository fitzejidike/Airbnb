//package com.example.airbnb.config;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class PayStackConfig {
//    // Load PayStack secret key and URLs from application.properties file.
//    @Value("${paystack.secret.key}")
//    private String payStackSecretKey;
//    @Value("${paystack.verify.payment.url}")
//    private String payStackVerifyPaymentUrl;
//    @Value("${paystack.initiate.payment}")
//    private String payStackInitiatePaymentUrl;
//    @Value("${paystack.transfer-url}")
//    private String transferUrl;
//
//    public String getPayStackSecretKey() {
//        return payStackSecretKey;
//    }
//
//    public String getPayStackVerifyPaymentUrl() {
//        return payStackVerifyPaymentUrl;
//    }
//
//    public String getPayStackInitiatePaymentUrl() {
//        return payStackInitiatePaymentUrl;
//    }
//    public String getTransferUrl() {
//        return transferUrl;
//    }
//}
