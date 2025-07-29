package com.example.airbnb.paystack;


import com.example.airbnb.dtos.request.PaymentRequest;
import com.example.airbnb.dtos.request.TransferRequest;
import com.example.airbnb.dtos.responses.PaymentResponse;
import org.springframework.stereotype.Service;

@Service
public interface PayStackService {
    PaymentResponse initiatePayment(PaymentRequest paymentRequest);
    PaymentResponse verifyPayment(String reference);
    PaymentResponse initiateTransfer(TransferRequest transferRequest);
    PaymentResponse verifyTransfer(String reference);
    String getBanks();
}
