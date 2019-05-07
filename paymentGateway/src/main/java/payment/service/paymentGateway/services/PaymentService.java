package payment.service.paymentGateway.services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import payment.service.paymentGateway.model.Card;
import payment.service.paymentGateway.model.User;
import payment.service.paymentGateway.payload.PaymentRequest;
import payment.service.paymentGateway.repository.CardRepository;
import payment.service.paymentGateway.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CardRepository cardRepository;

    public void makePayment(PaymentRequest paymentRequest) throws StripeException {
        Stripe.apiKey = "sk_test_1koZ3XEtpBc6gMFffH8V3UEZ008ev5Maiu";
        User user = userRepository.findByUsername(paymentRequest.getUsername());

        Card card = cardRepository.findByCardNumberAndUserAndExpMonthAndExpYearAndCvc(paymentRequest.getCardNumber(),user,paymentRequest.getExpMonth(),paymentRequest.getExpYear(),paymentRequest.getCvc());

        Map<String, Object> chargeParam = new HashMap<>();
        chargeParam.put("amount",paymentRequest.getPrice());
        chargeParam.put("currency","usd");
        chargeParam.put("customer",user.getToken());
        chargeParam.put("source",card.getToken());

         Charge.create(chargeParam);

    }
}
