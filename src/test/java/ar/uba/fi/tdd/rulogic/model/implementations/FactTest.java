package ar.uba.fi.tdd.rulogic.model.implementations;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ar.uba.fi.tdd.rulogic.model.abstractions.KnowledgeBase;
import ar.uba.fi.tdd.rulogic.model.abstractions.KnowledgeLine;
import ar.uba.fi.tdd.rulogic.model.abstractions.Query;

public class FactTest {
	
	private KnowledgeBase knowledgeBase;
	
	@Before
	public void setUpTest() throws Exception {
		knowledgeBase = mock(KnowledgeBase.class);
	}
	
	private void tryAnswerQuery(String factName, List<String> factArgs, String queryName, List<String> queryArgs, boolean expectedResult) {
		Query query = mock(Query.class);
		when(query.name()).thenReturn(queryName);
		when(query.arguments()).thenReturn(queryArgs);		
		
		KnowledgeLine noArgumentsFact = new Fact(factName, factArgs);
		boolean answer = noArgumentsFact.tryAnswer(query, knowledgeBase);
		assertThat(answer, is(expectedResult));		
	}
	
	@Test
	public void noArgumentsFactMatchesNoArgumentsQuery() {
		tryAnswerQuery("varon", Arrays.asList(), "varon", Arrays.asList(), true);
	}
	
	@Test
	public void noArgumentsFactDoesntMatchNoArgumentsQuery() {
		tryAnswerQuery("varon", Arrays.asList(), "mujer", Arrays.asList(), false);
	}
	
	@Test
	public void noArgumentsFactDoesntMatchQueryWithArguments() {
		tryAnswerQuery("varon", Arrays.asList(), "varon", Arrays.asList("jose"), false);
	}
	
	@Test
	public void singleArgumentFactMatchesSingleArgumentQuery() {
		tryAnswerQuery("varon", Arrays.asList("jose"), "varon", Arrays.asList("jose"), true);
	}
	
	@Test
	public void singleArgumentFactDoesntMatchSingleArgumentQuery() {
		tryAnswerQuery("varon", Arrays.asList("jose"), "varon", Arrays.asList("pedro"), false);
	}
	
	@Test
	public void singleArgumentFactDoesntMatchNoArgumentQuery() {
		tryAnswerQuery("varon", Arrays.asList("jose"), "varon", Arrays.asList(), false);
	}
	
	@Test
	public void singleArgumentFactDoesntMatchMultipleArgumentsQuery() {
		tryAnswerQuery("varon", Arrays.asList("jose"), "varon", Arrays.asList("jose", "pedro"), false);
	}
	
	@Test
	public void multipleArgumentsFactMatchesMultipleArgumentsQuery() {
		tryAnswerQuery("varon", Arrays.asList("jose", "pedro"), "varon", Arrays.asList("jose", "pedro"), true);
	}
	
	@Test
	public void multipleArgumentsFactDoesntMatchArgumentsOutOfOrderQuery() {
		tryAnswerQuery("varon", Arrays.asList("jose", "pedro"), "varon", Arrays.asList("pedro", "jose"), false);
	}
	
	@Test
	public void multipleArgumentsFactDoesntMatchMissingArgumentQuery() {
		tryAnswerQuery("varon", Arrays.asList("jose", "pedro"), "varon", Arrays.asList("jose"), false);
	}
}
