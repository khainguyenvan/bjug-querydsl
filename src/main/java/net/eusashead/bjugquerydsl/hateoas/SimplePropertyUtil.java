package net.eusashead.bjugquerydsl.hateoas;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.ClassUtils;

public class SimplePropertyUtil {
	
	public static Boolean isSimpleProperty(Class<?> clazz) {
		if (clazz.isPrimitive()) {
			return true;
		} else if (ClassUtils.wrapperToPrimitive(clazz) != null) {
			return true;
		} else if (String.class.isAssignableFrom(clazz)) {
			return true;
		} else if (Date.class.isAssignableFrom(clazz)) {
			return true;
		} else if (BigDecimal.class.isAssignableFrom(clazz)) {
			return true;
		}
		return false;
	}
	
}
