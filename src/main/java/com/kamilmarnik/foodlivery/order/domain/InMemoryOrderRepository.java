package com.kamilmarnik.foodlivery.order.domain;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

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
  public <S extends Order> List<S> saveAll(Iterable<S> iterable) {
    return null;
  }

  @Override
  public Optional<Order> findById(Long id) {
    return Optional.ofNullable(values.get(id));
  }

  @Override
  public boolean existsById(Long aLong) {
    return false;
  }

  @Override
  public Optional<Order> findById(String s) {
    return Optional.empty();
  }

  @Override
  public boolean existsById(String s) {
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
  public Order getOne(String s) {
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

}
