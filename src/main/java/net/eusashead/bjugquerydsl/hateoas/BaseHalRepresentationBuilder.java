package net.eusashead.bjugquerydsl.hateoas;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.theoryinpractise.halbuilder.api.ReadableRepresentation;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

public abstract class BaseHalRepresentationBuilder extends
BaseResponseEntityBuilder<ReadableRepresentation> {

	protected final RepresentationFactory representationFactory;
	protected Representation representation;
	protected final RepresentationConverter converter;
	protected final String[] requestedFields;

	public BaseHalRepresentationBuilder(RepresentationFactory representationFactory, RepresentationConverter converter, HttpServletRequest request, String fieldVariable) {
		super(request);

		// Set the RepresentationFactory
		this.representationFactory = representationFactory;
		
		// Set up the bean converter 
		this.converter = converter;

		// Get requested fields from the request
		this.requestedFields = request.getParameterValues(fieldVariable);

		// Create representation
		this.representation = representationFactory.newRepresentation(request.getRequestURI() + request.getQueryString());
	}

	@Override
	public ResponseEntityBuilder<ReadableRepresentation> etag() {
		// Weak etag
		this.headers.add(ETAG_HEADER, "w/" + Integer.valueOf(this.representation.hashCode()).toString());
		return this;
	}

	@Override
	public ResponseEntity<ReadableRepresentation> get() {
		return new ResponseEntity<ReadableRepresentation>(representation, headers, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ReadableRepresentation> create() {
		if (this.headers.getLocation() == null) {
			throw new RuntimeException("Location header must be set before calling create().");
		}
		return new ResponseEntity<ReadableRepresentation>(headers, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ReadableRepresentation> update() {
		return new ResponseEntity<ReadableRepresentation>(headers, HttpStatus.NO_CONTENT);
	}

	@Override
	public ResponseEntity<ReadableRepresentation> delete() {
		return new ResponseEntity<ReadableRepresentation>(headers, HttpStatus.NO_CONTENT);
	}

	@Override
	public ResponseEntity<ReadableRepresentation> head() {
		return new ResponseEntity<ReadableRepresentation>(headers, HttpStatus.NO_CONTENT); 
	}

	/**
	 * Add a link to the 
	 * representation
	 * @param rel
	 * @param href
	 * @return
	 */
	public ResponseEntityBuilder<ReadableRepresentation> withLink(String rel, String href) {
		this.representation.withLink(rel, href);
		return this;
	}

	/** 
	 * Set a namespace for the 
	 * HAL representation
	 * @param namespace
	 * @param href
	 * @return
	 */
	public ResponseEntityBuilder<ReadableRepresentation> withNamespace(String namespace, String href) {
		this.representation.withNamespace(namespace, href);
		return this;
	}

}