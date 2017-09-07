package com.moredian.fishnet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@Configuration
public class SpringConfigure {

	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
	    MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
	    ObjectMapper objectMapper = new ObjectMapper();
	    /**
	     * 序列化成json时,将所有的long变成string
	     * 因为js中得数字类型不能包含所有的java long值
	     */
	    SimpleModule simpleModule = new SimpleModule();
	    simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
	    simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
	    objectMapper.registerModule(simpleModule);

	    jackson2HttpMessageConverter.setObjectMapper(objectMapper);
	    return jackson2HttpMessageConverter;
	}
	
}

