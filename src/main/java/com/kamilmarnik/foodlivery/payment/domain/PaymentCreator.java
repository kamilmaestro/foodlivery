package com.kamilmarnik.foodlivery.payment.domain;

import com.kamilmarnik.foodlivery.order.domain.AmountOfFood;
import com.kamilmarnik.foodlivery.order.event.OrderFinished;
import com.kamilmarnik.foodlivery.supplier.domain.Money;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.kamilmarnik.foodlivery.payment.domain.PaymentStatus.PAID_OFF;
import static com.kamilmarnik.foodlivery.payment.domain.PaymentStatus.TO_PAY;
import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.toSet;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
final class PaymentCreator {

  Set<Payment> createPayments(OrderFinished order) {
    final Map<Long, List<OrderFinished.UserOrderFinished>> userOrdersByUserId = order.getUserOrders().stream()
        .collect(Collectors.groupingBy(OrderFinished.UserOrderFinished::getUserId));

    return userOrdersByUserId.entrySet().stream()
        .filter(entry -> !entry.getValue().isEmpty())
        .map(entry -> createPayment(order, entry.getKey(), entry.getValue()))
        .collect(toSet());
  }

  private Payment createPayment(OrderFinished order, long userId, List<OrderFinished.UserOrderFinished> userOrders) {
    final String paymentUuid = randomUUID().toString();
    final Set<PaymentDetails> paymentDetails = userOrders.stream()
        .map(userOrder -> createPaymentDetails(paymentUuid, userOrder))
        .collect(toSet());

    return Payment.builder()
        .uuid(paymentUuid)
        .purchaserId(order.getPurchaserId())
        .payerId(userId)
        .supplierId(order.getSupplierId())
        .channelId(order.getChannelId())
        .toPay(calculateOrderPrice(userOrders))
        .createdAt(order.getFinishedAt())
        .status(isUserPurchaser(order, userId) ? PAID_OFF : TO_PAY)
        .paymentDetails(paymentDetails)
        .build();
  }

  private boolean isUserPurchaser(OrderFinished order, long userId) {
    return userId == order.getPurchaserId();
  }

  private PaymentDetails createPaymentDetails(String paymentUuid, OrderFinished.UserOrderFinished userOrder) {
    return PaymentDetails.builder()
        .paymentUuid(paymentUuid)
        .foodName(userOrder.getFoodName())
        .amountOfFood(userOrder.getFoodAmount())
        .foodPrice(userOrder.getFoodPrice())
        .build();
  }

  private Money calculateOrderPrice(List<OrderFinished.UserOrderFinished> userOrders) {
    final double price = userOrders.stream()
        .filter(userOrder -> userOrder.getFoodPrice() >= 0 && userOrder.getFoodAmount() > 0)
        .mapToDouble(userOrder -> userOrder.getFoodPrice() * userOrder.getFoodAmount())
        .sum();

    return new Money(price);
  }

}
