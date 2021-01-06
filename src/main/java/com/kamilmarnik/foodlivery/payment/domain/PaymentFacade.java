package com.kamilmarnik.foodlivery.payment.domain;

import com.kamilmarnik.foodlivery.infrastructure.PageInfo;
import com.kamilmarnik.foodlivery.order.event.OrderFinished;
import com.kamilmarnik.foodlivery.payment.dto.PaymentDto;
import com.kamilmarnik.foodlivery.payment.exception.PaymentNotFound;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;

import javax.transaction.Transactional;
import java.util.Set;

import static com.kamilmarnik.foodlivery.infrastructure.authentication.LoggedUserGetter.getLoggedUserId;
import static com.kamilmarnik.foodlivery.payment.domain.PaymentStatus.PAID_OFF;

@Transactional
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentFacade {

  PaymentRepository paymentRepository;
  PaymentCreator paymentCreator;

  public Page<PaymentDto> findUserCharges(PageInfo pageInfo) {
    return paymentRepository.findAllByPayerIdAndStatusNot(getLoggedUserId(), PAID_OFF, pageInfo.toPageRequest())
        .map(Payment::dto);
  }

  public Page<PaymentDto> findUserMoneyDue(PageInfo pageInfo) {
    return paymentRepository.findAllByPurchaserIdAndStatusNot(getLoggedUserId(), PAID_OFF, pageInfo.toPageRequest())
        .map(Payment::dto);
  }

  public void marksAsPaidOff(long paymentId) {
    final Payment payment = getPayment(paymentId);
    final Payment updatedPayment = payment.marksAsPaidOff();
    paymentRepository.save(updatedPayment);
  }

  @EventListener
  void onOrderFinished(OrderFinished orderFinished) {
    final Set<Payment> payments = paymentCreator.createPayments(orderFinished);
    paymentRepository.saveAll(payments);
  }

  private Payment getPayment(long paymentId) {
    return paymentRepository.findById(paymentId)
        .orElseThrow(() -> new PaymentNotFound(paymentId));
  }

}
