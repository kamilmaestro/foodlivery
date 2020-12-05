package com.kamilmarnik.foodlivery.order.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "application.order")
final class OrderExpirationConfig {

  private int expirationAfterMinutes;

}
