package ar.uba.fi.tdd.rulogic.model.abstractions;

public interface KnowledgeLine {

	public boolean tryAnswer(Query query, KnowledgeBase knowledgeBase);
	
}
