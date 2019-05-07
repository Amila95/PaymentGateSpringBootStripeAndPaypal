package payment.service.paymentGateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import payment.service.paymentGateway.model.Card;
import payment.service.paymentGateway.model.User;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Card findByCardNumber(String cardNumber);
    Card findByCardNumberAndUserAndExpMonthAndExpYearAndCvc(String cardNumber, User user, String expYear,String expMonth,String cvc);
    List<?> findByUser(User user);
}
