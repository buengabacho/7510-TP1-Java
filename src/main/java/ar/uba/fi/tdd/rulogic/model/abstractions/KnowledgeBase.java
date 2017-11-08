package ar.uba.fi.tdd.rulogic.model.abstractions;

public interface KnowledgeBase {
	
	public void addKnowledgeLine(KnowledgeLine line);

	public boolean answer(String query) throws MalformedQueryException;

}
