package net.eusashead.bjugquerydsl.hateoas;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

public class JpaEntityResourceMetadataExtractor implements
ResourceMetadataExtractor {

	private final ConcurrentMap<Class<?>, ResourceMetadata> cache = new ConcurrentHashMap<Class<?>, ResourceMetadata>();
	
	/* (non-Javadoc)
	 * @see net.eusashead.bjugquerydsl.hateoas.ResourceMetadataExtractor#extract(java.lang.Class)
	 */
	@Override
	public ResourceMetadata extract(Class<?> target) {
		
		// Get an ancestor with @Entity annotation
		Class<?> entity = getEntityClass(target);
		
		// Check cache for previously extracted metadata.
		ResourceMetadata metadata = cache.putIfAbsent(entity, buildResourceMetadata(entity));
		if (metadata == null) {
			metadata = cache.get(entity);
		}
		return metadata;
		
	}

	/**
	 * Because Hibernate and other JPA
	 * implementations tend to use proxies
	 * or modified bytecode, we need to check
	 * not just the returned object's class
	 * but also its ancestors (which the 
	 * proxy will extend).
	 * @param target
	 * @return
	 */
	private Class<?> getEntityClass(Class<?> target) {
		if (target == null) {
			return null;
		}
		if (target.isAnnotationPresent(Entity.class)) {
			return target;
		} else {
			return getEntityClass(target.getSuperclass());
		}
	}

	private ResourceMetadata buildResourceMetadata(Class<?> target) {
		// Check it's an @Entity (or one of its ancestors is)
		Class<?> entity = getEntityClass(target);
		if (entity == null) {
			throw new IllegalArgumentException(String.format("Class %s or its ancestors doesn't have an @Entity annotation.", target));
		}
		
		// Create a builder
		final ResourceMetadataBuilder builder = new ResourceMetadataBuilder();
		
		// Process the properties
		BeanInfo beanInfo;
		try {
			beanInfo = Introspector.getBeanInfo(entity);
			for (PropertyDescriptor property : beanInfo.getPropertyDescriptors()) {
				if (!property.getName().equals("class")) {
					extractPropertyMetadata(property, builder);
				}
			}
		} catch (IntrospectionException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}

		// Build the metadata
		return builder.build();
	}

	/**
	 * Get the property metadata
	 * for the supplied property
	 * @param property
	 * @param builder
	 */
	@SuppressWarnings("unchecked")
	private void extractPropertyMetadata(PropertyDescriptor property, ResourceMetadataBuilder builder) {
		// Get the field for the property
		Method accessor = property.getReadMethod();
		
		// Get the property name
		String name = property.getName();

		// Is it an identity field?
		if (AnnotationHelper.fieldOrPropertyAnnotated(property, Id.class)) {
			builder.identityProperty(name, accessor);
		}

		// Is it a relation field (and therefore embedded)?
		else if (AnnotationHelper.fieldOrPropertyAnnotated(property, ManyToOne.class, OneToOne.class, OneToMany.class, ManyToMany.class)) {
			builder.embeddedResource(name, accessor);
		}

		// None of the above, must be @Basic or unadorned.
		else {
			builder.simpleProperty(name, accessor);
		}
	}

	/* (non-Javadoc)
	 * @see net.eusashead.bjugquerydsl.hateoas.ResourceMetadataExtractor#canExtract(java.lang.Class)
	 */
	@Override
	public boolean canExtract(Class<?> target) {
		// Check it's an @Entity (or an ancestor is)
		return getEntityClass(target) != null ? true : false;
	}

}
