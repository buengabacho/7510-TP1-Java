package ar.uba.fi.tdd.rulogic.model.implementations;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import ar.uba.fi.tdd.rulogic.model.abstractions.Query;
import ar.uba.fi.tdd.rulogic.model.abstractions.QueryParser;

public class ConcreteQueryParserTest {
	
	private QueryParser queryParser;
	
	@Before
	public void setUpTest() throws Exception {
		queryParser = new ConcreteQueryParser();
	}
	
	@Test
	public void malformedQueryShouldThrowException() {
		try {
			this.queryParser.parseQuery("varonjavier).");
			fail("Expected exception: 'malformed query', but didn't get it.");
		} catch (Exception e) {
			assertThat(e.getMessage(), is("Malformed query"));
		}
	}
	
	@Test
	public void correctQueryWithNoArguments() {
		try {
			Query parsedQuery = this.queryParser.parseQuery("varon ().");
			assertThat(parsedQuery.name(), is("varon"));
			assertThat(parsedQuery.arguments(), is(Arrays.asList()));
		} catch (Exception e) {
			fail("Unexpected exception thrown: '" + e.getMessage() + "'");
		}
	}
	
	@Test
	public void correctQueryWithOneArgument() {
		try {
			Query parsedQuery = this.queryParser.parseQuery("varon (javier).");
			assertThat(parsedQuery.name(), is("varon"));
			assertThat(parsedQuery.arguments(), is(Arrays.asList("javier")));
		} catch (Exception e) {
			fail("Unexpected exception thrown: '" + e.getMessage() + "'");
		}
	}
	
	@Test
	public void correctQueryWithMultipleArguments() {
		try {
			Query parsedQuery = this.queryParser.parseQuery("varon (javier, roberto).");
			assertThat(parsedQuery.name(), is("varon"));
			assertThat(parsedQuery.arguments(), is(Arrays.asList("javier", "roberto")));
		} catch (Exception e) {
			fail("Unexpected exception thrown: '" + e.getMessage() + "'");
		}
	}

}
