package net.eusashead.bjugquerydsl.data.repository;

import net.eusashead.bjugquerydsl.data.entity.Value;
import java.lang.Integer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ValueRepository extends JpaRepository<Value, Integer>, QueryDslPredicateExecutor<Value> {
	

}