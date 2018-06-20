/*
 * Copyright (c) 2012. Adam Wells.
 */

package com.megaport.api.dto;

public enum UsageAlgorithm {

	BYTES_TRANSFERRED, TIME_AT_RATE, RATE_PLAN,
	POST_PAID_FIXED, // post paid, but regardless of hours or speed - ports (not a product yet)
	POST_PAID_HOURLY, // new VXC pricing, mrc plus rate based charging
	POST_PAID_HOURLY_SPEED, // used for long haul VXC
	POST_PAID_HOURLY_SPEED_MCR,
	POST_PAID_HOURLY_SPEED_METRO_VXC,
	POST_PAID_HOURLY_SPEED_LONG_HAUL_VXC,
	NOT_POST_PAID // ie pre-paid, used for Ports, and legacy metro VXCs etc

}
