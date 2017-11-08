package ar.uba.fi.tdd.rulogic.model.implementations;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ar.uba.fi.tdd.rulogic.model.abstractions.KnowledgeBase;
import ar.uba.fi.tdd.rulogic.model.abstractions.KnowledgeLine;
import ar.uba.fi.tdd.rulogic.model.abstractions.Query;

public class RuleTest {
	
	private KnowledgeBase knowledgeBase;
	
	@Before
	public void setUpTest() throws Exception {
		knowledgeBase = mock(KnowledgeBase.class);
		when(knowledgeBase.answer("varon (pedro)")).thenReturn(true);
		when(knowledgeBase.answer("hijo (jose, pedro)")).thenReturn(true);
	}

	private void tryAnswerQuery(String ruleName, List<String> ruleArgs, List<String> ruleSubqueries, String queryName, List<String> queryArgs, boolean expectedResult) {
		Query query = mock(Query.class);
		when(query.name()).thenReturn(queryName);
		when(query.arguments()).thenReturn(queryArgs);		
		
		KnowledgeLine noArgumentsFact = new Rule(ruleName, ruleArgs, ruleSubqueries);
		boolean answer = noArgumentsFact.tryAnswer(query, knowledgeBase);
		assertThat(answer, is(expectedResult));		
	}
	
	@Test
	public void singleArgumentRuleMatchesQuery() {
		tryAnswerQuery("hombre", Arrays.asList("x"), Arrays.asList("varon (x)"), "hombre", Arrays.asList("pedro"), true);
	}
	
	@Test
	public void singleArgumentRuleDoesntMatchQueryName() {
		tryAnswerQuery("hombre", Arrays.asList("x"), Arrays.asList("varon (x)"), "mujer", Arrays.asList("pedro"), false);
	}
	
	@Test
	public void singleArgumentRuleDoesntMatchQueryArguments() {
		tryAnswerQuery("hombre", Arrays.asList("x"), Arrays.asList("varon (x)"), "hombre", Arrays.asList("laura"), false);
	}
	
	@Test
	public void multipleSubqueriesRuleMatchesQuery() {
		tryAnswerQuery("padre", Arrays.asList("x", "y"), Arrays.asList("varon (x)", "hijo(y, x)"), "padre", Arrays.asList("pedro", "jose"), false);
	}
	
	@Test
	public void multipleSubqueriesRuleDoesntMatchQueryArguments() {
		tryAnswerQuery("padre", Arrays.asList("x", "y"), Arrays.asList("varon (x)", "hijo(y, x)"), "padre", Arrays.asList("jose", "pedro"), false);
	}

}
