package ar.uba.fi.tdd.rulogic.model.implementations;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ar.uba.fi.tdd.rulogic.model.abstractions.KnowledgeBase;
import ar.uba.fi.tdd.rulogic.model.abstractions.KnowledgeLine;
import ar.uba.fi.tdd.rulogic.model.abstractions.Query;
import ar.uba.fi.tdd.rulogic.model.abstractions.QueryParser;
import ar.uba.fi.tdd.rulogic.model.builder.RuleParser;

public class RuleParserTest {

	private RuleParser ruleParser;
	private KnowledgeBase knowledgeBase;
	private QueryParser queryParser;
	
	@Before
	public void setUpTest() throws Exception {
		queryParser = mock(QueryParser.class);
		when(queryParser.safeParseQuery("varon()")).thenReturn(new Query("varon", Arrays.asList()));
		when(queryParser.safeParseQuery("varon(x)")).thenReturn(new Query("varon", Arrays.asList("x")));
		when(queryParser.safeParseQuery("hijo(y, x)")).thenReturn(new Query("hijo", Arrays.asList("y", "x")));
		ruleParser = new RuleParser(queryParser);
		knowledgeBase = mock(KnowledgeBase.class);
		when(knowledgeBase.answer("varon()")).thenReturn(true);
		when(knowledgeBase.answer("varon(javier)")).thenReturn(true);
		when(knowledgeBase.answer("hijo(jose,javier)")).thenReturn(true);
	}
	
	@Test
	public void malformedRuleShouldReturnNull() {
		KnowledgeLine parsedRule = this.ruleParser.tryHandle("hombre (x) : varonx).");
		assertNull(parsedRule);
	}

	private void tryAnswerQuery(String ruleString, String queryName, List<String> queryArgs) {
		Query query = mock(Query.class);
		when(query.name()).thenReturn(queryName);
		when(query.arguments()).thenReturn(queryArgs);		
		KnowledgeLine parsedRule = this.ruleParser.tryHandle(ruleString);
		boolean answer = parsedRule.tryAnswer(query, knowledgeBase);
		assertThat(answer, is(true));
	}	
	
	@Test
	public void correctRuleWithNoArguments() {
		tryAnswerQuery("hombre() :- varon().", "hombre", Arrays.asList());
	}
	
	@Test
	public void correctFactWithOneArgument() {
		tryAnswerQuery("hombre(x) :- varon(x).", "hombre", Arrays.asList("javier"));
	}
	
	@Test
	public void correctFactWithMultipleArguments() {
		tryAnswerQuery("padre(x, y) :- varon(x), hijo(y, x).", "padre", Arrays.asList("javier", "jose"));
	}
	
}
