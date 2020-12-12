package com.kamilmarnik.foodlivery.payment.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class PaymentConfiguration {

  PaymentFacade paymentFacade() {
    return paymentFacade(new InMemoryPaymentRepository());
  }

  @Bean
  PaymentFacade paymentFacade(PaymentRepository paymentRepository) {
    return PaymentFacade.builder()
        .paymentRepository(paymentRepository)
        .paymentCreator(new PaymentCreator())
        .build();
  }

}
