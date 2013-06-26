package net.eusashead.bjugquerydsl.hateoas;

import java.lang.reflect.Method;

public class SimpleProperty extends BaseProperty implements Property {

	public SimpleProperty(String name, Method accessor) {
		super(name, accessor);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accessor == null) ? 0 : accessor.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		SimpleProperty other = (SimpleProperty) obj;
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
		return true;
	}

	@Override
	public String toString() {
		return "SimpleProperty [name=" + name + "]";
	}

	@Override
	protected void validateAccessor(Method accessor) {
		if (!SimplePropertyUtil.isSimpleProperty(accessor.getReturnType())){
			throw new IllegalArgumentException(String.format("Type %s is not a simple property", accessor.getReturnType()));
		}
	}

}
