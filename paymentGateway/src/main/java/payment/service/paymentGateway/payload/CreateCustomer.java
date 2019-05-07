package payment.service.paymentGateway.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class CreateCustomer {
    @NotBlank(message = "Username cannot be blank")
    @Email(message = "Username needs to be an email")
    private String username;
    @NotBlank(message = "Card Number cannot be blank")
    private String cardNumber;
    @NotBlank(message = "Exp Month cannot be blank")
    private String expMonth;
    @NotBlank(message = "Exp Year cannot be blank")
    private String expYear;
    @NotBlank(message = "CVC cannot be blank")
    private String cvc;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(String expMonth) {
        this.expMonth = expMonth;
    }

    public String getExpYear() {
        return expYear;
    }

    public void setExpYear(String expYear) {
        this.expYear = expYear;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }
}
