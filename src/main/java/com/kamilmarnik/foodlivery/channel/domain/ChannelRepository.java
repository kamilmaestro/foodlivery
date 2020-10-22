package com.kamilmarnik.foodlivery.channel.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface ChannelRepository extends JpaRepository<Channel, Long> {

  Optional<Channel> findByUuid(String channelUuid);

}
