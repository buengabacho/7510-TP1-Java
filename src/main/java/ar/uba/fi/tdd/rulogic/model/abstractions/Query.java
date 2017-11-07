package ar.uba.fi.tdd.rulogic.model.abstractions;

import java.util.List;

public class Query {
	private String name;
	private List<String> arguments;
	
	public Query(String name, List<String> arguments) {
		this.name = name;
		this.arguments = arguments;
	}
	
	public String name() {
		return this.name;
	}
	
	public List<String> arguments() {
		return this.arguments;
	}
}
