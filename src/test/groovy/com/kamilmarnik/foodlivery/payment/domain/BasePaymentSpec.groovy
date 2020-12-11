package com.kamilmarnik.foodlivery.payment.domain

import com.kamilmarnik.foodlivery.SecurityContextProvider
import com.kamilmarnik.foodlivery.samples.SampleChannels
import com.kamilmarnik.foodlivery.samples.SampleFood
import com.kamilmarnik.foodlivery.samples.SampleOrders
import com.kamilmarnik.foodlivery.samples.SampleSuppliers
import com.kamilmarnik.foodlivery.samples.SampleUsers

abstract class BasePaymentSpec implements SampleUsers, SampleSuppliers, SampleOrders, SampleFood, SampleChannels, SecurityContextProvider {
}
