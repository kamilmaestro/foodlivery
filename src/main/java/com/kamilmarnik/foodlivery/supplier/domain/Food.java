package com.kamilmarnik.foodlivery.supplier.domain;

import com.kamilmarnik.foodlivery.supplier.dto.FoodDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter(AccessLevel.PACKAGE)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "food")
class Food {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(name = "name")
  String name;

  @Column(name = "supplier_id")
  Long supplierId;

  @Column(name = "image_id")
  Long imageId;

  @Embedded
  @AttributeOverride(name = "price", column = @Column(name = "price"))
  Money price;

  FoodDto dto() {
    return FoodDto.builder()
        .id(this.id)
        .name(this.name)
        .supplierId(this.supplierId)
        .price(this.price.getValueAsDouble())
        .imageId(this.imageId)
        .build();
  }

}
