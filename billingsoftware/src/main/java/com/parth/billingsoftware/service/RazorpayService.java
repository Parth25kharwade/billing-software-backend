package com.parth.billingsoftware.service;

import com.parth.billingsoftware.io.RazorpayResponse;
import com.razorpay.RazorpayException;

public interface RazorpayService {
    RazorpayResponse createOrder(Double amount, String currency) throws RazorpayException;
}
