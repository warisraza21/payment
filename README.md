# payment

#I have added one sql script run that script in workbench it will create schema (payment_gateway) and tables (merchant, payment) in it
 or you can create schema `payment_gateway` only table will get created once you run the Payment application

# Run the applications Payment and Merchant both will run on 8082 and 8081 port respectively

# Request will come to Merchant application from there by using webclient this Merchant app will connect with
Payment application and you can get the response accordingly

Application has three apis

1) register_merchant Post request it will create the merchant record

2) payment Post request it will create the payment record

3) payment_status/{paymentId} Get request it will give the status of the payment


#merchant and payment is mapped with one to many mapping (one merchant can have many payments)


-> Steps you can follow for Api call
#first step is to call "register_merchant" api once you register;

#You can call "payment" api with merchant_id of register merchant here I am assuming that phone number will not be null or empty so by
using phone and name I am creating (merchant_id = name to lower without space + phone);

# once the "payment" api is called with payload you will get a response in which you will have one id in response message
copy that id and call the "payment_status/paymentId" place that id in place of paymentId in response you will
get the status of payment;
