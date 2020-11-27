package com.kamilmarnik.foodlivery.channel.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

interface ChannelRepository extends JpaRepository<Channel, Long> {

  Optional<Channel> findByUuid(String channelUuid);

  Page<Channel> getByIdIn(Collection<Long> channelIds, Pageable pageable);

}
