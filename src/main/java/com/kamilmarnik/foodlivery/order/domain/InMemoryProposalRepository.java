package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.infrastructure.PageInfo;
import org.springframework.data.domain.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

class InMemoryProposalRepository implements ProposalRepository {

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
  public void delete(Proposal order) {

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

  @Override
  public Set<Proposal> findAllBySupplierId(long supplierId) {
    return values.values().stream()
        .filter(proposal -> proposal.getSupplierId() == supplierId)
        .collect(Collectors.toSet());
  }

  @Override
  public Page<Proposal> findByChannelId(long channelId, Pageable pageable) {
    return new PageImpl<>(values.values().stream()
        .filter(proposal -> proposal.getChannelId().equals(channelId))
        .collect(Collectors.toList()));
  }

}
