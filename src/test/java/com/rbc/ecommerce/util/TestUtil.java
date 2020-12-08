package com.rbc.ecommerce.util;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;

import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class TestUtil {
	public static final ObjectMapper OM = new ObjectMapper()
			.registerModule(new JavaTimeModule())
			.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"))
			.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	
	public static String writeValue(Object value) throws IOException {
		try(StringWriter sw = new StringWriter()) {
			OM.writeValue(sw, value);
			return sw.toString();
		}
	}
	
	public static <T> T readValue(MvcResult data, TypeReference<T> typeReference) throws IOException {
		String res = data.getResponse().getContentAsString();
		return OM.readValue(res, typeReference);
	}
}
