When a customer wants to buy and check out his item/s(NFC tag will be mounted on item), without having to wait for a long time in a queue; here comes my Android Application.

Using my Android App which is integrated with stripe API(which acts as a payment gateway), customer just needs to tap on an item or come close to its proximity; A Page pops out to enter the user's credit card information. Once it is successful, item price will be displayed on the phone screen and it gives two options; one is to "confirm" the item and the other is to go "back" to the home page.

Once the user confirms the payment, price information will go to the stripe backend, which returns a token. Token along with the price information go to the server and updates the inventory. This will be sent to the Stripe backend where it validates the information and charges the credit card. Based on this information, it sends success or failure message to the customer. If it is successful, it sends an invoice to the user. This information can be used to activate or de-activate anti-theft devices.

I have added a Demo-Video of my Project.

[![Mobile Payments Video](http://img.youtube.com/vi/oqu5whyJoKI/0.jpg)](http://www.youtube.com/watch?v=oqu5whyJoKI)
