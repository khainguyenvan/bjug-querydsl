package net.eusashead.bjugquerydsl.hateoas;

import java.util.List;
import java.util.Map;

import org.springframework.web.util.UriTemplate;

public interface ResourceMetadata {

	UriTemplate getUriTemplate();
	List<IdentityProperty> getIdentityProperties();
	List<SimpleProperty> getSimpleProperties();
	List<EmbeddedResource> getEmbeddedResources();
	List<Property> getAllProperties();
	List<Property> getPropertiesByName(String... properties);
	Property getPropertyByName(String property);
	Map<String, Object> getUriVariables(Object bean);

}
