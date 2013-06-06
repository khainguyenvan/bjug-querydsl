package net.eusashead.bjugquerydsl.data.repository;

import net.eusashead.bjugquerydsl.data.entity.Message;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends MongoRepository<Message, String>, QueryDslPredicateExecutor<Message>{

}
