package com.demosp3security.config;

import java.lang.reflect.Type;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.demosp3security.dto.FileModelDTO;
import com.demosp3security.dto.FileModelDTOV;

@Configuration
@EnableWebMvc
public class MultipartConfig implements WebMvcConfigurer {

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new Converter());
		converters.add(new ConverterV());
	}
}

class Converter extends MappingJackson2HttpMessageConverter {

	@Override
	public boolean canRead(Type type, Class<?> contextClass, MediaType mediaType) {
		return type.getTypeName().equals(FileModelDTO.class.getName());
	}
}

class ConverterV extends MappingJackson2HttpMessageConverter {

	@Override
	public boolean canRead(Type type, Class<?> contextClass, MediaType mediaType) {
		return type.getTypeName().equals(FileModelDTOV.class.getName());
	}
}
