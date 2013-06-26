package net.eusashead.bjugquerydsl.hateoas;


import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runners.JUnit4;

import org.junit.runner.RunWith;
import org.springframework.core.MethodParameter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.theoryinpractise.halbuilder.DefaultRepresentationFactory;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

@RunWith(JUnit4.class)
public class HalPageResponseEntityBuilderHandlerMethodArgumentResolverTest {

	private RepresentationFactory representationFactory = new DefaultRepresentationFactory();
	private RepresentationConverter converter = new RepresentationConverterImpl(representationFactory, new SimplePropertyConverterImpl());

	@Test
	public void testSupport() throws Exception {
		HalPageResponseEntityBuilderHandlerMethodArgumentResolver resolver = new HalPageResponseEntityBuilderHandlerMethodArgumentResolver(representationFactory, converter);
		MethodParameter parameter = mockMethodParameter();
		Assert.assertTrue(resolver.supportsParameter(parameter));
	}

	@Test
	public void testResolve() throws Exception {
		HalPageResponseEntityBuilderHandlerMethodArgumentResolver resolver = new HalPageResponseEntityBuilderHandlerMethodArgumentResolver(representationFactory, converter);
		MethodParameter parameter = mockMethodParameter();
		ModelAndViewContainer container = mock(ModelAndViewContainer.class);
		NativeWebRequest request = mockRequest();
		WebDataBinderFactory factory = mock(WebDataBinderFactory.class);
		Object resolved = resolver.resolveArgument(parameter, container , request , factory);
		Assert.assertEquals(HalPageResponseEntityBuilder.class, resolved.getClass());
	}

	private NativeWebRequest mockRequest() {
		MockHttpServletRequest nativeRequest = new MockHttpServletRequest("GET", "http://www.domain.com/path/to/content");
		NativeWebRequest request = mock(NativeWebRequest.class);
		doReturn(nativeRequest).when(request).getNativeRequest(HttpServletRequest.class);
		return request;
	}
	
	private MethodParameter mockMethodParameter() {
		MethodParameter parameter = mock(MethodParameter.class);
		doReturn(HalPageResponseEntityBuilder.class).when(parameter).getParameterType();
		return parameter;
	}
	
}
