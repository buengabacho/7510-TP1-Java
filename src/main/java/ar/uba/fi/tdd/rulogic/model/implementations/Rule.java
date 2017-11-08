package ar.uba.fi.tdd.rulogic.model.implementations;

import java.util.List;

import ar.uba.fi.tdd.rulogic.model.abstractions.KnowledgeBase;
import ar.uba.fi.tdd.rulogic.model.abstractions.KnowledgeLine;
import ar.uba.fi.tdd.rulogic.model.abstractions.Query;

public class Rule implements KnowledgeLine {
	
	private String name;
	private List<String> arguments;
	private List<String> subqueries;
	
	public Rule(String name, List<String> arguments, List<String> subqueries) {
		this.name = name;
		this.arguments = arguments;
		this.subqueries = subqueries;
	}
	
	private String instanciateSubquery(Query query, String subquery) {
		int idx = 0;
		String instanciatedSubquery = subquery;
		for (String argument : this.arguments) {
			instanciatedSubquery = instanciatedSubquery.replaceAll("(?<=[\\(,]) *" + argument + " *(?=[\\),])", query.arguments().get(idx));
			System.out.println("replacement: " + instanciatedSubquery);
			idx++;				
		}
		return instanciatedSubquery;
	}
	
	private boolean getAnswerFromKnowledgeBase(KnowledgeBase knowledgeBase, Query query, String subquery) {
		boolean response;
		try {
			String instanciatedSubquery = instanciateSubquery(query, subquery);
			response = knowledgeBase.answer(instanciatedSubquery);
		} catch (Exception e) {
			response = false;
			System.out.println("Silent fail on unexpected exception: '" + e.getMessage() + "'");
		}
		return response;
	}

	@Override
	public boolean tryAnswer(Query query, KnowledgeBase knowledgeBase) {
		boolean isSameName = query.name().equals(this.name);
		if (!isSameName) {
			return false;
		}
		boolean response = true;
		for (String subquery : this.subqueries) {
			response = response && getAnswerFromKnowledgeBase(knowledgeBase, query, subquery);
		}
		return response;
	}

}
