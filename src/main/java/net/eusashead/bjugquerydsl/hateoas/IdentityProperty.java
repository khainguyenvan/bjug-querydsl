package net.eusashead.bjugquerydsl.hateoas;

import java.lang.reflect.Method;

public class IdentityProperty extends SimpleProperty {

	public IdentityProperty(String name, Method accessor) {
		super(name, accessor);
	}
	
	@Override
	public String toString() {
		return "IdentityProperty [name=" + name + "]";
	}

}
