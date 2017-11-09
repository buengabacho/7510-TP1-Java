package ar.uba.fi.tdd.rulogic.model.builder;

import ar.uba.fi.tdd.rulogic.model.abstractions.KnowledgeLine;

public abstract class DbLineParsingChain {
	
	private DbLineParsingChain next;
	
	public DbLineParsingChain() {
		this.next = null;
	}
	
	protected abstract KnowledgeLine tryHandle(String line); 

	public final KnowledgeLine parseLine(int lineNumber, String line) throws UnknownLineFormatException {
		KnowledgeLine result = this.tryHandle(line);
		if (result != null) {
			return result;
		}
		if (this.next != null) {
			return this.next.parseLine(lineNumber, line);
		}
		throw new UnknownLineFormatException(lineNumber, line); 
	}

	public final void setNext(DbLineParsingChain next) {
		this.next = next;
	}

}
