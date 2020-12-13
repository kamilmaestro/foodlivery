package com.kamilmarnik.foodlivery.payment.domain;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class InMemoryPaymentRepository implements PaymentRepository {

  private final Map<Long, Payment> values = new ConcurrentHashMap<>();

  @Override
  public List<Payment> findAll() {
    return null;
  }

  @Override
  public List<Payment> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<Payment> findAll(Pageable pageable) {
    return null;
  }

  @Override
  public List<Payment> findAllById(Iterable<Long> iterable) {
    return null;
  }

  @Override
  public long count() {
    return 0;
  }

  @Override
  public void deleteById(Long aLong) {

  }

  @Override
  public void delete(Payment payment) {

  }

  @Override
  public void deleteAll(Iterable<? extends Payment> iterable) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public Payment save(Payment payment) {
    if (payment.getId() == null || payment.getId() == 0L) {
      payment.setId(new Random().nextLong());
    }
    values.put(payment.getId(), payment);

    return payment;
  }

  @Override
  public <S extends Payment> List<S> saveAll(Iterable<S> iterable) {
    return StreamSupport.stream(iterable.spliterator(), false)
        .map(this::save)
        .map(payment -> (S) payment)
        .collect(Collectors.toList());
  }

  @Override
  public Optional<Payment> findById(Long id) {
    return values.values().stream()
        .filter(payment -> payment.getId().equals(id))
        .findFirst();
  }

  @Override
  public boolean existsById(Long aLong) {
    return false;
  }

  @Override
  public void flush() {

  }

  @Override
  public <S extends Payment> S saveAndFlush(S s) {
    return null;
  }

  @Override
  public void deleteInBatch(Iterable<Payment> iterable) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public Payment getOne(Long aLong) {
    return null;
  }

  @Override
  public <S extends Payment> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends Payment> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends Payment> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends Payment> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends Payment> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends Payment> boolean exists(Example<S> example) {
    return false;
  }

  @Override
  public Page<Payment> findAllByPayerIdAndStatusNot(long userId, PaymentStatus status, Pageable pageable) {
    return new PageImpl<>(values.values().stream()
        .filter(payment -> payment.getPayerId() == userId && !payment.getStatus().equals(status))
        .collect(Collectors.toList()));
  }

  @Override
  public Page<Payment> findAllByPurchaserIdAndStatusNot(long userId, PaymentStatus status, Pageable pageable) {
    return new PageImpl<>(values.values().stream()
        .filter(payment -> payment.getPurchaserId() == userId && !payment.getStatus().equals(status))
        .collect(Collectors.toList()));
  }

}
