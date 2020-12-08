package com.rbc.ecommerce.util;

import java.util.UUID;

public class RequestIdService {
	public String requestId() {
		return UUID.randomUUID().toString();
	}
}
