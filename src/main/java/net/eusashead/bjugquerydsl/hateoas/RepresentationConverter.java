package net.eusashead.bjugquerydsl.hateoas;

import org.springframework.web.util.UriTemplate;

import com.theoryinpractise.halbuilder.api.Representation;

public interface RepresentationConverter {

	Representation convert(Object bean);

	Representation convert(Object bean, String... properties);

	Representation convert(Object bean, UriTemplate uriTemplate,
			String... properties);

	void addProperty(Representation representation, String name, Object value);

	void addRepresentation(Representation representation, String rel,
			Object value);

	void addRepresentation(Representation representation, String rel,
			UriTemplate uriTemplate, Object value);

}