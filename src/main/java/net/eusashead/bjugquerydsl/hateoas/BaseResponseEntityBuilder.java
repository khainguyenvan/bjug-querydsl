package net.eusashead.bjugquerydsl.hateoas;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriTemplate;

public abstract class BaseResponseEntityBuilder<T> implements ResponseEntityBuilder<T>{

	public static final String LAST_MODIFIED_HEADER = "Last-Modified";
	public static final String ETAG_HEADER = "Etag";
	public static final String LOCATION_HEADER = "Location";
	public static final String RFC2822DATE = "EEE, dd MMM yyyy HH:mm:ss Z";
	
	protected final HttpServletRequest request;
	protected final HttpHeaders headers;
	protected final String domain;

	public BaseResponseEntityBuilder(HttpServletRequest request) {
		
		// Validate the request
		if (request == null) {
			throw new IllegalArgumentException("HttpServletRequest is null.");
		}
		
		// Set the request
		this.request = request;
		
		// Set the root URL
		this.domain = request.getScheme() + "://" + request.getServerName();

		// Create new headers
		this.headers = new HttpHeaders();
	}

	@Override
	public ResponseEntityBuilder<T> location(String path, Object...objects) {
		UriTemplate uriTemplate = new UriTemplate(path);
		this.headers.add(LOCATION_HEADER, this.domain + uriTemplate.expand(objects).toString());
		return this;
	}

	

	@Override
	public ResponseEntityBuilder<T> etag(Date date) {
		// Weak etag
		this.headers.add(ETAG_HEADER, "w/" + Long.valueOf(date.getTime()).toString()); 
		return this;
	}

	@Override
	public ResponseEntityBuilder<T> etag(Integer version) {
		// Strong etag
		this.headers.add(ETAG_HEADER, version.toString());
		return this;
	}

	@Override
	public ResponseEntityBuilder<T> lastModified(Date date) {
		this.headers.add(LAST_MODIFIED_HEADER, new SimpleDateFormat(RFC2822DATE).format(date));
		return this;
	}

	@Override
	public ResponseEntityBuilder<T> expireIn(long millis) {
		Date now = new Date();
		this.headers.setExpires(millis + now.getTime());
		this.headers.setCacheControl(String.format("max-age=%d", millis/1000));
		return this;
	}

}