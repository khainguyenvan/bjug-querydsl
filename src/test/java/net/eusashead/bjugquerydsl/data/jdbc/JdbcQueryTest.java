package net.eusashead.bjugquerydsl.data.jdbc;

import java.util.List;

import javax.sql.DataSource;

import net.eusashead.bjugquerydsl.config.WebConfig;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.mysema.query.Tuple;
import com.mysema.query.sql.H2Templates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.types.Projections;

/**
 * Basic demonstration of JDBC
 * query capabilities, both
 * raw column Tuples and bean
 * projection
 * @author patrickvk
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes={WebConfig.class})
public class JdbcQueryTest {

	@Autowired
	private DataSource connection;

	@Test
	public void testBasicQuery() throws Exception {
		SQLTemplates dialect = new H2Templates();
		SQLQuery query = new SQLQuery(connection.getConnection(), dialect);
		QStockKeepingUnit path = QStockKeepingUnit.stockKeepingUnit;
		List<Tuple> results = query.from(path)
				.list(path.productId, path.price);
		Assert.assertTrue(results.size() == 8);
	}

	@Test
	public void testProjectionQuery() throws Exception {
		SQLTemplates dialect = new H2Templates();
		SQLQuery query = new SQLQuery(connection.getConnection(), dialect);
		QStockKeepingUnit path = QStockKeepingUnit.stockKeepingUnit;
		List<StockKeepingUnit> results = query.from(path)
				.list(Projections.bean(StockKeepingUnit.class, path.productId, path.price));
		
		Assert.assertTrue(results.size() == 8);
	}
}
