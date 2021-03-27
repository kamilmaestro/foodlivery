package com.kamilmarnik.foodlivery.channel.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;

interface ChannelMemberRepository extends JpaRepository<ChannelMember, Long> {

  @Query("SELECT cm.channelId FROM ChannelMember cm WHERE cm.memberId = :userId")
  Collection<Long> findChannelIdsAllByUserId(@Param("userId") long userId);

  Collection<ChannelMember> findByChannelId(long channelId);

  Optional<ChannelMember> findByChannelIdAndMemberId(long channelId, long memberId);

}
