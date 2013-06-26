package net.eusashead.bjugquerydsl.hateoas;

public interface ResourceMetadataExtractor {
	
	boolean canExtract(Class<?> target);
	
	ResourceMetadata extract(Class<?> target);

}
