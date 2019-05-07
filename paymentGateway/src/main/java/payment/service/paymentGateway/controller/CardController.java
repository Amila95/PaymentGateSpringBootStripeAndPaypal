package payment.service.paymentGateway.controller;

import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import payment.service.paymentGateway.model.Card;
import payment.service.paymentGateway.model.User;
import payment.service.paymentGateway.payload.AddCustomerWithCardRequest;
import payment.service.paymentGateway.payload.CreateCustomer;
import payment.service.paymentGateway.services.CardService;
import payment.service.paymentGateway.services.MapValidationErrorService;

import javax.validation.Valid;

@RestController
public class CardController {
    @Autowired
    private CardService cardService;
    @Autowired
    private MapValidationErrorService mapValidationErrorService;
    @PostMapping("/addCard")
    public ResponseEntity<?> AddCard(@Valid @RequestBody AddCustomerWithCardRequest addCustomerWithCardRequest, BindingResult result) throws StripeException {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null)return errorMap;
        Card card = cardService.addCard(addCustomerWithCardRequest);
        return  new ResponseEntity<Card>(card, HttpStatus.CREATED);

    }
}
