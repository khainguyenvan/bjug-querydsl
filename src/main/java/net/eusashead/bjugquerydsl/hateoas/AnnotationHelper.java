package net.eusashead.bjugquerydsl.hateoas;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AnnotationHelper {
	
	public static List<Field> getFieldsWithAnnotation(Class<?> clazz, Class<? extends Annotation> annotation) {
		Field[] fields = clazz.getDeclaredFields();
		List<Field> annotated = new ArrayList<Field>();
		for (Field field : fields) {
			if (field.isAnnotationPresent(annotation)) {
				annotated.add(field);
			}
		}
		return annotated;
	}
	
	/**
	 * Check if a bean field
	 * or its accessor method
	 * has an annotation
	 * @param pd
	 * @param annotations
	 * @return
	 */
	public static boolean fieldOrPropertyAnnotated(PropertyDescriptor pd, Class<? extends Annotation>... annotations) {
		// What to return
		boolean has = false;
		
		try {
			// Get the getter... oh yeah!
			Method readMethod = pd.getReadMethod();
			
			Class<?> declaringClass = readMethod.getDeclaringClass();
			
			// Get the field for the property
			Field field = declaringClass.getDeclaredField(pd.getName());
			
			// Check each annotation
			for (Class<? extends Annotation> annotation : annotations) {
				if (readMethod.isAnnotationPresent(annotation) || field.isAnnotationPresent(annotation)) {
					has = true;
					break;
				}
			}
			
		} catch (SecurityException e) {
			throw new RuntimeException(String.format("Can't access field %s in class %s", pd.getName(), pd.getReadMethod().getDeclaringClass()),e);
		} catch (NoSuchFieldException e) {
			throw new IllegalArgumentException(String.format("Can't find field %s in class %s", pd.getName(), pd.getReadMethod().getDeclaringClass()),e);
		}
		return has;
	}

}
