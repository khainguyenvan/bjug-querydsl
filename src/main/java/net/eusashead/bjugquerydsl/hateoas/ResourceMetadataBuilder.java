package net.eusashead.bjugquerydsl.hateoas;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.util.UriTemplate;

public class ResourceMetadataBuilder {
	
	private UriTemplate uriTemplate;
	private final List<IdentityProperty> identityProperties = new ArrayList<IdentityProperty>();
	private final List<SimpleProperty> simpleProperties = new ArrayList<SimpleProperty>();
	private final List<EmbeddedResource> embeddedResources = new ArrayList<EmbeddedResource>();

	public ResourceMetadataBuilder uriTemplate(String uriTemplate) {
		this.uriTemplate = new UriTemplate(uriTemplate);
		return this;
	}
	
	public ResourceMetadataBuilder uriTemplate(UriTemplate uriTemplate) {
		this.uriTemplate = uriTemplate;
		return this;
	}
	
	public ResourceMetadataBuilder identityProperty(String name, Method accessor) {
		this.identityProperties.add(new IdentityProperty(name, accessor));
		return this;
	}
	
	public ResourceMetadataBuilder simpleProperty(String name, Method accessor) {
		this.simpleProperties.add(new SimpleProperty(name, accessor));
		return this;
	}
	
	public ResourceMetadataBuilder embeddedResource(String name, Method accessor) {
		this.embeddedResources.add(new EmbeddedResource(name, null, accessor));
		return this;
	}
	
	public ResourceMetadataBuilder embeddedResource(String name, UriTemplate uriTemplate, Method accessor) {
		this.embeddedResources.add(new EmbeddedResource(name, uriTemplate, accessor));
		return this;
	}
	
	public ResourceMetadataBuilder embeddedResource(String name, String uriTemplate, Method accessor) {
		this.embeddedResources.add(new EmbeddedResource(name, new UriTemplate(uriTemplate), accessor));
		return this;
	}
	
	public ResourceMetadata build() {
		return new ResourceMetadataImpl(uriTemplate, identityProperties, simpleProperties, embeddedResources);
	}

}
