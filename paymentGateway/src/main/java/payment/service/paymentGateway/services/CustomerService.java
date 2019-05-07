package payment.service.paymentGateway.services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import payment.service.paymentGateway.exception.UsernameAlreadyExistsException;
import payment.service.paymentGateway.model.User;
import payment.service.paymentGateway.payload.AddCustomerRequest;
import payment.service.paymentGateway.payload.AddCustomerWithCardRequest;
import payment.service.paymentGateway.repository.CardRepository;
import payment.service.paymentGateway.repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    CardService cardService;

    public void addCustomer(AddCustomerRequest addCustomerRequest)throws StripeException {
        try {
            Stripe.apiKey = "sk_test_1koZ3XEtpBc6gMFffH8V3UEZ008ev5Maiu";
            User user1 = userRepository.findByUsername(addCustomerRequest.getUsername());
            if(user1==null){
                Map<String, Object> customerParameter = new HashMap<String, Object>();
                customerParameter.put("email", addCustomerRequest.getUsername());
                Customer newCustomer = Customer.create(customerParameter);
                User user = new User();
                user.setUsername(addCustomerRequest.getUsername());
                user.setToken(newCustomer.getId());
                userRepository.save(user);
            }
            else{
                throw new UsernameAlreadyExistsException("Username '"+addCustomerRequest.getUsername()+"' already exists");
            }
        }catch (Exception e){
            throw new UsernameAlreadyExistsException("Username '"+addCustomerRequest.getUsername()+"' already exists");
        }
    }

    public void addCustomerWithCard(AddCustomerWithCardRequest addCustomerWithCardRequest)throws StripeException {
        try {
        AddCustomerRequest addCustomerRequest = new AddCustomerRequest();
        addCustomerRequest.setUsername(addCustomerWithCardRequest.getUsername());
        this.addCustomer(addCustomerRequest);
        cardService.addCard(addCustomerWithCardRequest);

        }catch (Exception e){
            throw new UsernameAlreadyExistsException("Username '"+addCustomerWithCardRequest.getUsername()+"' already exists");
        }

    }

    public List<?> getCards(User user) {
        return cardRepository.findByUser(user);
    }
}
