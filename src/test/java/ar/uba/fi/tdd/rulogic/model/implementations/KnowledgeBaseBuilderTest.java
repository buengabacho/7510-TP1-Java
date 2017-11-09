package ar.uba.fi.tdd.rulogic.model.implementations;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import ar.uba.fi.tdd.rulogic.model.builder.KnowledgeBaseBuilder;
import ar.uba.fi.tdd.rulogic.model.builder.UnknownLineFormatException;

public class KnowledgeBaseBuilderTest {

	private KnowledgeBaseBuilder builder;
	
	@Before
	public void setUpTest() throws Exception {
		builder = new KnowledgeBaseBuilder();
	}
	
	@Test
	public void buildFromInexistentDbFileShouldFail() {
		try {
			builder.buildFromDBFile("inexistent.db");	
		} catch (IOException e) {

		} catch (UnknownLineFormatException e) {
			fail("Unexpected UnknownLineFormatException thrown");
		}
	}
	
	@Test
	public void buildFromCorrectDbFile() {
		try {
			builder.buildFromDBFile("src/test/resources/rules.db");
		} catch (IOException e) {
			fail("Unexpected IOException thrown");
		} catch (UnknownLineFormatException e) {
			fail("Unexpected UnknownLineFormatException thrown");
		}
	}

	@Test
	public void buildFromInvalidDbFile() {
		try {
			builder.buildFromDBFile("src/test/resources/invalidRules.db");
		} catch (IOException e) {
			fail("Unexpected IOException thrown");
		} catch (UnknownLineFormatException e) {
			assertThat(e.lineNumber, is(18));
			assertThat(e.lineData, is("tio(X, Y, Z):- varon(X),	(X, Z),padre(Z, Y)."));
		}
	}
}
