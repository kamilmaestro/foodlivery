package com.kamilmarnik.foodlivery.supplier.domain;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

class InMemoryFoodRepository implements FoodRepository {

  private final Map<Long, Food> values = new ConcurrentHashMap<>();

  @Override
  public List<Food> findAll() {
    return null;
  }

  @Override
  public List<Food> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<Food> findAll(Pageable pageable) {
    return null;
  }

  @Override
  public List<Food> findAllById(Iterable<Long> iterable) {
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
  public void delete(Food food) {

  }

  @Override
  public void deleteAll(Iterable<? extends Food> iterable) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public Food save(Food food) {
    if (food.getId() == null || food.getId() == 0L) {
      long foodId = new Random().nextLong();
      food = food.toBuilder()
          .id(foodId)
          .build();
    }
    values.put(food.getId(), food);

    return food;
  }

  @Override
  public <S extends Food> List<S> saveAll(Iterable<S> iterable) {
    return null;
  }

  @Override
  public Optional<Food> findById(Long aLong) {
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
  public <S extends Food> S saveAndFlush(S s) {
    return null;
  }

  @Override
  public void deleteInBatch(Iterable<Food> iterable) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public Food getOne(Long aLong) {
    return null;
  }

  @Override
  public <S extends Food> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends Food> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends Food> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends Food> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends Food> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends Food> boolean exists(Example<S> example) {
    return false;
  }

  @Override
  public List<Food> findAllBySupplierId(long supplierId) {
    return values.values().stream()
        .filter(food -> food.getSupplierId() == supplierId)
        .collect(Collectors.toList());
  }

}
