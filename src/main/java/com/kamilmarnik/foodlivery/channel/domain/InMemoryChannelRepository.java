package com.kamilmarnik.foodlivery.channel.domain;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class InMemoryChannelRepository implements ChannelRepository {

  Map<Long, Channel> values = new ConcurrentHashMap<>();

  @Override
  public List<Channel> findAll() {
    return null;
  }

  @Override
  public List<Channel> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<Channel> findAll(Pageable pageable) {
    return null;
  }

  @Override
  public List<Channel> findAllById(Iterable<Long> iterable) {
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
  public void delete(Channel channel) {

  }

  @Override
  public void deleteAll(Iterable<? extends Channel> iterable) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public Channel save(Channel channel) {
    if (channel.getId() == null || channel.getId() == 0L) {
      channel.setId(new Random().nextLong());
    }
    values.put(channel.getId(), channel);

    return channel;
  }

  @Override
  public <S extends Channel> List<S> saveAll(Iterable<S> iterable) {
    return null;
  }

  @Override
  public Optional<Channel> findById(Long id) {
    return values.values().stream()
        .filter(channel -> channel.getId().equals(id))
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
  public <S extends Channel> S saveAndFlush(S s) {
    return null;
  }

  @Override
  public void deleteInBatch(Iterable<Channel> iterable) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public Channel getOne(Long aLong) {
    return null;
  }

  @Override
  public <S extends Channel> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends Channel> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends Channel> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends Channel> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends Channel> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends Channel> boolean exists(Example<S> example) {
    return false;
  }

  @Override
  public Optional<Channel> findByUuid(String channelUuid) {
    return values.values().stream()
        .filter(channel -> channel.getUuid().equals(channelUuid))
        .findFirst();
  }

}
