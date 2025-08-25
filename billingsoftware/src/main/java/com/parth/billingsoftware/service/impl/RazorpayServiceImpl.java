package com.parth.billingsoftware.service.impl;

import com.parth.billingsoftware.io.RazorpayResponse;
import com.parth.billingsoftware.service.RazorpayService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RazorpayServiceImpl implements RazorpayService {

    @Value("${razorpay.key.id}")
    private String razorpayKeyId;

    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;

    @Override
    public RazorpayResponse createOrder(Double amount, String currency) throws RazorpayException {
        RazorpayClient client = new RazorpayClient(razorpayKeyId, razorpayKeySecret);

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", (int)(amount * 100)); // Razorpay accepts paise (int)
        orderRequest.put("currency", currency);
        orderRequest.put("receipt", "order_receipt_id_" + System.currentTimeMillis());
        orderRequest.put("payment_capture", 1);

        Order order = client.orders.create(orderRequest);
        return convertEntity(order);
    }

    private RazorpayResponse convertEntity(Order order) {
        return RazorpayResponse.builder()
                .id(order.get("id"))
                .amount(order.get("amount")) // ✅ convert safely
                .entity(order.get("entity"))
                .currency(order.get("currency").toString())
                .status(order.get("status").toString())
                .created_at(order.get("created_at")) // ✅ convert safely
                .receipt(order.get("receipt").toString())
                .build();
    }
}
