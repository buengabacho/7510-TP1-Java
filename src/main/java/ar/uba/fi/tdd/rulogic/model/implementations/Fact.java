package ar.uba.fi.tdd.rulogic.model.implementations;

import java.util.List;

import ar.uba.fi.tdd.rulogic.model.abstractions.KnowledgeBase;
import ar.uba.fi.tdd.rulogic.model.abstractions.KnowledgeLine;
import ar.uba.fi.tdd.rulogic.model.abstractions.Query;

public class Fact implements KnowledgeLine {
	
	String name;
	List<String> arguments;
	
	public Fact(String name, List<String> arguments) {
		this.name = name;
		this.arguments = arguments;
	}

	@Override
	public boolean tryAnswer(Query query, KnowledgeBase knowledgeBase) {
		return false;
	}

}
