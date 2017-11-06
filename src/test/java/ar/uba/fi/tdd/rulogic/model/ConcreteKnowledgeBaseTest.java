package ar.uba.fi.tdd.rulogic.model;

import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import ar.uba.fi.tdd.rulogic.model.implementations.ConcreteKnowledgeBase;

public class ConcreteKnowledgeBaseTest {

	@InjectMocks
	private ConcreteKnowledgeBase knowledgeBase;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
	}

	@Test
	public void test() {
		Assert.assertFalse(this.knowledgeBase.answer("varon (javier)."));
	}

}
