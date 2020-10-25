package com.kamilmarnik.foodlivery.order.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class InMemoryFinalizedOrderRepository implements FinalizedOrderRepository {

  private final Map<Long, FinalizedOrder> values = new ConcurrentHashMap<>();

  @Override
  public List<FinalizedOrder> findAll() {
    return null;
  }

  @Override
  public List<FinalizedOrder> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<FinalizedOrder> findAll(Pageable pageable) {
    return null;
  }

  @Override
  public List<FinalizedOrder> findAllById(Iterable<Long> iterable) {
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
  public void delete(FinalizedOrder finalizedOrder) {

  }

  @Override
  public void deleteAll(Iterable<? extends FinalizedOrder> iterable) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public FinalizedOrder save(FinalizedOrder order) {
    if (order.getId() == null || order.getId() == 0L) {
      long orderId = new Random().nextLong();
      order.setId(orderId);
    }
//    final Set<UserOrder> userOrders = order.getUserOrders().stream()
//        .map(this::saveUserOrder)
//        .collect(Collectors.toSet());
//    final Order withUserOrders = order.toBuilder().userOrders(userOrders).build();
    values.put(order.getId(), order);

    return order;
  }

  @Override
  public <S extends FinalizedOrder> List<S> saveAll(Iterable<S> iterable) {
    return null;
  }

  @Override
  public Optional<FinalizedOrder> findById(Long aLong) {
    return Optional.empty();
  }

  @Override
  public boolean existsById(Long aLong) {
    return false;
  }

  @Override
  public void flush() {

  }

  @Override
  public <S extends FinalizedOrder> S saveAndFlush(S s) {
    return null;
  }

  @Override
  public void deleteInBatch(Iterable<FinalizedOrder> iterable) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public FinalizedOrder getOne(Long aLong) {
    return null;
  }

  @Override
  public <S extends FinalizedOrder> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends FinalizedOrder> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends FinalizedOrder> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends FinalizedOrder> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends FinalizedOrder> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends FinalizedOrder> boolean exists(Example<S> example) {
    return false;
  }

}
