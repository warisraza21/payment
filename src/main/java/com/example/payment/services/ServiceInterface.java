package com.example.payment.services;

import com.example.payment.models.MerchantDTO;
import com.example.payment.models.PaymentDTO;
import com.example.payment.models.ResponseDTO;

public interface ServiceInterface {

    ResponseDTO createMerchant(MerchantDTO merchantDTO);

    ResponseDTO doPayment(PaymentDTO paymentDTO);

    ResponseDTO checkStatus(String paymentId);
}
