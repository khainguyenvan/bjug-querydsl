package net.eusashead.bjugquerydsl.controller;

import java.util.Map;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class SkuSearchRequestHandlerMethodArgumentResolver implements
HandlerMethodArgumentResolver {

	private static final String PRICE_MIN = "price.min";
	private static final String PRICE_MAX = "price.max";
	private static final String ATTR_PREFIX = "attr.";

	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer container, NativeWebRequest request,
			WebDataBinderFactory factory) throws Exception {

		// Create request builder
		SkuSearchRequestBuilder builder = new SkuSearchRequestBuilder();

		// Get minimum price if set
		if (assertParameter(PRICE_MIN, request)) {
			Double minPrice = Double.valueOf(request.getParameterValues(PRICE_MIN)[0]);
			builder.minPrice(minPrice);
		}

		// Get max price if set
		if (assertParameter(PRICE_MAX, request)) {
			Double maxPrice = Double.valueOf(request.getParameterValues(PRICE_MAX)[0]);
			builder.maxPrice(maxPrice);
		}
		
		// Get attributes map
		Map<String, String[]> attrs = request.getParameterMap();
		for (String name : attrs.keySet()) {
			if (name.startsWith(ATTR_PREFIX)) {
				if (assertParameter(name, request)) {
					builder.attribute(name.substring(ATTR_PREFIX.length()), attrs.get(name));
				}
			}
		}

		// return the request
		return builder.build();
	}

	private boolean assertParameter(String parameter, NativeWebRequest request) {
		String[] parameterValues = request.getParameterValues(parameter);
		return (parameterValues != null) && (parameterValues.length >= 1);
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return SkuSearchRequest.class.equals(parameter.getParameterType());
	}

}
