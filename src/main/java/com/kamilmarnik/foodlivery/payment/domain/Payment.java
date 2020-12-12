package com.kamilmarnik.foodlivery.payment.domain;

import com.kamilmarnik.foodlivery.payment.dto.PaymentDto;
import com.kamilmarnik.foodlivery.supplier.domain.Money;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "payments")
class Payment {

  @Setter(value = AccessLevel.PACKAGE)
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(name = "uuid")
  String uuid;

  @Column(name = "purchaser_id")
  Long purchaserId;

  @Column(name = "payer_id")
  Long payerId;

  @Column(name = "supplier_id")
  Long supplierId;

  @Column(name = "channel_id")
  Long channelId;

  @Embedded
  @AttributeOverride(name = "toPay", column = @Column(name = "to_pay"))
  Money toPay;

  @Column(name = "created_at")
  Instant createdAt;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "payment_uuid", referencedColumnName = "uuid", updatable = false, insertable = false)
  Set<PaymentDetails> paymentDetails;

  PaymentDto dto() {
    final List<PaymentDto.PaymentDetailsDto> paymentDetails = this.paymentDetails.stream()
        .map(PaymentDetails::dto)
        .collect(Collectors.toList());

    return PaymentDto.builder()
        .id(this.id)
        .purchaserId(this.purchaserId)
        .payerId(this.payerId)
        .supplierId(this.supplierId)
        .channelId(this.channelId)
        .price(this.toPay.getValueAsDouble())
        .createdAt(this.createdAt)
        .details(paymentDetails)
        .build();
  }

}
