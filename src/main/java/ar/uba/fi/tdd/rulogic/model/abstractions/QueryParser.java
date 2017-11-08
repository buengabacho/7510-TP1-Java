package ar.uba.fi.tdd.rulogic.model.abstractions;

public interface QueryParser {
	
	public Query parseQuery(String query) throws MalformedQueryException;

}
