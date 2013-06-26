package net.eusashead.bjugquerydsl.hateoas;

import java.lang.reflect.Method;

public interface Property {
	
	String getName();
	
	Method getAccessor();
	
	Class<?> getType();

	Object getValue(Object bean);

}
