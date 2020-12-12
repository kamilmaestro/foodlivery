package com.kamilmarnik.foodlivery.payment.domain

import com.kamilmarnik.foodlivery.SecurityContextProvider
import com.kamilmarnik.foodlivery.channel.domain.BaseChannelSpec
import com.kamilmarnik.foodlivery.order.domain.BaseOrderSpec
import com.kamilmarnik.foodlivery.order.domain.OrderConfiguration
import com.kamilmarnik.foodlivery.order.domain.OrderExpirationConfig
import com.kamilmarnik.foodlivery.order.domain.OrderFacade
import com.kamilmarnik.foodlivery.samples.SampleChannels
import com.kamilmarnik.foodlivery.samples.SampleFood
import com.kamilmarnik.foodlivery.samples.SampleOrders
import com.kamilmarnik.foodlivery.samples.SampleSuppliers
import com.kamilmarnik.foodlivery.samples.SampleUsers
import com.kamilmarnik.foodlivery.supplier.domain.SupplierConfiguration
import com.kamilmarnik.foodlivery.supplier.domain.SupplierFacade
import com.kamilmarnik.foodlivery.utils.FixedTimeProvider
import com.kamilmarnik.foodlivery.utils.TimeProvider
import org.springframework.context.ApplicationEventPublisher

abstract class BasePaymentSpec extends BaseChannelSpec implements SampleUsers, SampleSuppliers, SampleOrders, SampleFood, SampleChannels, SecurityContextProvider {

  ApplicationEventPublisher eventPublisher = Mock()
  SupplierFacade supplierFacade = new SupplierConfiguration().supplierFacade()
  OrderExpirationConfig expirationConfig = new OrderExpirationConfig()
  TimeProvider timeProvider = new FixedTimeProvider()
  OrderFacade orderFacade = new OrderConfiguration().orderFacade(supplierFacade, expirationConfig, timeProvider, eventPublisher)
  PaymentFacade paymentFacade = new PaymentConfiguration().paymentFacade()

  static Long CHANNEL_ID = null

  def setup() {
    logInUser(JOHN)
    expirationConfig.setExpirationAfterMinutes(180)
    CHANNEL_ID = channelFacade.createChannel(KRAKOW.name).id
  }

}
