package ar.uba.fi.tdd.rulogic.model.implementations;

import ar.uba.fi.tdd.rulogic.model.abstractions.KnowledgeBase;
import ar.uba.fi.tdd.rulogic.model.abstractions.KnowledgeLine;
import ar.uba.fi.tdd.rulogic.model.abstractions.Query;

public class Rule implements KnowledgeLine {

	@Override
	public boolean tryAnswer(Query query, KnowledgeBase knowledgeBase) {
		return false;
	}

}
