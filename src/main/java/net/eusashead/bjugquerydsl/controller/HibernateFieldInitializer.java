package net.eusashead.bjugquerydsl.controller;

import java.beans.PropertyDescriptor;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import com.mysema.query.types.Path;

public class HibernateFieldInitializer implements FieldInitializer {

	@Override
	public void initializeEntity(Object entity) {
		for (PropertyDescriptor desc : PropertyUtils.getPropertyDescriptors(entity)) {
			initializeField(entity, desc.getName());
		}

	}

	@Override
	public void initializeEntity(Object entity, Path<?>... fields) {
		for (Path<?> field : fields) {
			// Get the field name
			String name = field.getMetadata().getName();
			
			// Initialize
			initializeField(entity, name);
		}

	}

	@Override
	public void initializeCollection(Iterable<?> entities, Path<?>... field) {
		for (Object entity : entities) {
			initializeEntity(entity, field);
		}
	}
	
	/**
	 * Access a field to initialize it
	 * with special treatment for collections
	 * @param entity
	 * @param name
	 */
	private void initializeField(Object entity, String name) {
		
		// Accessing the field initializes it
		Object value;
		try {
			value = BeanUtils.getProperty(entity, name);
			
			// Unless it's a collection
			if (Iterable.class.isAssignableFrom(value.getClass())) {
				// In which this will
				Iterable.class.cast(value).iterator();
			}
			
		} catch (Exception e) {
			throw new RuntimeException(String.format("Error accessing field %s in type %s.", name, entity.getClass()), e);
		}
		
	}

}
