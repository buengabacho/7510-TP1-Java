package ar.uba.fi.tdd.rulogic.model.builder;

import ar.uba.fi.tdd.rulogic.model.abstractions.KnowledgeLine;

public abstract class DbLineParsingChain {
	
	private DbLineParsingChain next;
	
	public DbLineParsingChain() {
		this.next = null;
	}
	
	protected abstract KnowledgeLine tryHandle(String line); 

	public final KnowledgeLine parseLine(String line) throws UnknownLineFormatException {
		KnowledgeLine result = this.tryHandle(line);
		if (result != null) {
			return result;
		}
		if (next != null) {
			this.next.parseLine(line);
		}
		throw new UnknownLineFormatException(); 
	}

	public final void setNext(DbLineParsingChain next) {
		this.next = next;
	}

}
