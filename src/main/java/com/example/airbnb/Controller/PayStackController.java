package com.example.airbnb.Controller;

import com.example.airbnb.dtos.request.PaymentRequest;
import com.example.airbnb.dtos.request.TransferRequest;
import com.example.airbnb.dtos.responses.PaymentResponse;
import com.example.airbnb.paystack.PayStackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paystack")
@RequiredArgsConstructor
@Tag(name = "Paystack API", description = "Endpoints for handling payments and transfers via Paystack")
public class PayStackController {

    private final PayStackService payStackService;

    @PostMapping("/initiate-payment")
    @Operation(summary = "Initiate a new payment", description = "Creates a new Paystack payment session")
    public ResponseEntity<PaymentResponse> initiatePayment(@RequestBody PaymentRequest paymentRequest) {
        PaymentResponse response = payStackService.initiatePayment(paymentRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/verify-payment/{reference}")
    @Operation(summary = "Verify a payment", description = "Checks the status of a payment using the transaction reference")
    public ResponseEntity<PaymentResponse> verifyPayment(@PathVariable String reference) {
        PaymentResponse response = payStackService.verifyPayment(reference);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/initiate-transfer")
    @Operation(summary = "Initiate a transfer", description = "Transfers money from your Paystack balance to a recipient")
    public ResponseEntity<PaymentResponse> initiateTransfer(@RequestBody TransferRequest transferRequest) {
        PaymentResponse response = payStackService.initiateTransfer(transferRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/verify-transfer/{reference}")
    @Operation(summary = "Verify a transfer", description = "Checks the status of a transfer using the transfer reference")
    public ResponseEntity<PaymentResponse> verifyTransfer(@PathVariable String reference) {
        PaymentResponse response = payStackService.verifyTransfer(reference);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/banks")
    @Operation(summary = "Get list of banks", description = "Fetches all banks available in Paystack")
    public ResponseEntity<String> getBanks() {
        return ResponseEntity.ok(payStackService.getBanks());
    }
}
