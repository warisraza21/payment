package com.example.payment.services.serviceImpl;

import com.example.payment.entities.Merchant;
import com.example.payment.entities.Payment;
import com.example.payment.models.MerchantDTO;
import com.example.payment.models.PaymentDTO;
import com.example.payment.models.ResponseDTO;
import com.example.payment.repositories.MerchantRepository;
import com.example.payment.repositories.PaymentRepository;
import com.example.payment.services.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServiceImplementation implements ServiceInterface {

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public ResponseDTO createMerchant(MerchantDTO merchantDTO) {
        // concatenating phone and name for unique merchantId assuming phone will be present
        String merchantId = convertToLow(merchantDTO.getName()) + merchantDTO.getPhone();
        Optional<Merchant> merchantOpt = merchantRepository.findById(merchantId);

        if(!merchantOpt.isEmpty()){
            return new ResponseDTO(false,"Merchant already Exist!");
        } else {
            Merchant merchant = new Merchant();
            merchant.setMerchantId(merchantId);
            merchant.setAddress(merchantDTO.getAddress());
            merchant.setName(merchantDTO.getName());
            merchant.setEmail(merchantDTO.getEmail());
            merchant.setPhone(merchantDTO.getPhone());
            merchant.setBusinessType(merchantDTO.getBusinessType());
            merchantRepository.save(merchant);
            return new ResponseDTO(true,"Merchant has been Added Successfully!");
        }
    }

    @Override
    public ResponseDTO doPayment(PaymentDTO paymentDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        Optional<Merchant> merchant = merchantRepository.findById(paymentDTO.getMerchantId());
        Payment payment = new Payment();
        payment.setAmount(paymentDTO.getAmount());
        payment.setCurrency(paymentDTO.getCurrency());
        payment.setOrderId(paymentDTO.getOrderId());
        payment.setPaymentId(getUniqueId());

        if(!merchant.isEmpty()){
            payment.setMerchant(merchant.get());
            payment.setStatus("Success");
            responseDTO.setStatus(true);
            responseDTO.setMessage("Payment done Successfully with payment Id: ");
        } else {
            payment.setMerchant(null);
            payment.setStatus("Failed");
            responseDTO.setStatus(false);
            responseDTO.setMessage("Error while doing payment to merchant with Id " + paymentDTO.getMerchantId() + " and payment id : ");
        }
        try {
            Payment payment1 = paymentRepository.save(payment);
            String initialMsg = responseDTO.getMessage();
            responseDTO.setMessage(initialMsg + payment1.getPaymentId());
        } catch (Exception e){
            responseDTO.setStatus(false);
            responseDTO.setMessage(e.getMessage());
        }
        return responseDTO;
    }

    @Override
    public ResponseDTO checkStatus(String paymentId) {
        ResponseDTO responseDTO = new ResponseDTO();
        Optional<Payment> payment = paymentRepository.findById(paymentId);

        if(!payment.isEmpty()){
            Payment payment1 = payment.get();
            if(payment1.getMerchant() == null){
                responseDTO.setStatus(false);
                responseDTO.setMessage("Payment Status: " + payment1.getStatus());
            } else {
                responseDTO.setStatus(true);
                responseDTO.setMessage("Payment is done to " + payment1.getMerchant().getMerchantId() + " and Status is : " + payment1.getStatus());
            }
        } else{
            responseDTO.setStatus(false);
            responseDTO.setMessage("Payment record doesn't exist with this paymentId :  " + paymentId);
        }
        return responseDTO;
    }

    private String getUniqueId(){
        //generating unique uui Id
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    private String convertToLow(String input){
        if(input == null || input.isEmpty())
            return  "";
        // Remove all spaces and convert to lowercase
        String result = input.replaceAll("\\s+", "").toLowerCase();
        return  result;
    }
}
