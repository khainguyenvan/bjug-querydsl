package net.eusashead.bjugquerydsl.config;

import java.util.List;

import net.eusashead.bjugquerydsl.controller.FieldInitializer;
import net.eusashead.bjugquerydsl.controller.HibernateFieldInitializer;
import net.eusashead.bjugquerydsl.controller.SkuSearchRequestHandlerMethodArgumentResolver;
import net.eusashead.bjugquerydsl.hateoas.HalPageResponseEntityBuilderHandlerMethodArgumentResolver;
import net.eusashead.bjugquerydsl.hateoas.HalResponseEntityBuilderHandlerMethodArgumentResolver;
import net.eusashead.bjugquerydsl.hateoas.JpaEntityResourceMetadataExtractor;
import net.eusashead.bjugquerydsl.hateoas.RepresentationConverter;
import net.eusashead.bjugquerydsl.hateoas.RepresentationConverterImpl;
import net.eusashead.bjugquerydsl.hateoas.SimplePropertyConverterImpl;
import net.eusashead.hateoas.converter.hal.HalHttpMessageConverter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.theoryinpractise.halbuilder.DefaultRepresentationFactory;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

@Configuration
@Import(JpaConfig.class)
@EnableAspectJAutoProxy(proxyTargetClass=true)
@ComponentScan(basePackageClasses=net.eusashead.bjugquerydsl.controller.Marker.class)
public class WebConfig extends WebMvcConfigurationSupport {
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport#configureMessageConverters(java.util.List)
	 */
	@Override
	public void configureMessageConverters(
			List<HttpMessageConverter<?>> converters) {
		converters.add(halConverter());
		converters.add(jsonConverter());
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport#addArgumentResolvers(java.util.List)
	 */
	@Override
	protected void addArgumentResolvers(
			List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(pageableResolver());
		argumentResolvers.add(skuSearchResolver());
		argumentResolvers.add(halResponseEntityBuilderResolver());
		argumentResolvers.add(halPageResponseEntityBuilderResolver());
		super.addArgumentResolvers(argumentResolvers);
	}

	/**
	 * Set up a {@link PageableHandlerMethodArgumentResolver}
	 * to automatically extract paging parameters from a
	 * request
	 * @return
	 */
	@Bean
	public PageableHandlerMethodArgumentResolver pageableResolver() {
		PageableHandlerMethodArgumentResolver pageArgResolver = new PageableHandlerMethodArgumentResolver();
		Pageable fallback = new PageRequest(0, 20);
		pageArgResolver.setFallbackPageable(fallback);
		return pageArgResolver;
	}
	
	/**
	 * Set up a {@link PageableHandlerMethodArgumentResolver}
	 * to automatically extract paging parameters from a
	 * request
	 * @return
	 */
	@Bean
	public SkuSearchRequestHandlerMethodArgumentResolver skuSearchResolver() {
		SkuSearchRequestHandlerMethodArgumentResolver argResolver = new SkuSearchRequestHandlerMethodArgumentResolver();
		return argResolver;
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

	/**
	 * JAXB converter
	 * @return
	 */
	@Bean
	public HttpMessageConverter<Object> jaxbConverter() {
		Jaxb2RootElementHttpMessageConverter converter = new Jaxb2RootElementHttpMessageConverter();
		return converter;
	}


	/**
	 * Configure a Jackson {@link ObjectMapper}
	 * with the {@link Hibernate4Module} so 
	 * that JPA objects are correctly 
	 * serialized (e.g. lazy associations 
	 * aren't automatically serialized)
	 * @return configured {@link ObjectMapper}
	 */
	@Bean(name="jsonMapper")
	public ObjectMapper jsonMapper() {
		ObjectMapper mapper = new ObjectMapper();
		Hibernate4Module hm = new Hibernate4Module();
		hm.configure(Hibernate4Module.Feature.FORCE_LAZY_LOADING, false);
		mapper.registerModule(hm);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.setSerializationInclusion(Include.NON_NULL); // This doesn't work with Hibernate4Module :-(
		return mapper;
	}

	@Bean
	public FieldInitializer fieldInitializer() {
		return new HibernateFieldInitializer();
	}
	
	@Bean
	public RepresentationFactory representationFactory() {
		return new DefaultRepresentationFactory();
	}
	
	@Bean 
	public HalResponseEntityBuilderHandlerMethodArgumentResolver halResponseEntityBuilderResolver() {
		return new HalResponseEntityBuilderHandlerMethodArgumentResolver(representationFactory(), representationConverter(), "fields");
	}
	
	@Bean 
	public HalPageResponseEntityBuilderHandlerMethodArgumentResolver halPageResponseEntityBuilderResolver() {
		return new HalPageResponseEntityBuilderHandlerMethodArgumentResolver(representationFactory(), representationConverter(), "fields");
	}
	
	@Bean
	public RepresentationConverter representationConverter() {
		RepresentationConverterImpl representationConverter = new RepresentationConverterImpl(representationFactory(), new SimplePropertyConverterImpl());
		representationConverter.registerExtractor(new JpaEntityResourceMetadataExtractor());
		return representationConverter;
	}
	
	@Bean 
	public HalHttpMessageConverter halConverter() {
		return new HalHttpMessageConverter(representationFactory());
	}
	
}
