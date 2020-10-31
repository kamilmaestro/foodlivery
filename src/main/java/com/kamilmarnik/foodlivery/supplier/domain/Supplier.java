package com.kamilmarnik.foodlivery.supplier.domain;

import com.kamilmarnik.foodlivery.supplier.dto.SupplierDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter(AccessLevel.PACKAGE)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "suppliers")
class Supplier {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(name = "name")
  String name;

  @Column(name = "phone_number")
  String phoneNumber;

  @Column(name = "email")
  String email;

  @Column(name = "created_at")
  LocalDateTime createdAt;

  SupplierDto dto() {
    return SupplierDto.builder()
        .id(this.id)
        .name(this.name)
        .build();
  }

}
