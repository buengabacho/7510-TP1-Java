package ar.uba.fi.tdd.rulogic.model.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ar.uba.fi.tdd.rulogic.model.abstractions.KnowledgeLine;
import ar.uba.fi.tdd.rulogic.model.abstractions.QueryParser;
import ar.uba.fi.tdd.rulogic.model.implementations.Rule;

public class RuleParser extends DbLineParsingChain {
	
	private QueryParser queryParser;
	
	public RuleParser(QueryParser queryParser) {
		this.queryParser = queryParser;
	}

	@Override
	public KnowledgeLine tryHandle(String line) {
		Pattern p = Pattern.compile("^(.*)\\((.*)\\) *:- *(.*)\\s*\\.?$");
		Matcher m = p.matcher(line);
		if (m.find()) {
			String name = m.group(1).trim();
			List<String> arguments = new ArrayList<String>();
			List<String> subqueries = new ArrayList<String>();
			Arrays.asList(m.group(2).split(","))
					.stream()
					.map(x -> x.trim())
					.filter(x -> x.length() > 0)
					.forEach(arguments::add);
			Arrays.asList(m.group(3).split("(,\\s*(?=[^\\(]*\\([^\\)]*\\)))"))
					.stream()
					.map(x -> x.trim().replaceAll("\\.$", ""))
					.filter(x -> x.length() > 0)
					.forEach(subqueries::add);
			boolean subqueriesAreValid = 
					subqueries.stream()
					.allMatch(current -> this.queryParser.safeParseQuery(current) != null);
			if (subqueriesAreValid) {
				return new Rule(name, arguments, subqueries);
			}
		}
		return null;
	}

}
