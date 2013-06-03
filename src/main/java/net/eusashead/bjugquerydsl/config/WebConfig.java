package net.eusashead.bjugquerydsl.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.support.DomainClassConverter;
import org.springframework.data.web.PageableArgumentResolver;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.ServletWebArgumentResolverAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

@Configuration
@Import(JpaConfig.class)
@EnableAspectJAutoProxy(proxyTargetClass=true)
@ComponentScan(basePackageClasses=net.eusashead.bjugquerydsl.controller.Marker.class)
public class WebConfig extends WebMvcConfigurationSupport {
	@Override
	public void configureMessageConverters(
			List<HttpMessageConverter<?>> converters) {
		converters.add(jsonConverter());
		converters.add(jaxbConverter());
		
	}

	@Override
	protected void addArgumentResolvers(
			List<HandlerMethodArgumentResolver> argumentResolvers) {
		PageableArgumentResolver resolver = new PageableArgumentResolver();
		resolver.setFallbackPagable(new PageRequest(1, 20));
		argumentResolvers.add(new ServletWebArgumentResolverAdapter(resolver));
		super.addArgumentResolvers(argumentResolvers);
	}
	
	@Bean
	public DomainClassConverter<?> domainClassConverter() {
		return new DomainClassConverter<FormattingConversionService>(mvcConversionService());
	}


	/**
	 * Set up the Jackson JSON
	 * {@link HttpMessageConverter}
	 * @return
	 */
	@Bean
	public HttpMessageConverter<Object> jsonConverter() {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setObjectMapper(jsonMapper());
		return converter;
	}

	@Bean
	public HttpMessageConverter<Object> jaxbConverter() {
		Jaxb2RootElementHttpMessageConverter converter = new Jaxb2RootElementHttpMessageConverter();
		return converter;
	}
	

	/**
	 * Configure a Jackson {@link ObjectMapper}
	 * with the {@link Hibernate4Module} so 
	 * that JPA objects are correctly 
	 * serialized
	 * @return configured {@link ObjectMapper}
	 */
	@Bean(name="jsonMapper")
	public ObjectMapper jsonMapper() {
		ObjectMapper mapper = new ObjectMapper();
		Hibernate4Module hm = new Hibernate4Module();
		hm.configure(Hibernate4Module.Feature.FORCE_LAZY_LOADING, false);
		mapper.registerModule(hm);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		return mapper;
	}
}
