package com.kamilmarnik.foodlivery.channel.domain;

import com.kamilmarnik.foodlivery.channel.dto.ChannelMemberDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class InMemoryChannelMemberRepository implements ChannelMemberRepository {

  Map<Long, ChannelMember> values = new ConcurrentHashMap<>();

  @Override
  public List<ChannelMember> findAll() {
    return null;
  }

  @Override
  public List<ChannelMember> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<ChannelMember> findAll(Pageable pageable) {
    return null;
  }

  @Override
  public List<ChannelMember> findAllById(Iterable<Long> iterable) {
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
  public void delete(ChannelMember channelMember) {

  }

  @Override
  public void deleteAll(Iterable<? extends ChannelMember> iterable) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public ChannelMember save(ChannelMember channelMember) {
    if (channelMember.getId() == null || channelMember.getId() == 0L) {
      channelMember.setId(new Random().nextLong());
    }
    values.put(channelMember.getId(), channelMember);

    return channelMember;
  }

  @Override
  public <S extends ChannelMember> List<S> saveAll(Iterable<S> iterable) {
    return null;
  }

  @Override
  public Optional<ChannelMember> findById(Long aLong) {
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
  public <S extends ChannelMember> S saveAndFlush(S s) {
    return null;
  }

  @Override
  public void deleteInBatch(Iterable<ChannelMember> iterable) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public ChannelMember getOne(Long aLong) {
    return null;
  }

  @Override
  public <S extends ChannelMember> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends ChannelMember> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends ChannelMember> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends ChannelMember> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends ChannelMember> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends ChannelMember> boolean exists(Example<S> example) {
    return false;
  }

  @Override
  public Collection<Long> findChannelIdsAllByUserId(long userId) {
    return values.values().stream()
        .filter(channelMember -> channelMember.getMemberId().equals(userId))
        .map(ChannelMember::getChannelId)
        .collect(Collectors.toList());
  }

  @Override
  public Collection<ChannelMember> findByChannelId(long channelId) {
    return values.values().stream()
        .filter(channelMember -> channelMember.getChannelId().equals(channelId))
        .collect(Collectors.toList());
  }

}
