package com.kamilmarnik.foodlivery.order.domain;

import org.springframework.data.domain.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.kamilmarnik.foodlivery.order.domain.OrderStatus.FINALIZED;
import static com.kamilmarnik.foodlivery.order.domain.OrderStatus.ORDERED;

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
      long orderId = new Random().nextLong();
      order.setId(orderId);
    }
    final Set<UserOrder> userOrders = order.getUserOrders().stream()
        .map(this::saveUserOrder)
        .collect(Collectors.toSet());
    final Order withUserOrders = order.toBuilder().userOrders(userOrders).build();
    values.put(withUserOrders.getId(), withUserOrders);

    return withUserOrders;
  }

  private UserOrder saveUserOrder(UserOrder userOrder) {
    if (userOrder.getId() == null || userOrder.getId() == 0L) {
      long orderId = new Random().nextLong();
      userOrder.setId(orderId);
    }
    final Set<OrderedFood> orderedFood = userOrder.getFood().stream()
        .map(this::saveOrderedFood)
        .collect(Collectors.toSet());

    return userOrder.toBuilder().food(orderedFood).build();
  }

  private OrderedFood saveOrderedFood(OrderedFood orderedFood) {
    if (orderedFood.getId() == null || orderedFood.getId() == 0L) {
      long orderId = new Random().nextLong();
      orderedFood.setId(orderId);
    }

    return orderedFood;
  }

  @Override
  public <S extends Order> List<S> saveAll(Iterable<S> userOrders) {
    return StreamSupport.stream(userOrders.spliterator(), false)
        .map(order -> (S) save(order))
        .collect(Collectors.toList());
  }

  @Override
  public Optional<Order> findById(Long id) {
    return values.values().stream()
        .filter(order -> order.getId().equals(id))
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
  public Optional<Order> findActiveOrderForSupplierInChannel(long supplierId, long channelId) {
    return values.values().stream()
        .filter(order -> order.getSupplierId().equals(supplierId) &&
            order.getChannelId().equals(channelId) &&
            (order.getStatus().equals(ORDERED) || order.getStatus().equals(FINALIZED))
        ).findFirst();
  }

  @Override
  public Optional<Order> findByIdAndStatus(long orderId, OrderStatus status) {
    return values.values().stream()
        .filter(order -> order.getId().equals(orderId) && order.getStatus().equals(status))
        .findFirst();
  }

  @Override
  public Optional<Order> findByIdAndStatusIn(long orderId, Collection<OrderStatus> status) {
    return values.values().stream()
        .filter(order -> order.getId().equals(orderId) && status.contains(order.getStatus()))
        .findFirst();
  }

  @Override
  public Page<Order> findAllByChannelIdAndStatusIn(long channelId, Collection<OrderStatus> status, Pageable pageable) {
    final List<Order> orders = values.values().stream()
        .filter(order -> order.getChannelId().equals(channelId) && status.contains(order.getStatus()))
        .collect(Collectors.toList());
    return new PageImpl<>(orders, pageable, orders.size());
  }

  @Override
  public Page<Order> findAllUserOrders(long userId, Pageable pageable) {
    return null;
  }

  @Override
  public Optional<Order> findByIdAndStatusNot(long orderId, OrderStatus status) {
    return values.values().stream()
        .filter(order -> order.getId().equals(orderId) && !order.getStatus().equals(status))
        .findFirst();
  }

}
