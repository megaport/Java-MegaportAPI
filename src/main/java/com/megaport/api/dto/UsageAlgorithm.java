/*
 * Copyright (c) 2012. Adam Wells.
 */

package com.megaport.api.dto;

public enum UsageAlgorithm {

	BYTES_TRANSFERRED, TIME_AT_RATE, RATE_PLAN,
	POST_PAID_FIXED, // post paid, but regardless of hours or speed - ports
	POST_PAID_HOURLY, // ie can be used for counting hours of use, regardless of speed - metro VXC maybe?
	POST_PAID_HOURLY_SPEED, // ie can be used for hours of use at a given speed - long haul VXC
	NOT_POST_PAID // ie not billed by a Usage Algorithm!

}
