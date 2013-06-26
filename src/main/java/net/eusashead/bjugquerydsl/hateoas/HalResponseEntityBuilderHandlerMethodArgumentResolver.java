package net.eusashead.bjugquerydsl.hateoas;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.theoryinpractise.halbuilder.api.RepresentationFactory;

/**
 * Enables a {@link HalResponseEntityBuilder} to
 * be injected as a method parameter in a 
 * Spring MVC controller, pre-populated
 * with the HTTP request and other variables
 * @author patrickvk
 *
 */
public class HalResponseEntityBuilderHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	private static final String FIELDS_PARAM = "fields";
	
	private final RepresentationFactory representationFactory;
	private final RepresentationConverter converter;
	private final String fieldsParam;

	/**
	 * Supply a {@link RepresentationFactory} and
	 * the name of the HTTP request variable 
	 * that users can use to specify the
	 * included fields in a request
	 * @param representationFactory
	 * @param fieldsParam
	 */
	public HalResponseEntityBuilderHandlerMethodArgumentResolver(
			RepresentationFactory representationFactory, RepresentationConverter converter, String fieldsParam) {
		super();
		this.representationFactory = representationFactory;
		this.converter = converter;
		this.fieldsParam = fieldsParam;
	}
	
	/**
	 * Specify the {@link RepresentationFactory} but
	 * use the default value for the HTTP request 
	 * variable name for specifying fields to include
	 * in a request
	 * @param representationFactory
	 */
	public HalResponseEntityBuilderHandlerMethodArgumentResolver(
			RepresentationFactory representationFactory, RepresentationConverter converter) {
		super();
		this.representationFactory = representationFactory;
		this.converter = converter;
		this.fieldsParam = FIELDS_PARAM;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.method.support.HandlerMethodArgumentResolver#resolveArgument(org.springframework.core.MethodParameter, org.springframework.web.method.support.ModelAndViewContainer, org.springframework.web.context.request.NativeWebRequest, org.springframework.web.bind.support.WebDataBinderFactory)
	 */
	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer container, NativeWebRequest request,
			WebDataBinderFactory factory) throws Exception {

		return new HalResponseEntityBuilder(
				representationFactory, converter, request.getNativeRequest(HttpServletRequest.class), fieldsParam);
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.method.support.HandlerMethodArgumentResolver#supportsParameter(org.springframework.core.MethodParameter)
	 */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return HalResponseEntityBuilder.class.equals(parameter.getParameterType());
	}

}
