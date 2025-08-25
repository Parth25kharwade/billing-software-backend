package com.parth.billingsoftware.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RazorpayResponse {
    private String id;
    private String entity;
    private String currency;
    private String status;
    private  Integer amount;
    private Date created_at;
    private String receipt;
}
