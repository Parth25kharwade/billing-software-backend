package com.parth.billingsoftware.controller;

import com.parth.billingsoftware.io.OrderResponce;
import com.parth.billingsoftware.io.PaymentVerificationRequest;
import com.parth.billingsoftware.io.RazorpayRequest;
import com.parth.billingsoftware.io.RazorpayResponse;
import com.parth.billingsoftware.service.OrderService;
import com.parth.billingsoftware.service.RazorpayService;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PaymentController {
    private final RazorpayService razorpayService;
    private final OrderService orderService;
    @RequestMapping("/create-order")
    @ResponseStatus(HttpStatus.CREATED)
    public RazorpayResponse createRazorpayOrder(@RequestBody RazorpayRequest paymentRequest) throws RazorpayException {
       return razorpayService.createOrder(paymentRequest.getAmount(),paymentRequest.getCurrency());
    }

    @PostMapping("/verify")
    public OrderResponce verifyPayment(@RequestBody PaymentVerificationRequest request){
           return orderService.verifyPayment(request);

    }

}
