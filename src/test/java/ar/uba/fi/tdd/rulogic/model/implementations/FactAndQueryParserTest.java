package ar.uba.fi.tdd.rulogic.model.implementations;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ar.uba.fi.tdd.rulogic.model.abstractions.KnowledgeBase;
import ar.uba.fi.tdd.rulogic.model.abstractions.KnowledgeLine;
import ar.uba.fi.tdd.rulogic.model.abstractions.MalformedQueryException;
import ar.uba.fi.tdd.rulogic.model.abstractions.Query;
import ar.uba.fi.tdd.rulogic.model.builder.FactAndQueryParser;

public class FactAndQueryParserTest {
	
	private FactAndQueryParser factAndQueryParser;
	private KnowledgeBase knowledgeBase;
	
	@Before
	public void setUpTest() throws Exception {
		factAndQueryParser = new FactAndQueryParser();
		knowledgeBase = mock(KnowledgeBase.class);
	}
	
	@Test
	public void malformedQueryShouldThrowException() {
		try {
			this.factAndQueryParser.parseQuery("varonjavier).");
			fail("Expected exception: 'malformed query', but didn't get it.");
		} catch (MalformedQueryException e) {
		}
	}
	
	@Test
	public void correctQueryWithNoArguments() {
		try {
			Query parsedQuery = this.factAndQueryParser.parseQuery("varon ().");
			assertThat(parsedQuery.name(), is("varon"));
			assertThat(parsedQuery.arguments(), is(Arrays.asList()));
		} catch (MalformedQueryException e) {
			fail("Unexpected exception thrown: '" + e.getMessage() + "'");
		}
	}
	
	@Test
	public void correctQueryWithOneArgument() {
		try {
			Query parsedQuery = this.factAndQueryParser.parseQuery("varon (javier).");
			assertThat(parsedQuery.name(), is("varon"));
			assertThat(parsedQuery.arguments(), is(Arrays.asList("javier")));
		} catch (MalformedQueryException e) {
			fail("Unexpected exception thrown: '" + e.getMessage() + "'");
		}
	}
	
	@Test
	public void correctQueryWithMultipleArguments() {
		try {
			Query parsedQuery = this.factAndQueryParser.parseQuery("varon (javier, roberto).");
			assertThat(parsedQuery.name(), is("varon"));
			assertThat(parsedQuery.arguments(), is(Arrays.asList("javier", "roberto")));
		} catch (MalformedQueryException e) {
			fail("Unexpected exception thrown: '" + e.getMessage() + "'");
		}
	}
	
	@Test
	public void malformedFactShouldReturnNull() {
		KnowledgeLine parsedFact = this.factAndQueryParser.tryHandle("varonjavier).");
		assertNull(parsedFact);
	}
	
	private void tryAnswerQuery(String factString, String queryName, List<String> queryArgs) {
		Query query = mock(Query.class);
		when(query.name()).thenReturn(queryName);
		when(query.arguments()).thenReturn(queryArgs);		
		KnowledgeLine parsedFact = this.factAndQueryParser.tryHandle(factString);
		boolean answer = parsedFact .tryAnswer(query, knowledgeBase);
		assertThat(answer, is(true));		
	}	
	
	@Test
	public void correctFactWithNoArguments() {
		tryAnswerQuery("varon().", "varon", Arrays.asList());
	}
	
	@Test
	public void correctFactWithOneArgument() {
		tryAnswerQuery("varon(javier).", "varon", Arrays.asList("javier"));
	}
	
	@Test
	public void correctFactWithMultipleArguments() {
		tryAnswerQuery("varon(javier, roberto).", "varon", Arrays.asList("javier", "roberto"));
	}

}
