package ar.uba.fi.tdd.rulogic.model.implementations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.uba.fi.tdd.rulogic.model.abstractions.KnowledgeLine;
import ar.uba.fi.tdd.rulogic.model.abstractions.Query;
import ar.uba.fi.tdd.rulogic.model.abstractions.QueryParser;
import ar.uba.fi.tdd.rulogic.model.implementations.ConcreteKnowledgeBase;

public class ConcreteKnowledgeBaseTest {
	
	private QueryParser mockedParser;
	private Query mockedQueryJavier;
	private Query mockedQueryLaura;
	private KnowledgeLine mockedLineJavier;	
	private ConcreteKnowledgeBase knowledgeBase;
	
	@Before
	public void setUpTest() throws Exception {
		mockedParser = mock(QueryParser.class);
		mockedQueryJavier = mock(Query.class);
		mockedQueryLaura = mock(Query.class);
		when(mockedParser.parseQuery("varon (javier).")).thenReturn(mockedQueryJavier);
		when(mockedParser.parseQuery("varon (laura).")).thenReturn(mockedQueryLaura);
		when(mockedParser.parseQuery("varonjavier).")).thenThrow(new Exception("Malformed query"));
		knowledgeBase = new ConcreteKnowledgeBase(mockedParser);
		mockedLineJavier = mock(KnowledgeLine.class);
		when(mockedLineJavier.tryAnswer(mockedQueryJavier, knowledgeBase)).thenReturn(true);
	}

	@Test
	public void queryWithoutAddingKnowledgeLineJavierShouldBeFalse() {
		try {
			Assert.assertFalse(this.knowledgeBase.answer("varon (javier)."));
		} catch (Exception e) {
			fail("Unexpected exception thrown: " + e.getMessage());
		}
	}
	
	@Test
	public void queryAddingKnowledgeLineJavierShouldBeTrue() {
		try {
			this.knowledgeBase.addKnowledgeLine(mockedLineJavier);
			Assert.assertTrue(this.knowledgeBase.answer("varon (javier)."));
		} catch (Exception e) {
			fail("Unexpected exception thrown: " + e.getMessage());
		}
	}
	
	@Test
	public void differentQueryAddingKnowledgeLineJavierShouldBeFalse() {
		try {
			this.knowledgeBase.addKnowledgeLine(mockedLineJavier);
			Assert.assertFalse(this.knowledgeBase.answer("varon (laura)."));
		} catch (Exception e) {
			fail("Unexpected exception thrown: " + e.getMessage());
		}
	}
	
	@Test
	public void malformedQueryShouldThrowException() {
		try {
			this.knowledgeBase.addKnowledgeLine(mockedLineJavier);
			this.knowledgeBase.answer("varonjavier).");
			fail("Expected exception: 'malformed query', but didn't get it.");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Malformed query");
		}
	}

}
