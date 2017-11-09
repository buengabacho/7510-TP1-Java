package ar.uba.fi.tdd.rulogic.model.builder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import ar.uba.fi.tdd.rulogic.model.abstractions.KnowledgeBase;
import ar.uba.fi.tdd.rulogic.model.abstractions.KnowledgeLine;
import ar.uba.fi.tdd.rulogic.model.abstractions.QueryParser;
import ar.uba.fi.tdd.rulogic.model.implementations.ConcreteKnowledgeBase;

public class KnowledgeBaseBuilder {
	
	DbLineParsingChain dbLineParsingChain;
	
	public KnowledgeBaseBuilder() {
		DbLineParsingChain factParser = new FactAndQueryParser();
		DbLineParsingChain ruleParser = new RuleParser((QueryParser)factParser);
		factParser.setNext(ruleParser);
		dbLineParsingChain = factParser;
	}
	
	public KnowledgeBase buildFromDBFile(String path) throws IOException, UnknownLineFormatException {
		QueryParser queryParser = new FactAndQueryParser();
		KnowledgeBase knowledgeBase = new ConcreteKnowledgeBase(queryParser);
		List<String> lines = new ArrayList<String>();
		Files.lines(Paths.get(path)).forEach(lines::add);
		int lineNumber = 1;
		for (String line : lines) {
			KnowledgeLine knowledgeLine = this.dbLineParsingChain.parseLine(lineNumber, line);
			knowledgeBase.addKnowledgeLine(knowledgeLine);
			lineNumber++;
		}
		return knowledgeBase;
	}
}
