package net.eusashead.bjugquerydsl.hateoas;

import net.eusashead.bjugquerydsl.data.entity.Basket;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class JpaEntityResourceMetadataExtractorTest {

	JpaEntityResourceMetadataExtractor xtrctr;

	@Before
	public void before() {
		xtrctr= new JpaEntityResourceMetadataExtractor();
	}

	@Test
	public void testExtractEntityMetadata() throws Exception {
		ResourceMetadata meta = xtrctr.extract(Basket.class);
		Assert.assertNotNull(meta);
		Assert.assertEquals(Integer.valueOf(1), Integer.valueOf(meta.getIdentityProperties().size()));
		Assert.assertEquals(Integer.valueOf(2), Integer.valueOf(meta.getEmbeddedResources().size()));
		Assert.assertEquals(Integer.valueOf(0), Integer.valueOf(meta.getSimpleProperties().size()));

	}

	@Test
	public void testCanExtract() throws Exception {
		Assert.assertTrue(xtrctr.canExtract(Basket.class));
		Assert.assertFalse(xtrctr.canExtract(Object.class));
	}

	@Test(expected=RuntimeException.class)
	public void testNotAnnotated() throws Exception {
		xtrctr.extract(Object.class);
	}
}

