package payment.service.paymentGateway.payload;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class AddCustomerRequest {
    @NotBlank(message = "Username cannot be blank")
    @Email(message = "Username needs to be an email")
    @Column(unique = true)
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
