package net.eusashead.bjugquerydsl.config;

import net.eusashead.bjugquerydsl.data.repository.Marker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;

@Configuration
@EnableMongoRepositories(basePackageClasses={Marker.class})
public class MongoConfig extends AbstractMongoConfiguration {

	private static final String DATABASE_NAME = "yourdb";

	@Override
	public @Bean Mongo mongo() throws Exception {
		return new Mongo("localhost", 27017);
	}

	@Override
	public @Bean MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongo(), DATABASE_NAME);
	}

	@Override
	public MappingMongoConverter mappingMongoConverter() throws Exception {
		return new MappingMongoConverter(mongoDbFactory(), mongoMappingContext());
	}

	@Override
	public String getMappingBasePackage() {
		return "net.eusashead.bjugquerydsl.data.entity";
	}
	
	@Override
	public String getDatabaseName() {
		return DATABASE_NAME;
	}

}
