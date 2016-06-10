Imagine a world where you never have to wait in check out lines.
This project is a Proof of Concept that to enable such a customer experience using NFC and Stripe

A customer walks into the store and taps his android phone on the item he intends to buy. The item has its price data written on the mounted NFC tag.

The app scans the price information, on user confirmation talks to Stripe to obtain a token for the entered credit card details.

The token is sent via POST securely to the backend server along with the validated price. The backend server uses the token and charges the card using Stripe API.

The backend server can then use the transaction status returned by stripe to either disable the anti-theft devices and send an invoice to the customer.

File Descriptions:
-----------------

The client side main functionality is implemented by two files.

1. MobilePayment.java:
   Reads data from NFC, performs user interaction to confirm buying of the item.

2. ProcessPayment.java:
   On confirmation of buying it provides a a dialog box to enter the credit card details using stripe checkout api and obtains the token. This token is securely transmitted to the backend server where the card gets charged.

Both these files are found in:
app/src/main/java/nfc/android/mobilepayment/

Video Demo:
-----------

[![Mobile Payments Video](http://img.youtube.com/vi/oqu5whyJoKI/0.jpg)](http://www.youtube.com/watch?v=oqu5whyJoKI)
