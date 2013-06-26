package net.eusashead.bjugquerydsl.hateoas;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
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
public class HalPageResponseEntityBuilder extends BaseHalRepresentationBuilder implements ResponseEntityBuilder<ReadableRepresentation> {

	public HalPageResponseEntityBuilder(RepresentationFactory representationFactory, RepresentationConverter converter, HttpServletRequest request, String fieldVariable) {
		super(representationFactory, converter, request, fieldVariable);
	}

	/**
	 * Add all bean properties
	 * from the supplied bean
	 * to the representation
	 * @param value
	 * @return
	 */
	public ResponseEntityBuilder<ReadableRepresentation> withPage(Page<?> value, String uriTemplate, String... includeFields) {
		String[] fields = requestedFields == null ? includeFields : requestedFields;

		// Extract page data such as size, page number
		representation.withProperty("size", value.getSize());
		representation.withProperty("number", value.getNumber());
		representation.withProperty("numberOfElements", value.getNumberOfElements());
		representation.withProperty("totalElements", value.getTotalElements());

		// Next/back links
		if (value.hasNextPage()) {
			buildNextLink(representation, request);
		}
		if (value.hasPreviousPage()) {
			buildPreviousLink(representation, request);
		}

		// Build the content of the page
		for (Object object : value.getContent()) {
			Representation content = converter.convert(object, new UriTemplate(uriTemplate), fields);
			this.representation.withRepresentation("content", content);
		}
		return this;
	}

	private void buildNextLink(Representation representation,
			HttpServletRequest request) {
		Map<String, String[]> params = modifyPageNumber(request, 1);
		String link = buildLink(params);
		representation.withLink("next", link);
	}

	private void buildPreviousLink(Representation representation,
			HttpServletRequest request) {
		Map<String, String[]> params = modifyPageNumber(request, -1);
		String link = buildLink(params);
		representation.withLink("previous", link);
	}
	
	private String buildLink(Map<String, String[]> params) {
		String queryString = buildQueryString(params);
		System.out.println(queryString);
		String link = String.format("%s?%s", request.getRequestURI(), queryString);
		return link;
	}
	
	private String buildQueryString(Map<String, String[]> params) {
		StringBuilder builder = new StringBuilder();
		for (String key : params.keySet()) {
			for (String val : params.get(key)) {
				builder.append(key);
				builder.append("=");
				builder.append(val);
				builder.append("&");
			}
		}
		if (builder.lastIndexOf("&") == (builder.length() - 1)) {
			builder.deleteCharAt(builder.length() - 1);
		}
		return builder.toString();
	}

	/**
	 * Get the existing request 
	 * parameters and increment
	 * the page parameter (or decrement)
	 * @param request
	 * @param modifier
	 * @return
	 */
	private Map<String, String[]> modifyPageNumber(HttpServletRequest request, int modifier) {
		Map<String, String[]> oldParams = request.getParameterMap();
		Map<String, String[]> newParams = new HashMap<String, String[]>();
		for (String key : oldParams.keySet()) {
			String[] value = oldParams.get(key);
			if (key.equals("page")) {
				int page = Integer.parseInt(value[0]) + modifier;
				newParams.put("page", new String[]{Integer.valueOf(page).toString()});
			} else {
				newParams.put(key, value);
			}
		}
		return newParams;
	}

}
