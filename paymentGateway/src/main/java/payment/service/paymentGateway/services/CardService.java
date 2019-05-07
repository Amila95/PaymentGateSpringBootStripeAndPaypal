package payment.service.paymentGateway.services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import payment.service.paymentGateway.exception.UsernameAlreadyExistsException;
import payment.service.paymentGateway.model.Card;
import payment.service.paymentGateway.model.User;
import payment.service.paymentGateway.payload.AddCustomerWithCardRequest;
import payment.service.paymentGateway.repository.CardRepository;
import payment.service.paymentGateway.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class CardService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CardRepository cardRepository;

    public Card addCard(AddCustomerWithCardRequest addCustomerWithCardRequest) throws StripeException {
        Stripe.apiKey = "sk_test_1koZ3XEtpBc6gMFffH8V3UEZ008ev5Maiu";
        User user = userRepository.findByUsername(addCustomerWithCardRequest.getUsername());
        if(user != null){
            Card newCard = cardRepository.findByCardNumberAndUserAndExpMonthAndExpYearAndCvc(addCustomerWithCardRequest.getCardNumber(),user,addCustomerWithCardRequest.getExpYear(),addCustomerWithCardRequest.getExpMonth(),addCustomerWithCardRequest.getCvc());
            if(newCard == null){
                Customer customer = Customer.retrieve(user.getToken());
                Map<String, Object> cardParam = new HashMap<>();
                cardParam.put("number", addCustomerWithCardRequest.getCardNumber());
                cardParam.put("exp_month", addCustomerWithCardRequest.getExpMonth());
                cardParam.put("exp_year", addCustomerWithCardRequest.getExpYear());
                cardParam.put("cvc", addCustomerWithCardRequest.getCvc());

                Map<String, Object> tokenParam = new HashMap<>();
                tokenParam.put("card",cardParam);

                Token token = Token.create(tokenParam);
                Map<String, Object> source = new HashMap<>();
                source.put("source",token.getId());
                customer.getSources().create(source);

                Card card = new Card();
                card.setCvc(addCustomerWithCardRequest.getCvc());
                card.setExpYear(addCustomerWithCardRequest.getExpYear());
                card.setExpmonth(addCustomerWithCardRequest.getExpMonth());
                card.setCardnumber(addCustomerWithCardRequest.getCardNumber());
                card.setFingerprint(token.getCard().getFingerprint());
                card.setToken(token.getCard().getId());
                card.setUser(user);

                return cardRepository.save(card);
            }else{
                throw new UsernameAlreadyExistsException("Card Number '"+addCustomerWithCardRequest.getCardNumber()+"' is already Registered");
            }
        }
        else{
            throw new UsernameAlreadyExistsException("Username '"+addCustomerWithCardRequest.getUsername()+"' is Not Registered");
        }

    }
}
