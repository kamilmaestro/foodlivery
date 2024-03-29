package com.kamilmarnik.foodlivery.supplier.domain;

import org.springframework.data.domain.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
    List<Supplier> suppliers = new ArrayList<>(values.values());
    return new PageImpl<>(suppliers);
  }

  @Override
  public List<Supplier> findAllById(Iterable<Long> ids) {
    return values.values().stream()
        .filter(supplier ->
            StreamSupport.stream(ids.spliterator(), false).anyMatch(id -> supplier.getId().equals(id))
        ).collect(Collectors.toList());
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
    if (supplier.getId() == null || supplier.getId() == 0L) {
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

  @Override
  public Page<Supplier> findAllByNameOrAddress(String toSearch, Pageable pageable) {
    return null;
  }

}
