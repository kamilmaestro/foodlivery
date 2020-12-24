package com.kamilmarnik.foodlivery.channel.infrastructure;

import com.kamilmarnik.foodlivery.channel.domain.ChannelFacade;
import com.kamilmarnik.foodlivery.channel.dto.AddChannelDto;
import com.kamilmarnik.foodlivery.channel.dto.ChannelDto;
import com.kamilmarnik.foodlivery.channel.dto.ChannelMemberDto;
import com.kamilmarnik.foodlivery.image.dto.ImageUploadedDto;
import com.kamilmarnik.foodlivery.infrastructure.PageInfo;
import com.kamilmarnik.foodlivery.supplier.dto.SupplierDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

@RequestMapping("/channel")
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class ChannelController {

  ChannelFacade channelFacade;

  @Autowired
  ChannelController(ChannelFacade channelFacade) {
    this.channelFacade = channelFacade;
  }

  @PostMapping(value = "/", consumes = MediaType.TEXT_PLAIN_VALUE)
  ResponseEntity<ChannelDto> createChannel(@RequestBody String name) {
    return ResponseEntity.ok(channelFacade.createChannel(name));
  }

  @PostMapping("/{channelId}/invitation")
  ResponseEntity<String> generateInvitation(@PathVariable long channelId) {
    return ResponseEntity.ok(channelFacade.generateInvitation(channelId));
  }

  @PostMapping(value = "/join", consumes = MediaType.TEXT_PLAIN_VALUE)
  ResponseEntity<ChannelMemberDto> joinChannel(@RequestBody String invitation) {
    return ResponseEntity.ok(channelFacade.joinChannel(invitation));
  }

  @GetMapping("/")
  public ResponseEntity<Page<ChannelDto>> findChannelsByUserId(@ModelAttribute PageInfo pageInfo) {
    return ResponseEntity.ok(channelFacade.findChannelsByUserId(pageInfo));
  }

  @GetMapping("/{channelId}/members")
  public ResponseEntity<Collection<ChannelMemberDto>> findChannelMembers(@PathVariable long channelId) {
    return ResponseEntity.ok(channelFacade.findChannelMembers(channelId));
  }

  @PostMapping("/ids")
  public ResponseEntity<Page<ChannelDto>> findChannelsByIds(@RequestBody Collection<Long> channelIds,
                                                            @ModelAttribute PageInfo pageInfo) {
    return ResponseEntity.ok(channelFacade.findChannelsByIds(channelIds, pageInfo));
  }

}
