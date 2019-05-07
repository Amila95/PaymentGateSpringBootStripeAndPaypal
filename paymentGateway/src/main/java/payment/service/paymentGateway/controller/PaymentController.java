package payment.service.paymentGateway.controller;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import payment.service.paymentGateway.payload.PaymentRequest;
import payment.service.paymentGateway.services.MapValidationErrorService;
import payment.service.paymentGateway.services.PaymentService;

import javax.validation.Valid;


@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/payment")
    public ResponseEntity<?> makePayment(@Valid @RequestBody PaymentRequest paymentRequest,BindingResult result) throws StripeException {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null)return errorMap;
        paymentService.makePayment(paymentRequest);
        return  new ResponseEntity<>("", HttpStatus.CREATED);

    }
}
