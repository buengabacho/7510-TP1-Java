package ar.uba.fi.tdd.rulogic.model;

import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.uba.fi.tdd.rulogic.model.abstractions.KnowledgeLine;
import ar.uba.fi.tdd.rulogic.model.implementations.ConcreteKnowledgeBase;

public class ConcreteKnowledgeBaseTest {
	
	private KnowledgeLine mockedLine = mock(KnowledgeLine.class);
	
	private ConcreteKnowledgeBase knowledgeBase;

	@Before
	public void setUp() throws Exception {
		knowledgeBase = new ConcreteKnowledgeBase();
		when(mockedLine.tryAnswer("varon (javier).")).thenReturn(true);
	}

	@Test
	public void queryWithoutAddingKnowledgeLineShouldBeFalse() {
		Assert.assertFalse(this.knowledgeBase.answer("varon (javier)."));
	}
	
	@Test
	public void queryAddingKnowledgeLineShouldBeTrue() {
		this.knowledgeBase.addKnowledgeLine(mockedLine);
		Assert.assertTrue(this.knowledgeBase.answer("varon (javier)."));
	}	

}
