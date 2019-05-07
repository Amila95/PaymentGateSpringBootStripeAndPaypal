package payment.service.paymentGateway.controller;

import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import payment.service.paymentGateway.model.User;
import payment.service.paymentGateway.payload.AddCustomerRequest;
import payment.service.paymentGateway.payload.AddCustomerWithCardRequest;
import payment.service.paymentGateway.repository.UserRepository;
import payment.service.paymentGateway.services.CustomerService;
import payment.service.paymentGateway.services.MapValidationErrorService;

import javax.validation.Valid;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/addCustomer")
    public ResponseEntity<?> addCustomer(@Valid @RequestBody AddCustomerRequest addCustomerRequest, BindingResult result) throws StripeException {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null)return errorMap;
        customerService.addCustomer(addCustomerRequest);
        return  new ResponseEntity<>("", HttpStatus.CREATED);
   }


    @PostMapping("/addCustomerWithCard")
    public ResponseEntity<?> CreateCustomerWithCard(@Valid @RequestBody AddCustomerWithCardRequest addCustomerWithCardRequest, BindingResult result) throws StripeException {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null)return errorMap;
        customerService.addCustomerWithCard(addCustomerWithCardRequest);
        return  new ResponseEntity<>("", HttpStatus.CREATED);

    }

    @GetMapping("/getAllCard")
    public ResponseEntity<?> getAllCard(@RequestParam("username") String username){
        User user = userRepository.findByUsername(username);
        return ResponseEntity.ok(customerService.getCards(user));
    }

}
