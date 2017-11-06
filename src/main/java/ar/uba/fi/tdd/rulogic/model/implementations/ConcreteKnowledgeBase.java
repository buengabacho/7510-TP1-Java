package ar.uba.fi.tdd.rulogic.model.implementations;

import java.util.ArrayList;
import java.util.List;

import ar.uba.fi.tdd.rulogic.model.abstractions.KnowledgeBase;
import ar.uba.fi.tdd.rulogic.model.abstractions.KnowledgeLine;

public class ConcreteKnowledgeBase implements KnowledgeBase {
	
	private List<KnowledgeLine> lines;
	
	public ConcreteKnowledgeBase() {
		this.lines = new ArrayList<KnowledgeLine>();
	}

	public void addKnowledgeLine(KnowledgeLine line) {
		this.lines.add(line);
	}

	public boolean answer(String query) {
		return this.lines.stream().anyMatch(line -> line.tryAnswer(query));
	}
	
}
