package com.example.payment.controllers;


import com.example.payment.models.MerchantDTO;
import com.example.payment.models.PaymentDTO;
import com.example.payment.models.ResponseDTO;
import com.example.payment.services.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class Controller {

    @Autowired
    private ServiceInterface service;

    @PostMapping("register_merchant")
    public ResponseEntity<?> createMerchant(@RequestBody MerchantDTO merchantDTO){
        try{
            ResponseDTO responseDTO = service.createMerchant(merchantDTO);
            return new ResponseEntity<>(responseDTO,HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(new ResponseDTO(false,e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("payment")
    public ResponseEntity<?> doPayment(@RequestBody PaymentDTO paymentDTO){
        try{
            ResponseDTO responseDTO = service.doPayment(paymentDTO);
            return new ResponseEntity<>(responseDTO,HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(new ResponseDTO(false,e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("payment_status/{paymentId}")
    public ResponseEntity<?> getPaymentStatus(@PathVariable("paymentId") String paymentId){
        try{
            ResponseDTO responseDTO = service.checkStatus(paymentId);
            return new ResponseEntity<>(responseDTO,HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(new ResponseDTO(false,e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
