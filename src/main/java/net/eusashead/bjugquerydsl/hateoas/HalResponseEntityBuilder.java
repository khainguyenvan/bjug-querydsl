package net.eusashead.bjugquerydsl.hateoas;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.util.UriTemplate;

import com.theoryinpractise.halbuilder.api.ReadableRepresentation;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

/**
 * Simple class to build
 * a HAL representation
 * response and set 
 * etag/last-modified/location 
 * headers as required
 * @author patrickvk
 *
 */
public class HalResponseEntityBuilder extends BaseHalRepresentationBuilder implements ResponseEntityBuilder<ReadableRepresentation> {

	public HalResponseEntityBuilder(RepresentationFactory representationFactory, RepresentationConverter converter, HttpServletRequest request, String fieldVariable) {
		super(representationFactory, converter, request, fieldVariable);
	}
	
	/**
	 * Add the supplied property to 
	 * the representation
	 * @param name
	 * @param value
	 * @return
	 */
	public HalResponseEntityBuilder withProperty(String name, Object value) {
		converter.addProperty(representation, name, value);
		return this;
	}
	
	/**
	 * Add the supplied {@link Representation} to 
	 * the representation as an embedded resource
	 * @param rel
	 * @param value
	 * @return
	 */
	public HalResponseEntityBuilder withRepresentation(String rel, Object value) {
		converter.addRepresentation(representation, rel, value);
		return this;
	}
	
	/**
	 * Add the supplied {@link Representation} to 
	 * the representation as an embedded resource
	 * with the supplied {@link UriTemplate}
	 * @param rel
	 * @param value
	 * @return
	 */
	public HalResponseEntityBuilder withRepresentation(String rel, UriTemplate uriTemplate, Object value) {
		converter.addRepresentation(representation, rel, uriTemplate, value);
		return this;
	}

	/**
	 * Add all bean properties
	 * from the supplied bean
	 * to the representation
	 * @param value
	 * @return
	 */
	public ResponseEntityBuilder<ReadableRepresentation> withBean(Object value, String... includeFields) {
		String[] fields = requestedFields == null ? includeFields : requestedFields;
		this.representation = converter.convert(value, fields);
		return this;
	}

}
