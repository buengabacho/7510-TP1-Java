package ar.uba.fi.tdd.rulogic.model.implementations;

import ar.uba.fi.tdd.rulogic.model.abstractions.KnowledgeLine;

public class Fact implements KnowledgeLine {

	@Override
	public boolean tryAnswer(String query) {
		return false;
	}

}
