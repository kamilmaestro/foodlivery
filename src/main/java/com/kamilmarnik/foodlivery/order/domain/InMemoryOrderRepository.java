package com.kamilmarnik.foodlivery.order.domain;

import org.springframework.data.domain.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

class InMemoryOrderRepository implements OrderRepository {

  private final Map<Long, Order> values = new ConcurrentHashMap<>();

  @Override
  public List<Order> findAll() {
    return null;
  }

  @Override
  public List<Order> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<Order> findAll(Pageable pageable) {
    return null;
  }

  @Override
  public List<Order> findAllById(Iterable<Long> iterable) {
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
  public void delete(Order order) {

  }

  @Override
  public void deleteAll(Iterable<? extends Order> iterable) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public Order save(Order order) {
    if (order.getId() == null || order.getId() == 0L) {
      long supplierId = new Random().nextLong();
      order = order.toBuilder()
          .id(supplierId)
          .build();
    }
    values.put(order.getId(), order);

    return order;
  }

  @Override
  public <S extends Order> List<S> saveAll(Iterable<S> orders) {
    return StreamSupport.stream(orders.spliterator(), false)
        .map(order -> (S) save(order))
        .collect(Collectors.toList());
  }

  @Override
  public Optional<Order> findById(Long aLong) {
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
  public <S extends Order> S saveAndFlush(S s) {
    return null;
  }

  @Override
  public void deleteInBatch(Iterable<Order> iterable) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public Order getOne(Long aLong) {
    return null;
  }

  @Override
  public <S extends Order> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends Order> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends Order> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends Order> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends Order> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends Order> boolean exists(Example<S> example) {
    return false;
  }

  @Override
  public Page<Order> findAllBySupplierId(long supplierId, PageRequest pageRequest) {
    final List<Order> orders = values.values().stream()
        .filter(order -> order.getOrderedFood().getSupplierId() == supplierId)
        .collect(Collectors.toList());

    return new PageImpl<>(orders);
  }

}
