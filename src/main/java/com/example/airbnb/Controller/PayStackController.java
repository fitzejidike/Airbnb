//package com.example.airbnb.Controller;
//
//import com.example.airbnb.dtos.request.PaymentRequest;
//import com.example.airbnb.dtos.request.TransferRequest;
//import com.example.airbnb.dtos.responses.PaymentResponse;
//import com.example.airbnb.paystack.PayStackService;
//import lombok.AllArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/paystack")
//@AllArgsConstructor
//public class PayStackController {
//
//    private final PayStackService payStackService;
//
//    @PostMapping("/initiate-payment")
//    public PaymentResponse initiatePayment(@RequestBody PaymentRequest paymentRequest) {
//        return payStackService.initiatePayment(paymentRequest);
//    }
//
//    @GetMapping("/verify-payment/{reference}")
//    public ResponseEntity<PaymentResponse> verifyPayment(@PathVariable String reference) {
//        PaymentResponse response = payStackService.verifyPayment(reference);
//        return ResponseEntity.ok(response);
//
////    @PostMapping("/initiate-transfer")
////    public PaymentResponse initiateTransfer(@RequestBody TransferRequest transferRequest) {
////        return payStackService.initiateTransfer(transferRequest);
////    }
////
////    @GetMapping("/verify-transfer/{reference}")
////    public PaymentResponse verifyTransfer(@PathVariable String reference) {
////        return payStackService.verifyTransfer(reference);
////    }
////
////    @GetMapping("/banks")
////    public String getBanks() {
////        return payStackService.getBanks();
////    }
//    }
//}
