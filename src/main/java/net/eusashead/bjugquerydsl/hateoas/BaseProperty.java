package net.eusashead.bjugquerydsl.hateoas;

import java.lang.reflect.Method;

public abstract class BaseProperty implements Property {
	
	protected final String name;
	protected final Method accessor;

	public BaseProperty(String name, Method accessor) {
		super();
		if (name == null || name.length() < 1 || accessor == null) {
			throw new IllegalArgumentException("Name and accessor are both required.");
		}
		this.validateAccessor(accessor);
		this.name = name;
		this.accessor = accessor;
	}
	
	/**
	 * Return false if the property
	 * is not valid, true if it is.
	 * @param accessor
	 * @return
	 */
	abstract protected void validateAccessor(Method accessor);

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Method getAccessor() {
		return this.accessor;
	}

	@Override
	public Class<?> getType() {
		return this.accessor.getReturnType();
	}

	@Override
	public Object getValue(Object bean) {
		// Validate the object
		if (bean == null) {
			throw new IllegalArgumentException("Can't get value from a null object");
		}
		if (!accessor.getDeclaringClass().isAssignableFrom(bean.getClass())) {
			throw new IllegalArgumentException(String.format("Can't get value of type %s with accessor from %s", bean.getClass(), accessor.getDeclaringClass()));
		}
		
		// Get the value
		try {
			return this.getAccessor().invoke(bean);
		} catch (Exception e) {
			throw new IllegalArgumentException(String.format("Can't access property %s on bean of type %s.", this.name, bean.getClass()), e);
		}
	}

}