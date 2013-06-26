package net.eusashead.bjugquerydsl.hateoas;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.util.UriTemplate;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

public class RepresentationConverterImpl implements RepresentationConverter {

	private final RepresentationFactory representationFactory;

	private final List<ResourceMetadataExtractor> extractors = new ArrayList<ResourceMetadataExtractor>();

	private final SimplePropertyConverter simplePropertyConverter;

	public RepresentationConverterImpl(RepresentationFactory representationFactory, SimplePropertyConverter simplePropertyConverter) {
		this.representationFactory = representationFactory;
		this.simplePropertyConverter = simplePropertyConverter;
	}

	public void registerExtractor(ResourceMetadataExtractor extractor) {
		this.extractors.add(extractor);
	}

	/* (non-Javadoc)
	 * @see net.eusashead.bjugquerydsl.hateoas.RepresentationConverter#convert(java.lang.Object)
	 */
	@Override
	public Representation convert(Object bean) {
		return convert(bean, null, true);
	}

	/* (non-Javadoc)
	 * @see net.eusashead.bjugquerydsl.hateoas.RepresentationConverter#convert(java.lang.Object, java.lang.String)
	 */
	@Override
	public Representation convert(Object bean, String...properties) {
		return convert(bean, null, true, properties);
	}

	/* (non-Javadoc)
	 * @see net.eusashead.bjugquerydsl.hateoas.RepresentationConverter#convert(java.lang.Object, org.springframework.web.util.UriTemplate, java.lang.String)
	 */
	@Override
	public Representation convert(Object bean, UriTemplate uriTemplate, String...properties) {
		return convert(bean, uriTemplate, true, properties);
	}

	private Representation convert(Object bean, UriTemplate uriTemplate,
			boolean recursive, String... properties) {

		// Validate arguments
		if (bean == null) {
			throw new IllegalArgumentException("Cannot pass null bean to convert()");
		}
		
		Class<?> target = bean.getClass();
		
		// Get the ResourceMetadataExtractor
		ResourceMetadataExtractor extractor = findMatchingExtractor(target);

		// Get the metadata
		ResourceMetadata metadata = extractor.extract(target);

		// Get the URI template from metadata or argument
		uriTemplate = uriTemplate != null ? uriTemplate : metadata.getUriTemplate();

		// Get the URI from the template
		String uri = null;
		if (uriTemplate != null) {
			uri = uriTemplate.expand(metadata.getUriVariables(bean)).toString();
		}

		// Create the representation 
		Representation representation = representationFactory.newRepresentation(uri);

		// Determine the scope
		List<Property> scope;
		if (properties == null || properties.length == 0) {
			scope = metadata.getAllProperties();
		} else {
			scope = metadata.getPropertiesByName(properties); 
		}

		// Copy the properties
		for (Property property : scope) {
			
			// Get the property value
			Object value = property.getValue(bean);
			
			// Copy if not null
			if (value != null) {
				
				// Is it an embedded resource or a property
				if (EmbeddedResource.class.isAssignableFrom(property.getClass())) {
					
					// Only copy embedded if recursive
					if (recursive) {
						
						// Add the embedded resource
						EmbeddedResource embedded = EmbeddedResource.class.cast(property);
						addRepresentation(representation, embedded.getName(), embedded.getUriTemplate(), value);
					}
				} else {
					
					// It's a simple property, add it
					addProperty(representation, property.getName(), value);
				}
			}
		}
		return representation;
	}

	/**
	 * Find a {@link ResourceMetadataExtractor}
	 * that can extract {@link ResourceMetadata}
	 * for the given {@link Class}
	 * @param target
	 * @return
	 */
	private ResourceMetadataExtractor findMatchingExtractor(
			Class<? extends Object> target) {
		for (ResourceMetadataExtractor extractor : extractors) {
			if (extractor.canExtract(target)) {
				return extractor;
			}
		}
		throw new RuntimeException(String.format("No matching ResourceMetadataExtractor for class %s", target));
	}

	/* (non-Javadoc)
	 * @see net.eusashead.bjugquerydsl.hateoas.RepresentationConverter#addProperty(com.theoryinpractise.halbuilder.api.Representation, java.lang.String, java.lang.Object)
	 */
	@Override
	public void addProperty(Representation representation, String name, Object value) {
		if (SimplePropertyUtil.isSimpleProperty(value.getClass())) {
			Object converted = simplePropertyConverter.convert(value, String.class);
			representation.withProperty(name, converted);
		} else {
			throw new IllegalArgumentException(String.format("Property %s of type %s is not a simple property", name, value.getClass()));
		}
	}

	/* (non-Javadoc)
	 * @see net.eusashead.bjugquerydsl.hateoas.RepresentationConverter#addRepresentation(com.theoryinpractise.halbuilder.api.Representation, java.lang.String, java.lang.Object)
	 */
	@Override
	public void addRepresentation(Representation representation, String rel, Object value) {
		addRepresentation(representation, rel, null, value);
	}	

	/* (non-Javadoc)
	 * @see net.eusashead.bjugquerydsl.hateoas.RepresentationConverter#addRepresentation(com.theoryinpractise.halbuilder.api.Representation, java.lang.String, org.springframework.web.util.UriTemplate, java.lang.Object)
	 */
	@Override
	public void addRepresentation(Representation representation, String rel, UriTemplate uriTemplate, Object value) {

		if (!SimplePropertyUtil.isSimpleProperty(value.getClass())) {
			// Handle collections
			if (Iterable.class.isAssignableFrom(value.getClass())) {
				for (Object obj : Iterable.class.cast(value)) {
					// Create a representation
					Representation child = this.convert(obj, uriTemplate, false);
					representation.withRepresentation(rel, child);
				}
			} else {
				// Create a representation
				Representation child = this.convert(value, uriTemplate, false);
				representation.withRepresentation(rel, child);
			}
		} else {
			throw new IllegalArgumentException(String.format("Property %s of type %s is not a representation type", rel, value.getClass()));
		}
	}

}
