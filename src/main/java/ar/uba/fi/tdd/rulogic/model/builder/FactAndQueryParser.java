package ar.uba.fi.tdd.rulogic.model.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ar.uba.fi.tdd.rulogic.model.abstractions.KnowledgeLine;
import ar.uba.fi.tdd.rulogic.model.abstractions.MalformedQueryException;
import ar.uba.fi.tdd.rulogic.model.abstractions.Query;
import ar.uba.fi.tdd.rulogic.model.abstractions.QueryParser;
import ar.uba.fi.tdd.rulogic.model.implementations.Fact;

public class FactAndQueryParser extends DbLineParsingChain implements QueryParser {

	private String name;
	private List<String> arguments;
	
	private void extractNameAndAttributes(String line) {
		Pattern p = Pattern.compile("^(.*)\\((.*)\\).?$");
		Matcher m = p.matcher(line);
		this.arguments = new ArrayList<String>();
		if (m.find()) {
			this.name = m.group(1).trim();
			Arrays.asList(m.group(2).split(","))
					.stream()
					.map(x -> x.trim())
					.filter(x -> x.length() > 0)
					.forEach(this.arguments::add);
		}	
	}
	
	@Override
	public KnowledgeLine tryHandle(String line) {
		extractNameAndAttributes(line);
		Fact newFact = null;
		if (this.name != null) {
			newFact = new Fact(this.name, this.arguments); 
		}
		this.name = null;
		this.arguments = null;
		return newFact;
	}

	@Override
	public Query parseQuery(String query) throws MalformedQueryException {
		extractNameAndAttributes(query);
		Query newQuery = null;
		if (this.name != null) {
			newQuery = new Query(this.name, this.arguments);
			this.name = null;
			this.arguments = null;
			return newQuery;			
		} 
		throw new MalformedQueryException();
	}

	@Override
	public Query safeParseQuery(String query) {
		try {
			return this.parseQuery(query);
		} catch (MalformedQueryException e) {
			return null;
		}
	}

}
