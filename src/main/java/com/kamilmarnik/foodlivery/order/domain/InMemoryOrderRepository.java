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

  private final Map<Long, Proposal> values = new ConcurrentHashMap<>();

  @Override
  public List<Proposal> findAll() {
    return null;
  }

  @Override
  public List<Proposal> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<Proposal> findAll(Pageable pageable) {
    return null;
  }

  @Override
  public List<Proposal> findAllById(Iterable<Long> iterable) {
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
  public void delete(Proposal proposal) {

  }

  @Override
  public void deleteAll(Iterable<? extends Proposal> iterable) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public Proposal save(Proposal proposal) {
    if (proposal.getId() == null || proposal.getId() == 0L) {
      long supplierId = new Random().nextLong();
      proposal = proposal.toBuilder()
          .id(supplierId)
          .build();
    }
    values.put(proposal.getId(), proposal);

    return proposal;
  }

  @Override
  public <S extends Proposal> List<S> saveAll(Iterable<S> iterable) {
    return null;
  }

  @Override
  public Optional<Proposal> findById(Long id) {
    return Optional.ofNullable(values.get(id));
  }

  @Override
  public boolean existsById(Long aLong) {
    return false;
  }

  @Override
  public void flush() {

  }

  @Override
  public <S extends Proposal> S saveAndFlush(S s) {
    return null;
  }

  @Override
  public void deleteInBatch(Iterable<Proposal> iterable) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public Proposal getOne(Long aLong) {
    return null;
  }

  @Override
  public <S extends Proposal> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends Proposal> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends Proposal> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends Proposal> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends Proposal> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends Proposal> boolean exists(Example<S> example) {
    return false;
  }

}
