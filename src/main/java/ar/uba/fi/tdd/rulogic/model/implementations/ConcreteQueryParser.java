package ar.uba.fi.tdd.rulogic.model.implementations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ar.uba.fi.tdd.rulogic.model.abstractions.Query;
import ar.uba.fi.tdd.rulogic.model.abstractions.QueryParser;

public class ConcreteQueryParser implements QueryParser {

	@Override
	public Query parseQuery(String query) throws Exception {
		Pattern p = Pattern.compile("^(.*)\\((.*)\\).?$");
		Matcher m = p.matcher(query);
		String queryName;
		List<String> queryArguments = new ArrayList<String>();
		if (m.find()) {
			queryName = m.group(1).trim();
			Arrays.asList(m.group(2)
					.split(","))
					.stream()
					.map(x -> x.trim())
					.filter(x -> x.length() > 0)
					.forEach(queryArguments::add);
			return new Query(queryName, queryArguments);
		} else {
			throw new Exception("Malformed query");
		}
	}
	
}
