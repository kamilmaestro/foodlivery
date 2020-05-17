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

class InMemorySupplierRepository implements SupplierRepository {

  private final Map<Long, Supplier> values = new ConcurrentHashMap<>();

  @Override
  public List<Supplier> findAll() {
    return null;
  }

  @Override
  public List<Supplier> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<Supplier> findAll(Pageable pageable) {
    return null;
  }

  @Override
  public List<Supplier> findAllById(Iterable<Long> iterable) {
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
  public void delete(Supplier supplier) {

  }

  @Override
  public void deleteAll(Iterable<? extends Supplier> iterable) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public Supplier save(Supplier supplier) {
    if (supplier.getId() == null) {
      long supplierId = new Random().nextLong();
      supplier = supplier.toBuilder()
          .id(supplierId)
          .build();
    }
    values.put(supplier.getId(), supplier);

    return supplier;
  }

  @Override
  public <S extends Supplier> List<S> saveAll(Iterable<S> iterable) {
    return null;
  }

  @Override
  public Optional<Supplier> findById(Long supplierId) {
    return Optional.ofNullable(values.get(supplierId));
  }

  @Override
  public boolean existsById(Long aLong) {
    return false;
  }

  @Override
  public void flush() {

  }

  @Override
  public <S extends Supplier> S saveAndFlush(S s) {
    return null;
  }

  @Override
  public void deleteInBatch(Iterable<Supplier> iterable) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public Supplier getOne(Long aLong) {
    return null;
  }

  @Override
  public <S extends Supplier> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends Supplier> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends Supplier> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends Supplier> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends Supplier> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends Supplier> boolean exists(Example<S> example) {
    return false;
  }
}
