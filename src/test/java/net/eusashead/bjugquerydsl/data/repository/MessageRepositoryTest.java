package net.eusashead.bjugquerydsl.data.repository;

import net.eusashead.bjugquerydsl.config.MongoConfig;
import net.eusashead.bjugquerydsl.data.entity.Message;
import net.eusashead.bjugquerydsl.data.entity.QMessage;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Quick demonstration of the
 * Mongodb integration options
 * with QueryDSL and Spring Data Mongo
 * 
 * @author patrickvk
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={MongoConfig.class})
public class MessageRepositoryTest {

	@Autowired
	private MessageRepository repo;

	@Test
	public void testPredicate() throws Exception {
		Iterable<Message> results = repo.findAll(QMessage.message.id.eq("123"));
		Assert.assertNotNull(results);
	}
}
