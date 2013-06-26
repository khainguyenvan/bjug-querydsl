package net.eusashead.bjugquerydsl.hateoas;

import java.lang.reflect.Method;

import org.springframework.web.util.UriTemplate;

public class EmbeddedResource extends BaseProperty {
	
	private UriTemplate uriTemplate;

	public EmbeddedResource(String name, UriTemplate uriTemplate, Method accessor) {
		super(name, accessor);
		this.uriTemplate = uriTemplate;
	}
	
	public UriTemplate getUriTemplate() {
		return this.uriTemplate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accessor == null) ? 0 : accessor.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((uriTemplate == null) ? 0 : uriTemplate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmbeddedResource other = (EmbeddedResource) obj;
		if (accessor == null) {
			if (other.accessor != null)
				return false;
		} else if (!accessor.equals(other.accessor))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (uriTemplate == null) {
			if (other.uriTemplate != null)
				return false;
		} else if (!uriTemplate.equals(other.uriTemplate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EmbeddedResource [name=" + name + "]";
	}
	
	@Override
	protected void validateAccessor(Method accessor) {
		if (SimplePropertyUtil.isSimpleProperty(accessor.getReturnType())){
			throw new IllegalArgumentException(String.format("Type %s is a simple property", accessor.getReturnType()));
		}
	}

}
