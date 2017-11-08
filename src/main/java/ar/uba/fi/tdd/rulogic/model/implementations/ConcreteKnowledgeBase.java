package ar.uba.fi.tdd.rulogic.model.implementations;

import java.util.ArrayList;
import java.util.List;

import ar.uba.fi.tdd.rulogic.model.abstractions.KnowledgeBase;
import ar.uba.fi.tdd.rulogic.model.abstractions.KnowledgeLine;
import ar.uba.fi.tdd.rulogic.model.abstractions.MalformedQueryException;
import ar.uba.fi.tdd.rulogic.model.abstractions.QueryParser;
import ar.uba.fi.tdd.rulogic.model.abstractions.Query;

public class ConcreteKnowledgeBase implements KnowledgeBase {
	
	private List<KnowledgeLine> lines;
	private QueryParser parser;
	
	public ConcreteKnowledgeBase(QueryParser parser) {
		this.lines = new ArrayList<KnowledgeLine>();
		this.parser = parser;
	}

	public void addKnowledgeLine(KnowledgeLine line) {
		this.lines.add(line);
	}

	public boolean answer(String query) throws MalformedQueryException {
		Query parsedQuery = this.parser.parseQuery(query);
		return this.lines.stream().anyMatch(line -> line.tryAnswer(parsedQuery, this));
	}
	
}
