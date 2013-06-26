package net.eusashead.bjugquerydsl.hateoas;

import java.util.Date;

import org.springframework.http.ResponseEntity;

public interface ResponseEntityBuilder<T> {

	/** 
	 * Set the location header
	 * for use when creating
	 * a new resource
	 * @param path
	 * @param objects
	 * @return
	 */
	ResponseEntityBuilder<T> location(String path, Object... objects);

	/**
	 * Create ETag header based
	 * on hash representation of object
	 * @return
	 */
	ResponseEntityBuilder<T> etag();

	ResponseEntityBuilder<T> etag(Date date);

	ResponseEntityBuilder<T> etag(Integer version);

	ResponseEntityBuilder<T> lastModified(Date date);

	ResponseEntityBuilder<T> expireIn(long millis);

	ResponseEntity<T> get();

	ResponseEntity<T> create();

	ResponseEntity<T> update();

	ResponseEntity<T> delete();

	ResponseEntity<T> head();

}