package net.eusashead.bjugquerydsl.hateoas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.util.UriTemplate;

public class ResourceMetadataImpl implements ResourceMetadata {

	private final UriTemplate uriTemplate;
	private final List<IdentityProperty> identityProperties;
	private final List<SimpleProperty> simpleProperties;
	private final List<EmbeddedResource> embeddedResources;
	private final Map<String, Property> allProperties = new HashMap<String, Property>();
	
	public ResourceMetadataImpl(UriTemplate uriTemplate,
			List<IdentityProperty> identityProperties,
			List<SimpleProperty> simpleProperties,
			List<EmbeddedResource> embeddedResources) {
		super();
		this.uriTemplate = uriTemplate;
		this.identityProperties = Collections.unmodifiableList(identityProperties);
		this.simpleProperties = Collections.unmodifiableList(simpleProperties);
		this.embeddedResources = Collections.unmodifiableList(embeddedResources);
		this.addPropertiesToMap(identityProperties);
		this.addPropertiesToMap(simpleProperties);
		this.addPropertiesToMap(embeddedResources);
	}
	
	private void addPropertiesToMap(List<? extends Property> properties) {
		for (Property property : properties) {
			this.allProperties.put(property.getName(), property);
		}
	}

	@Override
	public UriTemplate getUriTemplate() {
		return this.uriTemplate;
	}

	@Override
	public List<IdentityProperty> getIdentityProperties() {
		return this.identityProperties;
	}

	@Override
	public List<SimpleProperty> getSimpleProperties() {
		return this.simpleProperties;
	}

	@Override
	public List<EmbeddedResource> getEmbeddedResources() {
		return this.embeddedResources;
	}

	@Override
	public List<Property> getAllProperties() {
		return Collections.unmodifiableList(new ArrayList<Property>(this.allProperties.values()));
	}
	
	@Override
	public Property getPropertyByName(String property) {
		return allProperties.get(property);
	}

	@Override
	public List<Property> getPropertiesByName(String... properties) {
		List<Property> matches = new ArrayList<Property>();
		for (String property : properties) {
			Property prop = this.allProperties.get(property);
			if (prop != null) {
				matches.add(prop);
			}
		}
		return matches;
	}

	@Override
	public Map<String, Object> getUriVariables(Object bean) {
		Map<String, Object> uriVariables = new HashMap<String, Object>();
		for (IdentityProperty id : getIdentityProperties()) {
			Object value = id.getValue(bean);
			uriVariables.put(id.getName(), value);
		}
		return uriVariables;
	}

}
