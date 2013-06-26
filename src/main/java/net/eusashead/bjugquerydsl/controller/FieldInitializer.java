package net.eusashead.bjugquerydsl.controller;

import com.mysema.query.types.Path;

public interface FieldInitializer {
	
	/**
	 * Initializes all bean
	 * fields in the entity
	 * @param entity
	 */
	void initializeEntity(Object entity);
	
	/**
	 * Initializes just the fields
	 * specified
	 * @param entity
	 * @param field
	 */
	void initializeEntity(Object entity, Path<?>... field);
	
	/**
	 * Initializes a collection
	 * of entities with just the 
	 * fields specified
	 * @param entities
	 * @param field
	 */
	void initializeCollection(Iterable<?> entities, Path<?>... field);

}
